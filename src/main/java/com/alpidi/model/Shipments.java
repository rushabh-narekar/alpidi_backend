package com.alpidi.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Shipments {
	@JsonProperty("carrier_accounts") 
    public List<Object> getCarrier_accounts() { 
		 return this.carrier_accounts; } 
    public void setCarrier_accounts(List<Object> carrier_accounts) { 
		 this.carrier_accounts = carrier_accounts; } 
    List<Object> carrier_accounts;
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
    @JsonProperty("status") 
    public String getStatus() { 
		 return this.status; } 
    public void setStatus(String status) { 
		 this.status = status; } 
    String status;
    @JsonProperty("address_from") 
    public AddressFrom getAddress_from() { 
		 return this.address_from; } 
    public void setAddress_from(AddressFrom address_from) { 
		 this.address_from = address_from; } 
    AddressFrom address_from;
    @JsonProperty("address_to") 
    public AddressTo getAddress_to() { 
		 return this.address_to; } 
    public void setAddress_to(AddressTo address_to) { 
		 this.address_to = address_to; } 
    AddressTo address_to;
    @JsonProperty("parcels") 
    public List<Parcel2> getParcels() { 
		 return this.parcels; } 
    public void setParcels(List<Parcel2> parcels) { 
		 this.parcels = parcels; } 
    List<Parcel2> parcels;
    @JsonProperty("shipment_date") 
    public Date getShipment_date() { 
		 return this.shipment_date; } 
    public void setShipment_date(Date shipment_date) { 
		 this.shipment_date = shipment_date; } 
    Date shipment_date;
    @JsonProperty("address_return") 
    public AddressReturn getAddress_return() { 
		 return this.address_return; } 
    public void setAddress_return(AddressReturn address_return) { 
		 this.address_return = address_return; } 
    AddressReturn address_return;
    @JsonProperty("alternate_address_to") 
    public Object getAlternate_address_to() { 
		 return this.alternate_address_to; } 
    public void setAlternate_address_to(Object alternate_address_to) { 
		 this.alternate_address_to = alternate_address_to; } 
    Object alternate_address_to;
    @JsonProperty("customs_declaration") 
    public Object getCustoms_declaration() { 
		 return this.customs_declaration; } 
    public void setCustoms_declaration(Object customs_declaration) { 
		 this.customs_declaration = customs_declaration; } 
    Object customs_declaration;
    @JsonProperty("extra") 
    public Object getExtra() { 
		 return this.extra; } 
    public void setExtra(Object extra) { 
		 this.extra = extra; } 
    Object extra;
    @JsonProperty("rates") 
    public List<Rate2> getRates() { 
		 return this.rates; } 
    public void setRates(List<Rate2> rates) { 
		 this.rates = rates; } 
    List<Rate2> rates;
    @JsonProperty("messages") 
    public Object getMessages() { 
		 return this.messages; } 
    public void setMessages(Object messages) { 
		 this.messages = messages; } 
    Object messages;
    @JsonProperty("metadata") 
    public String getMetadata() { 
		 return this.metadata; } 
    public void setMetadata(String metadata) { 
		 this.metadata = metadata; } 
    String metadata;
    @JsonProperty("test") 
    public boolean getTest() { 
		 return this.test; } 
    public void setTest(boolean test) { 
		 this.test = test; } 
    boolean test;
    @JsonProperty("order") 
    public Object getOrder() { 
		 return this.order; } 
    public void setOrder(Object order) { 
		 this.order = order; } 
    Object order;
}
