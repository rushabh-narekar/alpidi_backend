package com.alpidi.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "ListingInventory")
public class ListingInventory {
	@JsonProperty("listingid") 
    public String getListingid() { 
		 return this.listingid; } 
    public void setListingid(String listingid) { 
		 this.listingid = listingid; } 
    String listingid;
	@JsonProperty("products") 
    public List<ListingProducts> getProducts() { 
		 return this.products; } 
    public void setProducts(List<ListingProducts> products) { 
		 this.products = products; } 
    List<ListingProducts> products;
    @JsonProperty("price_on_property") 
    public List<Object> getPrice_on_property() { 
		 return this.price_on_property; } 
    public void setPrice_on_property(List<Object> price_on_property) { 
		 this.price_on_property = price_on_property; } 
    List<Object> price_on_property;
    @JsonProperty("quantity_on_property") 
    public List<Object> getQuantity_on_property() { 
		 return this.quantity_on_property; } 
    public void setQuantity_on_property(List<Object> quantity_on_property) { 
		 this.quantity_on_property = quantity_on_property; } 
    List<Object> quantity_on_property;
    @JsonProperty("sku_on_property") 
    public List<Object> getSku_on_property() { 
		 return this.sku_on_property; } 
    public void setSku_on_property(List<Object> sku_on_property) { 
		 this.sku_on_property = sku_on_property; } 
    List<Object> sku_on_property;
    @JsonProperty("listing") 
    public Object getListing() { 
		 return this.listing; } 
    public void setListing(Object listing) { 
		 this.listing = listing; } 
    Object listing;

}
