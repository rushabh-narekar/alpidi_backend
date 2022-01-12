package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "ProductionPartner")
public class ProductionPartnerDetails {
	@JsonProperty("production_partner_id") 
    public String getProduction_partner_id() { 
		 return this.productionpartnerid; } 
    public void setProduction_partner_id(String production_partner_id) { 
		 this.productionpartnerid = production_partner_id; } 
    String productionpartnerid;
    
    @JsonProperty("partner_name") 
    public String getpartner_name() { 
		 return this.partnername; } 
    public void setPartner_name(String partnername) { 
		 this.partnername = partnername; } 
    String partnername;
    
    @JsonProperty("location") 
    public String getProducts() { 
		 return this.location; } 
    public void setProducts(String location) { 
		 this.location = location; } 
    String location;
}
