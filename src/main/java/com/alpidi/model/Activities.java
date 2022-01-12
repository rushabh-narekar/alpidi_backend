package com.alpidi.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "activities")
public class Activities {
	@JsonProperty("id") 
    public String getid() { 
		 return this.id; 
	} 
    public void setid(String id) { 
		 this.id = id; 
	} 
    String id;
    
    @JsonProperty("loginuserid") 
    public String getloginuserid() { 
		 return this.loginuserid; 
	} 
    public void setloginuserid(String loginuserid) { 
		 this.loginuserid = loginuserid; 
	} 
    String loginuserid;
    
    @JsonProperty("orderid") 
    public String getorderid() { 
		 return this.orderid; 
	} 
    public void setorderid(String orderid) { 
		 this.orderid = orderid; 
	} 
    String orderid;
    
    @JsonProperty("inventoryid") 
    public String getinventoryid() { 
		 return this.inventoryid; 
	} 
    public void setinventoryid(String inventoryid) { 
		 this.inventoryid = inventoryid; 
	} 
    String inventoryid;
    
    @JsonProperty("sendQuantity") 
    public Number getsendQuantity() { 
		 return this.sendQuantity; 
	} 
    public void setsendQuantity(Number sendQuantity) { 
		 this.sendQuantity = sendQuantity; 
	} 
    Number sendQuantity;
    
    @JsonProperty("orderStatus") 
    public int getorderStatus() { 
		 return this.orderStatus; 
	} 
    public void setorderStatus(int orderStatus) { 
		 this.orderStatus = orderStatus; 
	} 
    int orderStatus;
    
    @JsonProperty("updatedDate") 
    public Date getupdatedDate() { 
		 return this.updatedDate; 
	} 
    public void setupdatedDate(Date updatedDate) { 
		 this.updatedDate = updatedDate; 
	} 
    Date updatedDate=new Date();
}
