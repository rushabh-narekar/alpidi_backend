package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "printSlipSetting")
public class SettingPrintSlip {
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
    
    }
