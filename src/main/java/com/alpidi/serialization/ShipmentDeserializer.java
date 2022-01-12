package com.alpidi.serialization;

import java.lang.reflect.Type;

import com.alpidi.model.Address;
import com.alpidi.model.Shipment;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;


public class ShipmentDeserializer implements JsonDeserializer<Shipment> {
    public Shipment deserialize(
    		JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Shipment shipment = GsonFactory.DEFAULT_GSON
        	.fromJson(json, com.alpidi.model.Shipment.class);
        JsonObject jsonObject = json.getAsJsonObject();
        Address addressTo = this.getAddress(jsonObject, "address_to");
        if (addressTo != null) {
        	shipment.setAddressTo(addressTo);
        }
        Address addressFrom = this.getAddress(jsonObject, "address_from");
        if (addressFrom != null) {
        	shipment.setAddressFrom(addressFrom);
        }
        Address addressReturn = this.getAddress(jsonObject, "address_return");
        if (addressReturn != null) {
        	shipment.setAddressReturn(addressReturn);
        }
 		return shipment;
    }

    private Address getAddress(JsonObject jsonObject, String address_key) {
    	if (jsonObject.has(address_key)) {
    		JsonElement elem = jsonObject.get(address_key);
    		if ((elem != null) && !elem.isJsonNull()) {
    			String valuesString = elem.toString();
    			if ((valuesString != null) && !valuesString.isEmpty()) {
    				try {
    					return GsonFactory.DEFAULT_GSON.fromJson(
    						valuesString, Address.class);
    				} catch (JsonSyntaxException ex) {
    					// Expected if it's not an address type, return raw.
    				}
    			}
    		}
    	}
    	return null;
    }
}