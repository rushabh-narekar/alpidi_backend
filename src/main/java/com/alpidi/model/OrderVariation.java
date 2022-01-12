package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderVariation {
	@JsonProperty("property_id") 
    public String getProperty_id() { 
		 return this.property_id; } 
    public void setProperty_id(String property_id) { 
		 this.property_id = property_id; } 
    String property_id;
    @JsonProperty("value_id") 
    public String getValue_id() { 
		 return this.value_id; } 
    public void setValue_id(String value_id) { 
		 this.value_id = value_id; } 
    String value_id;
    @JsonProperty("formatted_name") 
    public String getFormatted_name() { 
		 return this.formatted_name; } 
    public void setFormatted_name(String formatted_name) { 
		 this.formatted_name = formatted_name; } 
    String formatted_name;
    @JsonProperty("formatted_value") 
    public String getFormatted_value() { 
		 return this.formatted_value; } 
    public void setFormatted_value(String formatted_value) { 
		 this.formatted_value = formatted_value; } 
    String formatted_value;

}
