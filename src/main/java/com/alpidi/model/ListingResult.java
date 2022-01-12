package com.alpidi.model; 
import com.alpidi.payload.response.UploadFileResponse;
import com.fasterxml.jackson.annotation.JsonProperty; 
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document; 

@Document(collection = "Listing")
public class ListingResult{
	@Id
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    @JsonProperty("listing_id")
    public String getListingid() { 
         return this.listingid; } 
    public void setListingid(String listingid) { 
         this.listingid = listingid; } 
    @Indexed(unique=true)
    String listingid;
    @JsonProperty("user_id") 
    public String getUserid() { 
         return this.userid; } 
    public void setUserid(String userid) { 
         this.userid = userid; } 
    String userid;
    @JsonProperty("shop_id") 
    public String getShopid() { 
         return this.shopid; } 
    public void setShopid(String shopid) { 
         this.shopid = shopid; } 
    String shopid;
    @JsonProperty("title") 
    public String getTitle() { 
         return this.title; } 
    public void setTitle(String title) { 
         this.title = title; } 
    String title;
    @JsonProperty("description") 
    public String getDescription() { 
         return this.description; } 
    public void setDescription(String description) { 
         this.description = description; } 
    String description;
    @JsonProperty("state") 
    public String getState() { 
         return this.state; } 
    public void setState(String state) { 
         this.state = state; } 
    String state;
    @JsonProperty("creation_timestamp") 
    public int getCreation_timestamp() { 
         return this.creation_timestamp; } 
    public void setCreation_timestamp(int creation_timestamp) { 
         this.creation_timestamp = creation_timestamp; } 
    int creation_timestamp;
    @JsonProperty("ending_timestamp") 
    public int getEnding_timestamp() { 
         return this.ending_timestamp; } 
    public void setEnding_timestamp(int ending_timestamp) { 
         this.ending_timestamp = ending_timestamp; } 
    int ending_timestamp;
    @JsonProperty("original_creation_timestamp") 
    public int getOriginal_creation_timestamp() { 
         return this.original_creation_timestamp; } 
    public void setOriginal_creation_timestamp(int original_creation_timestamp) { 
         this.original_creation_timestamp = original_creation_timestamp; } 
    int original_creation_timestamp;
    @JsonProperty("last_modified_timestamp") 
    public int getLast_modified_timestamp() { 
         return this.lastmodifiedtimestamp; } 
    public void setLast_modified_timestamp(int lastmodifiedtimestamp) { 
         this.lastmodifiedtimestamp = lastmodifiedtimestamp; } 
    int lastmodifiedtimestamp;
    @JsonProperty("state_timestamp") 
    public int getState_timestamp() { 
         return this.state_timestamp; } 
    public void setState_timestamp(int state_timestamp) { 
         this.state_timestamp = state_timestamp; } 
    int state_timestamp;
    @JsonProperty("quantity") 
    public int getQuantity() { 
         return this.quantity; } 
    public void setQuantity(int quantity) { 
         this.quantity = quantity; } 
    int quantity;
    @JsonProperty("shop_section_id") 
    public String getShop_section_id() { 
         return this.shopsectionid; } 
    public void setShop_section_id(String shopsectionid) { 
         this.shopsectionid = shopsectionid; } 
    String shopsectionid;
    @JsonProperty("featured_rank") 
    public int getFeatured_rank() { 
         return this.featured_rank; } 
    public void setFeatured_rank(int featured_rank) { 
         this.featured_rank = featured_rank; } 
    int featured_rank;
    @JsonProperty("url") 
    public String getUrl() { 
         return this.url; } 
    public void setUrl(String url) { 
         this.url = url; } 
    String url;
    @JsonProperty("num_favorers") 
    public int getNum_favorers() { 
         return this.num_favorers; } 
    public void setNum_favorers(int num_favorers) { 
         this.num_favorers = num_favorers; } 
    int num_favorers;
    @JsonProperty("non_taxable") 
    public boolean getNon_taxable() { 
         return this.non_taxable; } 
    public void setNon_taxable(boolean non_taxable) { 
         this.non_taxable = non_taxable; } 
    boolean non_taxable;
    @JsonProperty("is_customizable") 
    public boolean getIs_customizable() { 
         return this.is_customizable; } 
    public void setIs_customizable(boolean is_customizable) { 
         this.is_customizable = is_customizable; } 
    boolean is_customizable;
    @JsonProperty("listing_type") 
    public int getListing_type() { 
         return this.listing_type; } 
    public void setListing_type(int listing_type) { 
         this.listing_type = listing_type; } 
    int listing_type;
    @JsonProperty("tags") 
    public List<String> getTags() { 
         return this.tags; } 
    public void setTags(List<String> tags) { 
         this.tags = tags; } 
    List<String> tags;
    @JsonProperty("materials") 
    public List<String> getMaterials() { 
         return this.materials; } 
    public void setMaterials(List<String> materials) { 
         this.materials = materials; } 
    List<String> materials;
    @JsonProperty("shipping_profile_id") 
    public String getShipping_profile_id() { 
         return this.shippingprofileid; } 
    public void setShipping_profile_id(String shipping_profile_id) { 
         this.shippingprofileid = shipping_profile_id; } 
    String shippingprofileid;
    @JsonProperty("processing_min") 
    public int getProcessing_min() { 
         return this.processing_min; } 
    public void setProcessing_min(int processing_min) { 
         this.processing_min = processing_min; } 
    int processing_min;
    @JsonProperty("processing_max") 
    public int getProcessing_max() { 
         return this.processing_max; } 
    public void setProcessing_max(int processing_max) { 
         this.processing_max = processing_max; } 
    int processing_max;
    @JsonProperty("who_made") 
    public String getWho_made() { 
         return this.who_made; } 
    public void setWho_made(String who_made) { 
         this.who_made = who_made; } 
    String who_made;
    @JsonProperty("when_made") 
    public String getWhen_made() { 
         return this.when_made; } 
    public void setWhen_made(String when_made) { 
         this.when_made = when_made; } 
    String when_made;
    @JsonProperty("is_supply") 
    public boolean getIs_supply() { 
         return this.is_supply; } 
    public void setIs_supply(boolean is_supply) { 
         this.is_supply = is_supply; } 
    boolean is_supply;
    @JsonProperty("item_weight") 
    public Object getItem_weight() { 
         return this.item_weight; } 
    public void setItem_weight(Object item_weight) { 
         this.item_weight = item_weight; } 
    Object item_weight;
    @JsonProperty("item_weight_unit") 
    public Object getItem_weight_unit() { 
         return this.item_weight_unit; } 
    public void setItem_weight_unit(Object item_weight_unit) { 
         this.item_weight_unit = item_weight_unit; } 
    Object item_weight_unit;
    @JsonProperty("item_length") 
    public Object getItem_length() { 
         return this.item_length; } 
    public void setItem_length(Object item_length) { 
         this.item_length = item_length; } 
    Object item_length;
    @JsonProperty("item_width") 
    public Object getItem_width() { 
         return this.item_width; } 
    public void setItem_width(Object item_width) { 
         this.item_width = item_width; } 
    Object item_width;
    @JsonProperty("item_height") 
    public Object getItem_height() { 
         return this.item_height; } 
    public void setItem_height(Object item_height) { 
         this.item_height = item_height; } 
    Object item_height;
    @JsonProperty("item_dimensions_unit") 
    public Object getItem_dimensions_unit() { 
         return this.item_dimensions_unit; } 
    public void setItem_dimensions_unit(Object item_dimensions_unit) { 
         this.item_dimensions_unit = item_dimensions_unit; } 
    Object item_dimensions_unit;
    @JsonProperty("is_private") 
    public boolean getIs_private() { 
         return this.is_private; } 
    public void setIs_private(boolean is_private) { 
         this.is_private = is_private; } 
    boolean is_private;
    @JsonProperty("recipient") 
    public Object getRecipient() { 
         return this.recipient; } 
    public void setRecipient(Object recipient) { 
         this.recipient = recipient; } 
    Object recipient;
    @JsonProperty("occasion") 
    public Object getOccasion() { 
         return this.occasion; } 
    public void setOccasion(Object occasion) { 
         this.occasion = occasion; } 
    Object occasion;
    @JsonProperty("style") 
    public List<Object> getStyle() { 
         return this.style; } 
    public void setStyle(List<Object> style) { 
         this.style = style; } 
    List<Object> style;
    @JsonProperty("file_data") 
    public String getFile_data() { 
         return this.file_data; } 
    public void setFile_data(String file_data) { 
         this.file_data = file_data; } 
    String file_data;
    @JsonProperty("has_variations") 
    public boolean getHas_variations() { 
         return this.has_variations; } 
    public void setHas_variations(boolean has_variations) { 
         this.has_variations = has_variations; } 
    boolean has_variations;
    @JsonProperty("should_auto_renew") 
    public boolean getShould_auto_renew() { 
         return this.should_auto_renew; } 
    public void setShould_auto_renew(boolean should_auto_renew) { 
         this.should_auto_renew = should_auto_renew; } 
    boolean should_auto_renew;
    @JsonProperty("language") 
    public String getLanguage() { 
         return this.language; } 
    public void setLanguage(String language) { 
         this.language = language; } 
    String language;
    @JsonProperty("price") 
    public ListingPrice getPrice() { 
         return this.price; } 
    public void setPrice(ListingPrice price) { 
         this.price = price; } 
    ListingPrice price;
    @JsonProperty("taxonomy_id") 
    public int getTaxonomy_id() { 
         return this.taxonomy_id; } 
    public void setTaxonomy_id(int taxonomy_id) { 
         this.taxonomy_id = taxonomy_id; } 
    int taxonomy_id;
    @JsonProperty("img75") 
    public String getimg75() { 
         return this.img75; } 
    public void setimg75(String img75) { 
         this.img75 = img75; } 
    String img75;
    
