package com.adobe.aem.demo.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Sidebarslingmodel {
	
	
	@ValueMapValue
	public String logopathfield;
	
	@ValueMapValue
	public String logomobileimage;
	
	@ValueMapValue
	public String logolink;
	
	@ValueMapValue
	public String checkbox;
	
	@ValueMapValue
	public String country;
	

	public String getLogopath() {
		return logopathfield;
	}

	public String getLogomobileimage() {
		return logomobileimage;
	}

	public String getLogolink() {
		return logolink;
	}

	public String getCheckbox() {
		return checkbox;
	}
	
	public String getCountry() {
		return country;
	}
	
	

}