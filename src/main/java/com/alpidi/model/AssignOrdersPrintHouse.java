package com.alpidi.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "assignPrinthouseOrders")
public class AssignOrdersPrintHouse {
	@JsonProperty("shopUserId") 
    public String getShopUserId() { 
		 return this.shopUserId; } 
    public void setShopUserId(String shopUserId) { 
		 this.shopUserId = shopUserId; } 
    String shopUserId;
    
    @JsonProperty("shopid") 
    public String getShopid() { 
		 return this.shopid; 
	} 
    public void setShopid(String shopid) { 
		 this.shopid = shopid; 
	} 
    String shopid;
    
    @JsonProperty("orderid") 
    public String getOrderid() { 
		 return this.orderid; 
	} 
    public void setOrderid(String orderid) { 
		 this.orderid = orderid; 
	} 
    String orderid;
    
    @JsonProperty("listingid") 
    public String getlistingid() { 
		 return this.listingid; 
	} 
    public void setlistingid(String listingid) { 
		 this.listingid = listingid; 
	} 
    String listingid;
    
    @JsonProperty("printHouseUserid") 
    public String getPrintHouseUserid() { 
		 return this.printHouseUserId; 
	} 
    public void setPrintHouseUserid(String printHouseUserid) { 
		 this.printHouseUserId = printHouseUserid; 
	} 
    String printHouseUserId;
    
    @JsonProperty("printhouseOrders") 
    public List<OrderDetails> getPrinthouseOrders() { 
		 return this.printhouseOrders; 
	} 
    public void setPrinthouseOrders(List<OrderDetails> printhouseOrders) { 
		 this.printhouseOrders = printhouseOrders; 
	} 
    List<OrderDetails> printhouseOrders;
    
    
    
}
