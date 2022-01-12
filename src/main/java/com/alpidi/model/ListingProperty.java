package com.alpidi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListingProperty {
//	 @JsonProperty("property_id") 
//	    public long getProperty_id() { 
//			 return this.property_id; } 
//	    public void setProperty_id(long property_id) { 
//			 this.property_id = property_id; } 
//	    long property_id;
	    @JsonProperty("property_name") 
	    public String getProperty_name() { 
			 return this.property_name; } 
	    public void setProperty_name(String property_name) { 
			 this.property_name = property_name; } 
	    String property_name;
//	    @JsonProperty("scale_id") 
//	    public Object getScale_id() { 
//			 return this.scale_id; } 
//	    public void setScale_id(Object scale_id) { 
//			 this.scale_id = scale_id; } 
//	    Object scale_id;
//	    @JsonProperty("scale_name") 
//	    public Object getScale_name() { 
//			 return this.scale_name; } 
//	    public void setScale_name(Object scale_name) { 
//			 this.scale_name = scale_name; } 
//	    Object scale_name;
//	    @JsonProperty("value_ids") 
//	    public List<String> getValue_ids() { 
//			 return this.value_ids; } 
//	    public void setValue_ids(List<String> value_ids) { 
//			 this.value_ids = value_ids; } 
//	    List<String> value_ids;
	    @JsonProperty("values") 
	    public List<String> getValues() { 
			 return this.values; } 
	    public void setValues(List<String> values) { 
			 this.values = values; } 
	    List<String> values;
}
