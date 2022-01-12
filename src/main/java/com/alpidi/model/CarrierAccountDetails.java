package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "carrierAccounts")
public class CarrierAccountDetails {
	@JsonProperty("loginuserid") 
    public String getloginuserid() { 
		 return this.loginuserid; 
	} 
    public void setloginuserid(String loginuserid) { 
		 this.loginuserid = loginuserid; 
	} 
    String loginuserid;
    
	@JsonProperty("carrier") 
    public String getCarrier() { 
		 return this.carrier; } 
    public void setCarrier(String carrier) { 
		 this.carrier = carrier; } 
    String carrier;
    @JsonProperty("object_id") 
    public String getObject_id() { 
		 return this.objectid; } 
    public void setObject_id(String object_id) { 
		 this.objectid = object_id; } 
    String objectid;
    @JsonProperty("object_owner") 
    public String getObject_owner() { 
		 return this.objectowner; } 
    public void setObject_owner(String object_owner) { 
		 this.objectowner = object_owner; } 
    String objectowner;
    @JsonProperty("account_id") 
    public String getAccount_id() { 
		 return this.accountid; } 
    public void setAccount_id(String account_id) { 
		 this.accountid = account_id; } 
    String accountid;
    @JsonProperty("parameters") 
    public Object getParameters() { 
		 return this.parameters; } 
    public void setParameters(Object parameters) { 
		 this.parameters = parameters; } 
    Object parameters;
    @JsonProperty("test") 
    public boolean getTest() { 
		 return this.test; } 
    public void setTest(boolean test) { 
		 this.test = test; } 
    boolean test;
    @JsonProperty("active") 
    public boolean getActive() { 
		 return this.active; } 
    public void setActive(boolean active) { 
		 this.active = active; } 
    boolean active;
    @JsonProperty("is_shippo_account") 
    public boolean getIs_shippo_account() { 
		 return this.isshippoaccount; } 
    public void setIs_shippo_account(boolean is_shippo_account) { 
		 this.isshippoaccount = is_shippo_account; } 
    boolean isshippoaccount;
    @JsonProperty("metadata") 
    public String getMetadata() { 
		 return this.metadata; } 
    public void setMetadata(String metadata) { 
		 this.metadata = metadata; } 
    String metadata;
}
