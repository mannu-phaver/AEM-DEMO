package com.adobe.aem.demo.core.services.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.adobe.aem.demo.core.services.SearchService;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.day.cq.wcm.api.NameConstants.NT_PAGE;

@Component(service = SearchService.class, property = {
        Constants.SERVICE_ID + "=SearchService",
        Constants.SERVICE_DESCRIPTION + "=Service using QueryBuilder to search JCR"
})
public class SearchServiceImpl implements SearchService {

    @Reference
    private QueryBuilder queryBuilder;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<String> searchByKeyword(String keyword) {
        List<String> resultPaths = new ArrayList<>();
        Map<String, Object> authInfo = new HashMap<>();
        authInfo.put(ResourceResolverFactory.SUBSERVICE, "mannu");

        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(authInfo)) {
            Session session = resourceResolver.adaptTo(Session.class);
            if (session == null) {
                return resultPaths;
            }

            Map<String, String> predicates = new HashMap<>();
            predicates.put("type", NT_PAGE);
            predicates.put("path", "/content/");
            predicates.put("fulltext", keyword);

            Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
            SearchResult searchResult = query.getResult();

            for (Hit hit : searchResult.getHits()) {
                resultPaths.add(hit.getPath());
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultPaths;
    }
}