    @JsonProperty("images") 
    public List<ShopImage> getImages() { 
         return this.images; } 
    public void setImages(List<ShopImage> shopImage) { 
    	System.out.print(shopImage);
         this.images = shopImage; } 
    List<ShopImage> images;

    @JsonProperty("property") 
    public List<ListingProperty> getproperty() { 
         return this.property; } 
    public void setproperty(List<ListingProperty> property) { 
         this.property = property; } 
    List<ListingProperty> property;
    
    @JsonProperty("vectorfiles") 
    public List<UploadFileResponse> getvectorfiles() { 
         return this.vectorfiles; } 
    public void setvectorfiles(List<UploadFileResponse> vectorfiles) { 
         this.vectorfiles = vectorfiles; } 
    List<UploadFileResponse> vectorfiles;
    
    @JsonProperty("isvectorfileupload") 
    public Boolean getisvectorfileupload() { 
         return this.isvectorfileupload; } 
    public void setisvectorfileupload(Boolean isvectorfileupload) { 
         this.isvectorfileupload = isvectorfileupload; } 
    Boolean isvectorfileupload;
    
    @JsonProperty("is_vectoricon") 
    public Boolean getis_vectoricon() { 
         return this.is_vectoricon; } 
    public void setis_vectoricon(Boolean is_vectoricon) { 
         this.is_vectoricon = is_vectoricon; } 
    Boolean is_vectoricon;
    
