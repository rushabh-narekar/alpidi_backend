package com.alpidi.model;

import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.alpidi.payload.response.UploadFileResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderTransaction {
	@JsonProperty("transaction_id") 
    public String getTransaction_id() { 
		 return this.transactionid; } 
    public void setTransaction_id(String transaction_id) { 
		 this.transactionid = transaction_id; } 
    String transactionid;
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
    @JsonProperty("seller_user_id") 
    public String getSeller_user_id() { 
		 return this.selleruserid; } 
    public void setSeller_user_id(String selleruserid) { 
		 this.selleruserid = selleruserid; } 
    String selleruserid;
    @JsonProperty("buyer_user_id") 
    public String getBuyer_user_id() { 
		 return this.buyeruserid; } 
    public void setBuyer_user_id(String buyer_user_id) { 
		 this.buyeruserid = buyer_user_id; } 
    String buyeruserid;
    @JsonProperty("create_timestamp") 
    public int getCreate_timestamp() { 
		 return this.create_timestamp; } 
    public void setCreate_timestamp(int create_timestamp) { 
		 this.create_timestamp = create_timestamp; } 
    int create_timestamp;
    @JsonProperty("paid_timestamp") 
    public int getPaid_timestamp() { 
		 return this.paidtimestamp; } 
    public void setPaid_timestamp(int paid_timestamp) { 
		 this.paidtimestamp = paid_timestamp; } 
    int paidtimestamp;
    @JsonProperty("shipped_timestamp") 
    public int getShipped_timestamp() { 
		 return this.shipped_timestamp; } 
    public void setShipped_timestamp(int shipped_timestamp) { 
		 this.shipped_timestamp = shipped_timestamp; } 
    int shipped_timestamp;
    @JsonProperty("quantity") 
    public int getQuantity() { 
		 return this.quantity; } 
    public void setQuantity(int quantity) { 
		 this.quantity = quantity; } 
    int quantity;
    @JsonProperty("listing_image_id") 
    public String getListing_image_id() { 
		 return this.listingimageid; } 
    public void setListing_image_id(String listing_image_id) { 
		 this.listingimageid = listing_image_id; } 
    String listingimageid;
    @JsonProperty("receipt_id") 
    public Object getReceipt_id() { 
		 return this.receipt_id; } 
    public void setReceipt_id(Object receipt_id) { 
		 this.receipt_id = receipt_id; } 
    Object receipt_id;
    @JsonProperty("is_digital") 
    public boolean getIs_digital() { 
		 return this.is_digital; } 
    public void setIs_digital(boolean is_digital) { 
		 this.is_digital = is_digital; } 
    boolean is_digital;
    @JsonProperty("file_data") 
    public String getFile_data() { 
		 return this.file_data; } 
    public void setFile_data(String file_data) { 
		 this.file_data = file_data; } 
    String file_data;
    @JsonProperty("listing_id") 
    public String getListing_id() { 
		 return this.listingid; } 
    public void setListing_id(String listingid) { 
		 this.listingid = listingid; } 
    String listingid;
    @JsonProperty("product_id") 
    public String getProduct_id() { 
		 return this.productid; } 
    public void setProduct_id(String productid) { 
		 this.productid = productid; } 
    String productid;
    @JsonProperty("transaction_type") 
    public String getTransaction_type() { 
		 return this.transactiontype; } 
    public void setTransaction_type(String transactiontype) { 
		 this.transactiontype = transactiontype; } 
    String transactiontype;
    @JsonProperty("price") 
    public ListingPrice getPrice() { 
		 return this.price; } 
    public void setPrice(ListingPrice price) { 
		 this.price = price; } 
    ListingPrice price;
    @JsonProperty("shipping_cost") 
    public OrderShippingCost getShipping_cost() { 
		 return this.shippingcost; } 
    public void setShipping_cost(OrderShippingCost shippingcost) { 
		 this.shippingcost = shippingcost; } 
    OrderShippingCost shippingcost;
    @JsonProperty("variations") 
    public List<OrderVariation> getVariations() { 
		 return this.variations; } 
    public void setVariations(List<OrderVariation> variations) { 
		 this.variations = variations; } 
    List<OrderVariation> variations;
    
    @JsonProperty("inventory") 
    public ListingInventory getinventory() { 
         return this.inventory; } 
    public void setinventory(ListingInventory inventory) { 
         this.inventory = inventory; } 
    ListingInventory inventory;
    
    @JsonProperty("img75") 
    public String getImg75() { 
		 return this.img75; } 
    public void setImg75(String img75) { 
		 this.img75 = img75; } 
    String img75;
    @JsonProperty("sku_number") 
    public String getSku_number() { 
		 return this.skunumber; } 
    public void setSku_number(String skunumber) { 
		 this.skunumber = skunumber; } 
    String skunumber;
    @JsonProperty("vectorfiles") 
    public List<UploadFileResponse> getvectorfiles() { 
         return this.vectorfiles; } 
    public void setvectorfiles(List<UploadFileResponse> vectorfiles) { 
         this.vectorfiles = vectorfiles; } 
    List<UploadFileResponse> vectorfiles;

    @JsonProperty("printHouseUserid") 
    public String getprinthouseid() { 
         return this.printHouseUserid; } 
    public void setprinthouseid(String printHouseUserid) { 
         this.printHouseUserid = printHouseUserid; } 
    String printHouseUserid;
    
    @JsonProperty("printhousename") 
    public String getprinthousename() { 
         return this.printhousename; } 
    public void setprinthousename(String printhousename) { 
         this.printhousename = printhousename; } 
    String printhousename;
    
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
    
    @JsonProperty("productionCost") 
    public Number getproductionCost() { 
		 return this.productionCost; } 
    public void setproductionCost(Number productionCost) { 
		 this.productionCost = productionCost; } 
    Number productionCost;
    
    @JsonProperty("transitstatus") 
    public String gettransitstatus() { 
		 return this.transitstatus; } 
    public void settransitstatus(String transitstatus) { 
		 this.transitstatus = transitstatus; } 
    String transitstatus="pretransit";
    
}
