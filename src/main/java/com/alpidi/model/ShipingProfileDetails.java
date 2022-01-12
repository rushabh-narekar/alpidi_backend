package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shipingProfiles")
public class ShipingProfileDetails {
	@JsonProperty("shipping_profile_id") 
    public String getShipping_profile_id() { 
		 return this.shippingprofileid; } 
    public void setShipping_profile_id(String shipping_profile_id) { 
		 this.shippingprofileid = shipping_profile_id; } 
    String shippingprofileid;
    @JsonProperty("shop_id") 
    public String getShop_id() { 
		 return this.shopid; } 
    public void setShop_id(String shop_id) { 
		 this.shopid = shop_id; } 
    String shopid;
    @JsonProperty("title") 
    public String getTitle() { 
		 return this.title; } 
    public void setTitle(String title) { 
		 this.title = title; } 
    String title;
    @JsonProperty("user_id") 
    public String getUser_id() { 
		 return this.userid; } 
    public void setUser_id(String user_id) { 
		 this.userid = user_id; } 
    String userid;
    @JsonProperty("min_processing_days") 
    public int getMin_processing_days() { 
		 return this.min_processing_days; } 
    public void setMin_processing_days(int min_processing_days) { 
		 this.min_processing_days = min_processing_days; } 
    int min_processing_days;
    @JsonProperty("max_processing_days") 
    public int getMax_processing_days() { 
		 return this.max_processing_days; } 
    public void setMax_processing_days(int max_processing_days) { 
		 this.max_processing_days = max_processing_days; } 
    int max_processing_days;
    @JsonProperty("processing_days_display_label") 
    public String getProcessing_days_display_label() { 
		 return this.processing_days_display_label; } 
    public void setProcessing_days_display_label(String processing_days_display_label) { 
		 this.processing_days_display_label = processing_days_display_label; } 
    String processing_days_display_label;
    @JsonProperty("origin_country_iso") 
    public String getOrigin_country_iso() { 
		 return this.origin_country_iso; } 
    public void setOrigin_country_iso(String origin_country_iso) { 
		 this.origin_country_iso = origin_country_iso; } 
    String origin_country_iso;
    @JsonProperty("origin_postal_code") 
    public String getOrigin_postal_code() { 
		 return this.origin_postal_code; } 
    public void setOrigin_postal_code(String origin_postal_code) { 
		 this.origin_postal_code = origin_postal_code; } 
    String origin_postal_code;
    @JsonProperty("profile_type") 
    public int getProfile_type() { 
		 return this.profile_type; } 
    public void setProfile_type(int profile_type) { 
		 this.profile_type = profile_type; } 
    int profile_type;
    @JsonProperty("is_deleted") 
    public boolean getIs_deleted() { 
		 return this.is_deleted; } 
    public void setIs_deleted(boolean is_deleted) { 
		 this.is_deleted = is_deleted; } 
    boolean is_deleted;
	
	  @JsonProperty("shipping_profile_destinations") public
	  List<Object> getShipping_profile_destinations() { return
	  this.shipping_profile_destinations; } public void
	  setShipping_profile_destinations(List<Object>
	  shipping_profile_destinations) { this.shipping_profile_destinations =
	  shipping_profile_destinations; } List<Object>
	  shipping_profile_destinations;
	  
	  @JsonProperty("shipping_profile_upgrades") public List<Object>
	  getShipping_profile_upgrades() { return this.shipping_profile_upgrades; }
	  public void setShipping_profile_upgrades(List<Object>
	  shipping_profile_upgrades) { this.shipping_profile_upgrades =
	  shipping_profile_upgrades; } List<Object> shipping_profile_upgrades;
	 
}
