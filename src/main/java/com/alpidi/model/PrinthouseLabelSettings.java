package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "printhouseLabelSettings")
public class PrinthouseLabelSettings {
	@JsonProperty("id") 
    public String getid() { 
		 return this.id; 
	} 
    public void setid(String id) { 
		 this.id = id; 
	} 
    String id;
    
    @JsonProperty("printhouseuserid") 
    public String getprinthouseuserid() { 
		 return this.printhouseuserid; 
	} 
    public void setprinthouseuserid(String printhouseuserid) { 
		 this.printhouseuserid = printhouseuserid; 
	} 
    String printhouseuserid;
    
    @JsonProperty("labelformat") 
    public String getlabelformat() { 
		 return this.labelformat; 
	} 
    public void setlabelformat(String labelformat) { 
		 this.labelformat = labelformat; 
	} 
    String labelformat;
    
    @JsonProperty("labeldpi") 
    public String getlabeldpi() { 
		 return this.labeldpi; 
	} 
    public void setlabeldpi(String labeldpi) { 
		 this.labeldpi = labeldpi; 
	} 
    String labeldpi;
    
    @JsonProperty("batchsummery") 
    public String getbatchsummery() { 
		 return this.batchsummery; 
	} 
    public void setbatchsummery(String batchsummery) { 
		 this.batchsummery = batchsummery; 
	} 
    String batchsummery;
    
    @JsonProperty("orderlabelby") 
    public String getorderlabelby() { 
		 return this.orderlabelby; 
	} 
    public void setorderlabelby(String orderlabelby) { 
		 this.orderlabelby = orderlabelby; 
	} 
    String orderlabelby;
    
    @JsonProperty("islabelbranding") 
    public Boolean getislabelbranding() { 
		 return this.islabelbranding; 
	} 
    public void setislabelbranding(Boolean islabelbranding) { 
		 this.islabelbranding = islabelbranding; 
	} 
    Boolean islabelbranding;
    
    @JsonProperty("shopname") 
    public Boolean getshopname() { 
		 return this.shopname; 
	} 
    public void setshopname(Boolean shopname) { 
		 this.shopname = shopname; 
	} 
    Boolean shopname;
    
    @JsonProperty("fromaddress") 
    public Boolean getfromaddress() { 
		 return this.fromaddress; 
	} 
    public void setfromaddress(Boolean fromaddress) { 
		 this.fromaddress = fromaddress; 
	} 
    Boolean fromaddress;
    
    @JsonProperty("listingimage") 
    public Boolean getlistingimage() { 
		 return this.listingimage; 
	} 
    public void setlistingimage(Boolean listingimage) { 
		 this.listingimage = listingimage; 
	} 
    Boolean listingimage;
    
    @JsonProperty("customizedetails") 
    public Boolean getcustomizedetails() { 
		 return this.customizedetails; 
	} 
    public void setcustomizedetails(Boolean customizedetails) { 
		 this.customizedetails = customizedetails; 
	} 
    Boolean customizedetails;
    
    @JsonProperty("shipingaddress") 
    public Boolean getshipingaddress() { 
		 return this.shipingaddress; 
	} 
    public void setshipingaddress(Boolean shipingaddress) { 
		 this.shipingaddress = shipingaddress; 
	} 
    Boolean shipingaddress;
    
    @JsonProperty("vectorfile") 
    public Boolean getvectorfile() { 
		 return this.vectorfile; 
	} 
    public void setvectorfile(Boolean vectorfile) { 
		 this.vectorfile = vectorfile; 
	} 
    Boolean vectorfile;
    
    @JsonProperty("skunumber") 
    public Boolean getskunumber() { 
		 return this.skunumber; 
	} 
    public void setskunumber(Boolean skunumber) { 
		 this.skunumber = skunumber; 
	} 
    Boolean skunumber;
    
    @JsonProperty("orderitemdetails") 
    public Boolean getorderitemdetails() { 
		 return this.orderitemdetails; 
	} 
    public void setorderitemdetails(Boolean orderitemdetails) { 
		 this.orderitemdetails = orderitemdetails; 
	} 
    Boolean orderitemdetails;

}
