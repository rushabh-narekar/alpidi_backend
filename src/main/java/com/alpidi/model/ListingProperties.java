package com.alpidi.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "listingVectorFiles")
public class ListingProperties {
	@Id
	private String id;

	@NotBlank
	@Size(max = 20)
	private String shopid;
	
	@NotBlank
	@Size(max = 20)
	private String listingid;
	
	@NotBlank
	private String lstimagesid;
	
	@NotBlank
	@Size(max = 20)
	private List<String> filename;
	
	String printhouseid;
	String skunumber;
	
	public ListingProperties(String shopid,String listingid,List<String> filename, String lstimagesid)
	{
		this.shopid=shopid;
		this.listingid=listingid;
		this.filename=filename;
		this.lstimagesid = lstimagesid;
	}
	
	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	public String getlistingid() {
		return shopid;
	}

	public void setlistingid(String listingid) {
		this.listingid = listingid;
	}
	
	public String getListingImages() {
		return lstimagesid;
	}

	public void setListingImages(String lstimagesid) {
		this.lstimagesid = lstimagesid;
	}
	
	public List<String> getfilename() {
		return filename;
	}
	public void setfilename(List<String> filename) {
		this.filename = filename;
	}
	
	public String getPrinthouseId() {
		return printhouseid;
	}

	public void setPrinthouseId(String printhouseid) {
		this.printhouseid = printhouseid;
	}
 
    public String getSku_number() { 
		 return this.skunumber; } 
    public void setSku_number(String skunumber) { 
		 this.skunumber = skunumber; } 
    
}
