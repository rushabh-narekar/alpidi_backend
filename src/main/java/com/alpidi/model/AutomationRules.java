package com.alpidi.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;

@Document(collection = "AutomationRules")
public class AutomationRules {
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
    
	@JsonProperty("rulename") 
    public String getrulename() { 
		 return this.rulename; 
	} 
    public void setrulename(String rulename) { 
		 this.rulename = rulename; 
	} 
    String rulename;
    
    @JsonProperty("status") 
    public Boolean getstatus() { 
		 return this.status; 
	} 
    public void setstatus(Boolean status) { 
		 this.status = status; 
	} 
    Boolean status;
    
    @JsonProperty("ordermatchcriteria") 
    public String getordermatchcriteria() { 
		 return this.ordermatchcriteria; 
	} 
    public void setordermatchcriteria(String ordermatchcriteria) { 
		 this.ordermatchcriteria = ordermatchcriteria; 
	} 
    String ordermatchcriteria;
    
    @JsonProperty("specificariteria") 
    public String getspecificariteria() { 
		 return this.specificariteria; 
	} 
    public void setspecificariteria(String specificariteria) { 
		 this.specificariteria = specificariteria; 
	} 
    String specificariteria;
    
    @JsonProperty("condition") 
    public String getcondition() { 
		 return this.condition; 
	} 
    public void setcondition(String condition) { 
		 this.condition = condition; 
	} 
    String condition;
    
    @JsonProperty("totalquntity") 
    public String gettotalquntity() { 
		 return this.totalquntity; 
	} 
    public void settotalquntity(String totalquntity) { 
		 this.totalquntity = totalquntity; 
	} 
    String totalquntity;
    
    @JsonProperty("addressid") 
    public String getaddressid() { 
		 return this.addressid; 
	} 
    public void setaddressid(String addressid) { 
		 this.addressid = addressid; 
	} 
    String addressid;
    
    @JsonProperty("addressess") 
    public ShipAddress getaddressess() { 
		 return this.addressess; 
	} 
    public void setaddressess(ShipAddress shipAddress) { 
		 this.addressess = shipAddress; 
	} 
    ShipAddress addressess;
    
    @JsonProperty("actiontype") 
    public Object getactiontype() { 
		 return this.actiontype; 
	} 
    public void setactiontype(Object actiontype) { 
		 this.actiontype = actiontype; 
	} 
    Object actiontype;
    
    @JsonProperty("weightlb") 
    public String getweightlb() { 
		 return this.weightlb; 
	} 
    public void setweightlb(String weightlb) { 
		 this.weightlb = weightlb; 
	} 
    String weightlb;
    
    @JsonProperty("weightoz") 
    public String getweightoz() { 
		 return this.weightoz; 
	} 
    public void setweightoz(String weightoz) { 
		 this.weightoz = weightoz; 
	} 
    String weightoz;
    
    @JsonProperty("isdelete") 
    public Boolean getisdelete() { 
		 return this.isdelete; 
	} 
    public void setisdelete(Boolean isdelete) { 
		 this.isdelete = isdelete; 
	} 
    Boolean isdelete=false;    
}
