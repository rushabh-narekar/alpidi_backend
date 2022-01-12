package com.alpidi.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
@Document(collection = "customizedOrders")
public class CustomizedOrders {
	@Id
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonProperty("userid") 
    public String getuserid() { 
		 return this.userid; 
	} 
    public void setuserid(String userid) { 
		 this.userid = userid; 
	} 
    String userid;
    
	@JsonProperty("orderid") 
    public String getorderid() { 
		 return this.orderid; 
	} 
    public void setorderid(String orderid) { 
		 this.orderid = orderid; 
	} 
    @Indexed(unique=true)
    String orderid;
    
    @JsonProperty("isAssigned") 
    public Boolean getisAssigned() { 
		 return this.isAssigned; } 
    public void setisAssigned(Boolean isAssigned) { 
		 this.isAssigned = isAssigned; } 
    Boolean isAssigned = false;
    
    @JsonProperty("transactions") 
    public List<CutomizedOrderTransaction> gettransaction() { 
		 return this.transaction; 
	} 
    public void settransaction(List<CutomizedOrderTransaction> transaction) { 
		 this.transaction = transaction; 
	} 
    List<CutomizedOrderTransaction> transaction;
}
