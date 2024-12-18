package com.adobe.aem.demo.core.servlets;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.json.JSONArray;
import org.json.JSONException;
import org.osgi.service.component.annotations.Component;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.commons.util.DamUtil;

// @SuppressWarnings("serial")
@Component(service=Servlet.class,
property = {"sling.servlet.resourceTypes=/apps/dropdownJson/data",
		"sling.servlet.methods=GET"})
//@SlingServletResourceTypes(resourceTypes = "/apps/dropdownJson/data")
public class DropJSONServlet extends SlingAllMethodsServlet {
	
	//private static final Logger LOG=LoggerFactory.getLogger(DropJSONServlet.class);
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
		ResourceResolver resolver=request.getResourceResolver();
		Resource countryResource=resolver.getResource("/content/dam/color.json");
		
		Asset asset=DamUtil.resolveToAsset(countryResource);
		Rendition originalAsset=asset.getOriginal();
		
		InputStream is=originalAsset.adaptTo(InputStream.class);
		
		StringBuilder sb=new StringBuilder();
		
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		
		String line;
		
		while(null != (line=br.readLine())) {
			sb.append(line);
		}
		//LOG.info("Colors Json Data:",sb.toString());
		
		JSONArray jsonContent=new JSONArray(sb.toString());
		Map<String, String> data=new TreeMap();
		for(int i=0; i<jsonContent.length(); i++) {
			data.put(jsonContent.getJSONObject(i).getString("text"),
					jsonContent.getJSONObject(i).getString("value"));
		}
		DataSource ds=new SimpleDataSource(new TransformIterator(data.keySet().iterator(),(Transformer) o -> {
			String dropValue =(String) o;
			ValueMap vm=new ValueMapDecorator(new HashMap());
			vm.put("text", dropValue);
			vm.put("value", data.get(dropValue));
			return new ValueMapResource(resolver,new ResourceMetadata(),"nt:unstructured",vm);
		}));
		request.setAttribute(DataSource.class.getName(),ds);
		}
		catch (JSONException e) 
		{
			//LOG.info("Error while fetching the data");
		}	
	}

}