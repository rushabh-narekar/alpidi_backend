package com.alpidi.model;

import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListingProducts {
	@JsonProperty("product_id") 
    public String getProduct_id() { 
		 return this.product_id; } 
    public void setProduct_id(String product_id) { 
		 this.product_id = product_id; } 
    String product_id;
    @JsonProperty("sku") 
    public String getSku() { 
		 return this.sku; } 
    public void setSku(String sku) { 
		 this.sku = sku; } 
    String sku;
    @JsonProperty("is_deleted") 
    public boolean getIs_deleted() { 
		 return this.is_deleted; } 
    public void setIs_deleted(boolean is_deleted) { 
		 this.is_deleted = is_deleted; } 
    boolean is_deleted;
	
	  @JsonProperty("offerings") 
	  public List<Object> getOfferings() { 
		  return this.offerings; 
	  }
	  public void setOfferings(List<Object> offerings) {
	  this.offerings = offerings; } 
	  List<Object> offerings;
	  
	  @JsonProperty("property_values") 
	  public List<Object> getProperty_values() {
	  return this.property_values; } 
	  public void setProperty_values(List<Object>
	  property_values) { this.property_values = property_values; } 
	  List<Object> property_values;
	 
}
