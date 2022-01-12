package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "configueShipment")
public class ConfigureShipment {
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

    @JsonProperty("shipaddressid") 
    public String getshipaddressid() { 
		 return this.shipaddressid; 
	} 
    public void setshipaddressid(String shipaddressid) { 
		 this.shipaddressid = shipaddressid; 
	} 
    String shipaddressid;
    
    @JsonProperty("shipdate") 
    public String getshipdate() { 
		 return this.shipdate; 
	} 
    public void setshipdate(String shipdate) { 
		 this.shipdate = shipdate; 
	} 
    String shipdate;
    
    @JsonProperty("service") 
    public String getservice() { 
		 return this.service; 
	} 
    public void setservice(String service) { 
		 this.service = service; 
	} 
    String service;
    
    @JsonProperty("packages") 
    public String getpackage() { 
		 return this.packages; 
	} 
    public void setpackage(String packages) { 
		 this.packages = packages; 
	} 
    String packages;
    
    @JsonProperty("length") 
    public String getlength() { 
		 return this.length; 
	} 
    public void setlength(String length) { 
		 this.length = length; 
	} 
    String length;

    @JsonProperty("width") 
    public String getwidth() { 
		 return this.width; 
	} 
    public void setwidth(String width) { 
		 this.width = width; 
	} 
    String width;
    
    @JsonProperty("height") 
    public String getheight() { 
		 return this.height; 
	} 
    public void setheight(String height) { 
		 this.height = height; 
	} 
    String height;
    
    @JsonProperty("weight") 
    public String getweight() { 
		 return this.weight; 
	} 
    public void setweight(String weight) { 
		 this.weight = weight; 
	} 
    String weight;

    @JsonProperty("massunit") 
    public String getmassunit() { 
		 return this.massunit; 
	} 
    public void setmassunit(String massunit) { 
		 this.massunit = massunit; 
	} 
    String massunit;
    
    @JsonProperty("confirmation") 
    public String getconfirmation() { 
		 return this.confirmation; 
	} 
    public void setconfirmation(String confirmation) { 
		 this.confirmation = confirmation; 
	} 
    String confirmation;
    
    @JsonProperty("shipingTransaction") 
    public Transaction2 getshipingTransaction() { 
		 return this.shipingTransaction; 
	} 
    public void setshipingTransaction(Transaction2 shipingTransaction) { 
		 this.shipingTransaction = shipingTransaction; 
	} 
    Transaction2 shipingTransaction;
}
