package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "AdditionalSettings")
public class AdditionalSettings {
	@JsonProperty("userid") 
    public String getUserid() { 
		 return this.userid; } 
    public void setUserid(String userid) { 
		 this.userid = userid; } 
    String userid;
    
	@JsonProperty("carrierServiceId") 
    public String getCarrierServiceId() { 
		 return this.carrierServiceId; } 
    public void setCarrierServiceId(String carrierServiceId) { 
		 this.carrierServiceId = carrierServiceId; } 
    String carrierServiceId;
    
    @JsonProperty("selectedprinthouseid") 
    public String[] getselectedprinthouseid() { 
		 return this.selectedprinthouseid; 
	} 
    public void setselectedprinthouseid(String[] selectedprinthouseid) { 
		 this.selectedprinthouseid = selectedprinthouseid; 
	} 
    String[] selectedprinthouseid=null; 
    
    @JsonProperty("printHouseUserId") 
    public String getPrintHouseUserid() { 
		 return this.printHouseUserId; 
	} 
    public void setPrintHouseUserid(String pritnHouseuserId) { 
		 this.printHouseUserId = pritnHouseuserId; 
	} 
    String printHouseUserId;
    
    @JsonProperty("approvedduration") 
    public String getApprovedduration() { 
		 return this.approvedduration; 
	} 
    public void setApprovedduration(String approvedduration) { 
		 this.approvedduration = approvedduration; 
	} 
    String approvedduration;
    
    @JsonProperty("durationtype") 
    public String getDurationtype() { 
		 return this.durationtype; 
	} 
    public void setDurationtype(String durationtype) { 
		 this.durationtype = durationtype; 
	} 
    String durationtype;
    
    @JsonProperty("manualoption") 
    public String getmanualoption() { 
		 return this.manualoption; 
	} 
    public void setmanualoption(String manualoption) { 
		 this.manualoption = manualoption; 
	} 
    String manualoption;
    
}
