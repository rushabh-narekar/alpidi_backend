package com.alpidi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CutomizedOrderTransaction {
	@JsonProperty("listingid") 
    public String getListing_id() { 
		 return this.listingid; } 
    public void setListing_id(String listingid) { 
		 this.listingid = listingid; } 
    String listingid;
    
    @JsonProperty("noteforseller") 
    public String getnoteforseller() { 
		 return this.noteforseller; } 
    public void setnoteforseller(String noteforseller) { 
		 this.noteforseller = noteforseller; } 
    String noteforseller;
    
    @JsonProperty("isApprovedByPrinthouse") 
    public Boolean getisApprovedByPrinthouse() { 
		 return this.isApprovedByPrinthouse; } 
    public void setisApprovedByPrinthouse(Boolean isApprovedByPrinthouse) { 
		 this.isApprovedByPrinthouse = isApprovedByPrinthouse; } 
    Boolean isApprovedByPrinthouse = false;
    
    @JsonProperty("printhouseid") 
    public String getprinthouseid() { 
		 return this.printhouseid; } 
    public void setprinthouseid(String printhouseid) { 
		 this.printhouseid = printhouseid; } 
    String printhouseid;
    
    @JsonProperty("quantity") 
    public Number getquantity() { 
		 return this.quantity; } 
    public void setquantity(Number quantity) { 
		 this.quantity = quantity; } 
    Number quantity;
    
    @JsonProperty("skunumber") 
    public String getskunumber() { 
		 return this.skunumber; } 
    public void setskunumber(String skunumber) { 
		 this.skunumber = skunumber; } 
    String skunumber;
    
    @JsonProperty("progress") 
    public int getprogress() { 
		 return this.progress; } 
    public void setprogress(int progress) { 
		 this.progress = progress; } 
    int progress = 1;
    
    @JsonProperty("transitstatus") 
    public String gettransitstatus() { 
		 return this.transitstatus; } 
    public void settransitstatus(String transitstatus) { 
		 this.transitstatus = transitstatus; } 
    String transitstatus="pretransit";
    
    @JsonProperty("variations") 
    public List<OrderVariation> getVariations() { 
		 return this.variations; } 
    public void setVariations(List<OrderVariation> variations) { 
		 this.variations = variations; } 
    List<OrderVariation> variations;
}
