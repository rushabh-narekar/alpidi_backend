package com.alpidi.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rate2 {
	@JsonProperty("object_created") 
    public Date getObject_created() { 
		 return this.object_created; } 
    public void setObject_created(Date object_created) { 
		 this.object_created = object_created; } 
    Date object_created;
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
    @JsonProperty("shipment") 
    public String getShipment() { 
		 return this.shipment; } 
    public void setShipment(String shipment) { 
		 this.shipment = shipment; } 
    String shipment;
    @JsonProperty("attributes") 
    public List<String> getAttributes() { 
		 return this.attributes; } 
    public void setAttributes(List<String> attributes) { 
		 this.attributes = attributes; } 
    List<String> attributes;
    @JsonProperty("amount") 
    public String getAmount() { 
		 return this.amount; } 
    public void setAmount(String amount) { 
		 this.amount = amount; } 
    String amount;
    @JsonProperty("currency") 
    public String getCurrency() { 
		 return this.currency; } 
    public void setCurrency(String currency) { 
		 this.currency = currency; } 
    String currency;
    @JsonProperty("amount_local") 
    public String getAmount_local() { 
		 return this.amount_local; } 
    public void setAmount_local(String amount_local) { 
		 this.amount_local = amount_local; } 
    String amount_local;
    @JsonProperty("currency_local") 
    public String getCurrency_local() { 
		 return this.currency_local; } 
    public void setCurrency_local(String currency_local) { 
		 this.currency_local = currency_local; } 
    String currency_local;
    @JsonProperty("provider") 
    public String getProvider() { 
		 return this.provider; } 
    public void setProvider(String provider) { 
		 this.provider = provider; } 
    String provider;
    @JsonProperty("provider_image_75") 
    public String getProvider_image_75() { 
		 return this.provider_image_75; } 
    public void setProvider_image_75(String provider_image_75) { 
		 this.provider_image_75 = provider_image_75; } 
    String provider_image_75;
    @JsonProperty("provider_image_200") 
    public String getProvider_image_200() { 
		 return this.provider_image_200; } 
    public void setProvider_image_200(String provider_image_200) { 
		 this.provider_image_200 = provider_image_200; } 
    String provider_image_200;
    @JsonProperty("servicelevel") 
    public ServiceLevel getServicelevel() { 
		 return this.servicelevel; } 
    public void setServicelevel(ServiceLevel servicelevel) { 
		 this.servicelevel = servicelevel; } 
    ServiceLevel servicelevel;
    @JsonProperty("estimated_days") 
    public int getEstimated_days() { 
		 return this.estimated_days; } 
    public void setEstimated_days(int estimated_days) { 
		 this.estimated_days = estimated_days; } 
    int estimated_days;
    @JsonProperty("arrives_by") 
    public String getArrives_by() { 
		 return this.arrives_by; } 
    public void setArrives_by(String arrives_by) { 
		 this.arrives_by = arrives_by; } 
    String arrives_by;
    @JsonProperty("duration_terms") 
    public String getDuration_terms() { 
		 return this.duration_terms; } 
    public void setDuration_terms(String duration_terms) { 
		 this.duration_terms = duration_terms; } 
    String duration_terms;
    @JsonProperty("messages") 
    public List<Object> getMessages() { 
		 return this.messages; } 
    public void setMessages(List<Object> messages) { 
		 this.messages = messages; } 
    List<Object> messages;
    @JsonProperty("carrier_account") 
    public String getCarrier_account() { 
		 return this.carrier_account; } 
    public void setCarrier_account(String carrier_account) { 
		 this.carrier_account = carrier_account; } 
    String carrier_account;
    @JsonProperty("test") 
    public boolean getTest() { 
		 return this.test; } 
    public void setTest(boolean test) { 
		 this.test = test; } 
    boolean test;
    @JsonProperty("zone") 
    public String getZone() { 
		 return this.zone; } 
    public void setZone(String zone) { 
		 this.zone = zone; } 
    String zone;
}
