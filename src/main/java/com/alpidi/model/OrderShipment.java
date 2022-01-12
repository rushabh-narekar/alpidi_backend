package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderShipment {
	@JsonProperty("receipt_shipping_id") 
    public String getReceipt_shipping_id() { 
		 return this.receiptshipping_id; } 
    public void setReceipt_shipping_id(String receiptshipping_id) { 
		 this.receiptshipping_id = receiptshipping_id; } 
    String receiptshipping_id;
    @JsonProperty("shipment_notification_timestamp") 
    public int getShipment_notification_timestamp() { 
		 return this.shipment_notification_timestamp; } 
    public void setShipment_notification_timestamp(int shipment_notification_timestamp) { 
		 this.shipment_notification_timestamp = shipment_notification_timestamp; } 
    int shipment_notification_timestamp;
    @JsonProperty("carrier_name") 
    public String getCarrier_name() { 
		 return this.carrier_name; } 
    public void setCarrier_name(String carrier_name) { 
		 this.carrier_name = carrier_name; } 
    String carrier_name;
    @JsonProperty("tracking_code") 
    public String getTracking_code() { 
		 return this.tracking_code; } 
    public void setTracking_code(String tracking_code) { 
		 this.tracking_code = tracking_code; } 
    String tracking_code;
}
