package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceLevel {
	@JsonProperty("name") 
    public String getname() { 
		 return this.name; } 
    public void setname(String name) { 
		 this.name = name; } 
    String name;
    
    @JsonProperty("token") 
    public String gettoken() { 
		 return this.token; } 
    public void settoken(String token) { 
		 this.token = token; } 
    String token;
    
    @JsonProperty("terms") 
    public String getterms() { 
		 return this.terms; } 
    public void setterms(String terms) { 
		 this.terms = terms; } 
    String terms;
    
    @JsonProperty("extended_token") 
    public String getextended_token() { 
		 return this.extended_token; } 
    public void setextended_token(String extended_token) { 
		 this.extended_token = extended_token; } 
    String extended_token;
    
    @JsonProperty("parent_servicelevel") 
    public String getparent_servicelevel() { 
		 return this.parent_servicelevel; } 
    public void setparent_servicelevel(String parent_servicelevel) { 
		 this.parent_servicelevel = parent_servicelevel; } 
    String parent_servicelevel;
}
