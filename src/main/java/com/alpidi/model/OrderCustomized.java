package com.alpidi.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "orderCutomization")
public class OrderCustomized {
	@JsonProperty("shopUserId") 
    public String getShopUserId() { 
		 return this.shopUserId; } 
    public void setShopUserId(String shopUserId) { 
		 this.shopUserId = shopUserId; } 
    String shopUserId;
    
    @JsonProperty("printHouseUserid") 
    public String getPrintHouseUserid() { 
		 return this.printHouseUserId; 
	} 
    public void setPrintHouseUserid(String printHouseUserid) { 
		 this.printHouseUserId = printHouseUserid; 
	} 
    String printHouseUserId;
    
    @JsonProperty("orderid") 
    public String getOrderid() { 
		 return this.orderid; 
	} 
    public void setOrderid(String orderid) { 
		 this.orderid = orderid; 
	} 
    String orderid;
    
    @JsonProperty("shopid") 
    public String getShopid() { 
		 return this.shopid; 
	} 
    public void setShopid(String shopid) { 
		 this.shopid = shopid; 
	} 
    String shopid;
    
    @JsonProperty("transactions") 
    public List<OrderTransaction> getTransactions() { 
		 return this.transactions; } 
    public void setTransactions(List<OrderTransaction> transactions) { 
		 this.transactions = transactions; } 
    List<OrderTransaction> transactions;
}
