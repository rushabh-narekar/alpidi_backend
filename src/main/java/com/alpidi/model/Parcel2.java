package com.alpidi.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parcel2 {
	@JsonProperty("object_state") 
    public String getObject_state() { 
		 return this.object_state; } 
    public void setObject_state(String object_state) { 
		 this.object_state = object_state; } 
    String object_state;
    @JsonProperty("object_created") 
    public Date getObject_created() { 
		 return this.object_created; } 
    public void setObject_created(Date object_created) { 
		 this.object_created = object_created; } 
    Date object_created;
    @JsonProperty("object_updated") 
    public Date getObject_updated() { 
		 return this.object_updated; } 
    public void setObject_updated(Date object_updated) { 
		 this.object_updated = object_updated; } 
    Date object_updated;
    @JsonProperty("object_id") 
    public String getObject_id() { 
		 return this.object_id; } 
    public void setObject_id(String object_id) { 
		 this.object_id = object_id; } 
    String object_id;
    @JsonProperty("object_owner") 
    public String getObject_owner() { 
		 return this.object_owner; } 
    public void setObject_owner(String object_owner) { 
		 this.object_owner = object_owner; } 
    String object_owner;
    @JsonProperty("template") 
    public Object getTemplate() { 
		 return this.template; } 
    public void setTemplate(Object template) { 
		 this.template = template; } 
    Object template;
    @JsonProperty("extra") 
    public Object getExtra() { 
		 return this.extra; } 
    public void setExtra(Object extra) { 
		 this.extra = extra; } 
    Object extra;
    @JsonProperty("length") 
    public String getLength() { 
		 return this.length; } 
    public void setLength(String length) { 
		 this.length = length; } 
    String length;
    @JsonProperty("width") 
    public String getWidth() { 
		 return this.width; } 
    public void setWidth(String width) { 
		 this.width = width; } 
    String width;
    @JsonProperty("height") 
    public String getHeight() { 
		 return this.height; } 
    public void setHeight(String height) { 
		 this.height = height; } 
    String height;
    @JsonProperty("distance_unit") 
    public String getDistance_unit() { 
		 return this.distance_unit; } 
    public void setDistance_unit(String distance_unit) { 
		 this.distance_unit = distance_unit; } 
    String distance_unit;
    @JsonProperty("weight") 
    public String getWeight() { 
		 return this.weight; } 
    public void setWeight(String weight) { 
		 this.weight = weight; } 
    String weight;
    @JsonProperty("mass_unit") 
    public String getMass_unit() { 
		 return this.mass_unit; } 
    public void setMass_unit(String mass_unit) { 
		 this.mass_unit = mass_unit; } 
    String mass_unit;
    @JsonProperty("value_amount") 
    public Object getValue_amount() { 
		 return this.value_amount; } 
    public void setValue_amount(Object value_amount) { 
		 this.value_amount = value_amount; } 
    Object value_amount;
    @JsonProperty("value_currency") 
    public Object getValue_currency() { 
		 return this.value_currency; } 
    public void setValue_currency(Object value_currency) { 
		 this.value_currency = value_currency; } 
    Object value_currency;
    @JsonProperty("metadata") 
    public String getMetadata() { 
		 return this.metadata; } 
    public void setMetadata(String metadata) { 
		 this.metadata = metadata; } 
    String metadata;
    @JsonProperty("line_items") 
    public List<Object> getLine_items() { 
		 return this.line_items; } 
    public void setLine_items(List<Object> line_items) { 
		 this.line_items = line_items; } 
    List<Object> line_items;
    @JsonProperty("test") 
    public boolean getTest() { 
		 return this.test; } 
    public void setTest(boolean test) { 
		 this.test = test; } 
    boolean test;
}
