package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction2 {
	 @JsonProperty("status") 
	 public String getstatus() { 
		 return this.status; } 
	 public void setstatus(String status) { 
		 this.status = status; } 
	 String status;
	 
	 @JsonProperty("object_id") 
	 public String getobject_id() { 
		 return this.object_id; } 
	 public void setobject_id(String object_id) { 
		 this.object_id = object_id; } 
	 String object_id;
	 
	 @JsonProperty("object_owner") 
	 public String getobject_owner() { 
		 return this.object_owner; } 
	 public void setobject_owner(String object_owner) { 
		 this.object_owner = object_owner; } 
	 String object_owner;
	 
	 @JsonProperty("object_created") 
	 public String getobject_created() { 
		 return this.object_created; } 
	 public void setobject_created(String object_created) { 
		 this.object_created = object_created; } 
	 String object_created;
	 
	 @JsonProperty("object_updated") 
	 public String getobject_updated() { 
		 return this.object_updated; } 
	 public void setobject_updated(String object_updated) { 
		 this.object_updated = object_updated; } 
	 String object_updated;
	 
	 @JsonProperty("commercial_invoice_url") 
	 public String getcommercial_invoice_url() { 
		 return this.commercial_invoice_url; } 
	 public void setcommercial_invoice_url(String commercial_invoice_url) { 
		 this.commercial_invoice_url = commercial_invoice_url; } 
	 String commercial_invoice_url;
	 
	 @JsonProperty("was_test") 
	 public String getwas_test() { 
		 return this.was_test; } 
	 public void setwas_test(String was_test) { 
		 this.was_test = was_test; } 
	 String was_test;
	 
	 @JsonProperty("rate") 
	 public Object getrate() { 
		 return this.rate; } 
	 public void setrate(Object rate) { 
		 this.rate = rate; } 
	 Object rate;
	 
	 @JsonProperty("tracking_number") 
	 public String gettracking_number() { 
		 return this.tracking_number; } 
	 public void settracking_number(String tracking_number) { 
		 this.tracking_number = tracking_number; } 
	 String tracking_number;
	 
	 @JsonProperty("tracking_status") 
	 public String gettracking_status() { 
		 return this.tracking_status; } 
	 public void settracking_status(String tracking_status) { 
		 this.tracking_status = tracking_status; } 
	 String tracking_status;
	 
	 @JsonProperty("tracking_url_provider") 
	 public String gettracking_url_provider() { 
		 return this.tracking_url_provider; } 
	 public void settracking_url_provider(String tracking_url_provider) { 
		 this.tracking_url_provider = tracking_url_provider; } 
	 String tracking_url_provider;
	 
	 @JsonProperty("label_url") 
	 public String getlabel_url() { 
		 return this.label_url; } 
	 public void setlabel_url(String label_url) { 
		 this.label_url = label_url; } 
	 String label_url;
	 
	 @JsonProperty("messages") 
	 public Object getmessages() { 
		 return this.messages; } 
	 public void setmessages(Object messages) { 
		 this.messages = messages; } 
	 Object messages;
	 
	 @JsonProperty("metadata") 
	 public String getmetadata() { 
		 return this.metadata; } 
	 public void setmetadata(String metadata) { 
		 this.metadata = metadata; } 
	 String metadata;
}
