package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "inventory")
public class Inventory {
	@JsonProperty("id") 
    public String getid() { 
		 return this.id; } 
    public void setid(String id) { 
		 this.id = id; } 
    String id;
    
    @JsonProperty("loginuserid") 
    public String getloginuserid() { 
		 return this.loginuserid; } 
    public void setloginuserid(String loginuserid) { 
		 this.loginuserid = loginuserid; } 
    String loginuserid;
    
    @JsonProperty("size") 
    public String getsize() { 
		 return this.size; } 
    public void setsize(String size) { 
		 this.size = size; } 
    String size;
    
    @JsonProperty("color") 
    public String getcolor() { 
		 return this.color; } 
    public void setcolor(String color) { 
		 this.color = color; } 
    String color;
    
    @JsonProperty("modelname") 
    public String getmodelname() { 
		 return this.modelname; } 
    public void setmodelname(String modelname) { 
		 this.modelname = modelname; } 
    String modelname;
    
    @JsonProperty("modelnumber") 
    public String getmodelnumber() { 
		 return this.modelnumber; } 
    public void setmodelnumber(String modelnumber) { 
		 this.modelnumber = modelnumber; } 
    String modelnumber;
    
    @JsonProperty("modeltype") 
    public String getmodeltype() { 
		 return this.modeltype; } 
    public void setmodeltype(String modeltype) { 
		 this.modeltype = modeltype; } 
    String modeltype;
    
    @JsonProperty("nacktype") 
    public String getnacktype() { 
		 return this.nacktype; } 
    public void setnacktype(String nacktype) { 
		 this.nacktype = nacktype; } 
    String nacktype;
    
    @JsonProperty("brand") 
    public String getbrand() { 
		 return this.brand; } 
    public void setbrand(String brand) { 
		 this.brand = brand; } 
    String brand;
    
    @JsonProperty("quntity") 
    public Number getquntity() { 
		 return this.quntity; 
		 } 
    public void setquntity(Number quntity) { 
		 this.quntity = quntity; 
		 } 
    Number quntity;
    
    @JsonProperty("productionCost") 
    public Number getproductionCost() { 
		 return this.productionCost; 
		 } 
    public void setproductionCost(Number productionCost) { 
		 this.productionCost = productionCost; 
		 } 
    Number productionCost;
    
    @JsonProperty("shipingamount") 
    public String getshipingamount() { 
		 return this.shipingamount; 
		 } 
    public void setshipingamount(String shipingamount) { 
		 this.shipingamount = shipingamount; 
		 } 
    String shipingamount;
    
    @JsonProperty("incomingOrderQuatity") 
    public Number getincomingOrderQuatity() { 
		 return this.incomingOrderQuatity; 
	} 
    public void setincomingOrderQuatity(Number incomingOrderQuatity) { 
		 this.incomingOrderQuatity = incomingOrderQuatity; 
	} 
    Number incomingOrderQuatity;
    
    @JsonProperty("processingQuantity") 
    public Number getprocessingQuantity() { 
		 return this.processingQuantity; 
	} 
    public void setprocessingQuantity(Number processingQuantity) { 
		 this.processingQuantity = processingQuantity; 
	} 
    Number processingQuantity;
    
    @JsonProperty("totalSellingQuantity") 
    public Number gettotalSellingQuantity() { 
		 return this.totalSellingQuantity; 
	} 
    public void settotalSellingQuantity(Number totalSellingQuantity) { 
		 this.totalSellingQuantity = totalSellingQuantity; 
	} 
    Number totalSellingQuantity;
    
    @JsonProperty("alertqty") 
    public Number getalertqty() { 
		 return this.alertqty; 
	} 
    public void setalertqty(Number alertqty) { 
		 this.alertqty = alertqty; 
	} 
    Number alertqty;
    
    @JsonProperty("criticalqty") 
    public Number getcriticalqty() { 
		 return this.criticalqty; 
	} 
    public void setcriticalqty(Number criticalqty) { 
		 this.criticalqty = criticalqty; 
	} 
    Number criticalqty;
    
    @JsonProperty("isdeleted") 
    public Boolean getisdeleted() { 
		 return this.isdeleted; } 
    public void setisdeleted(Boolean isdeleted) { 
		 this.isdeleted = isdeleted; } 
    Boolean isdeleted = false;
}