    @JsonProperty("sku_number") 
    public String getSku_number() { 
		 return this.skunumber; } 
    public void setSku_number(String skunumber) { 
		 this.skunumber = skunumber; } 
    String skunumber;
    
    @JsonProperty("priority") 
    public int getPriority() { 
		 return this.priority; } 
    public void setPriority(int priority) { 
		 this.priority = priority; } 
    int priority;
    
    @JsonProperty("production_partner") 
    public List<ProductionPartnerDetails> getproduction_partner() { 
		 return this.production_partner; } 
    public void setproduction_partner(List<ProductionPartnerDetails> production_partner) { 
		 this.production_partner = production_partner; } 
    List<ProductionPartnerDetails> production_partner;
    
    @JsonProperty("defaultprinthouse") 
    public String getDefaultprinthouse() { 
		 return this.defaultprinthouse; } 
    public void setDefaultprinthouse(String defaultprinthouse) { 
		 this.defaultprinthouse = defaultprinthouse; } 
    String defaultprinthouse;
    
    @JsonProperty("ListingProperties") 
    public List<ListingProperties> getListingProperties() { 
		 return this.ListingProperties; } 
    public void setListingProperties(List<ListingProperties> ListingProperties) { 
		 this.ListingProperties = ListingProperties; } 
    List<ListingProperties> ListingProperties;
    
    
}
