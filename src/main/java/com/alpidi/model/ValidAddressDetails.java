package com.alpidi.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidAddressDetails {
	@JsonProperty("isComplete") 
    public boolean getIsComplete() { 
		 return this.isComplete; } 
    public void setIsComplete(boolean isComplete) { 
		 this.isComplete = isComplete; } 
    boolean isComplete;
    @JsonProperty("objectId") 
    public String getObjectId() { 
		 return this.objectId; } 
    public void setObjectId(String objectId) { 
		 this.objectId = objectId; } 
    String objectId;
    @JsonProperty("objectOwner") 
    public String getObjectOwner() { 
		 return this.objectOwner; } 
    public void setObjectOwner(String objectOwner) { 
		 this.objectOwner = objectOwner; } 
    String objectOwner;
    @JsonProperty("objectCreated") 
    public Date getObjectCreated() { 
		 return this.objectCreated; } 
    public void setObjectCreated(Date objectCreated) { 
		 this.objectCreated = objectCreated; } 
    Date objectCreated;
    @JsonProperty("objectUpdated") 
    public Date getObjectUpdated() { 
		 return this.objectUpdated; } 
    public void setObjectUpdated(Date objectUpdated) { 
		 this.objectUpdated = objectUpdated; } 
    Date objectUpdated;
    @JsonProperty("name") 
    public String getName() { 
		 return this.name; } 
    public void setName(String name) { 
		 this.name = name; } 
    String name;
    @JsonProperty("company") 
    public String getCompany() { 
		 return this.company; } 
    public void setCompany(String company) { 
		 this.company = company; } 
    String company;
    @JsonProperty("street1") 
    public String getStreet1() { 
		 return this.street1; } 
    public void setStreet1(String street1) { 
		 this.street1 = street1; } 
    String street1;
    @JsonProperty("streetNo") 
    public String getStreetNo() { 
		 return this.streetNo; } 
    public void setStreetNo(String streetNo) { 
		 this.streetNo = streetNo; } 
    String streetNo;
    @JsonProperty("street2") 
    public String getStreet2() { 
		 return this.street2; } 
    public void setStreet2(String street2) { 
		 this.street2 = street2; } 
    String street2;
    @JsonProperty("city") 
    public String getCity() { 
		 return this.city; } 
    public void setCity(String city) { 
		 this.city = city; } 
    String city;
    @JsonProperty("state") 
    public String getState() { 
		 return this.state; } 
    public void setState(String state) { 
		 this.state = state; } 
    String state;
    @JsonProperty("zip") 
    public String getZip() { 
		 return this.zip; } 
    public void setZip(String zip) { 
		 this.zip = zip; } 
    String zip;
    @JsonProperty("country") 
    public String getCountry() { 
		 return this.country; } 
    public void setCountry(String country) { 
		 this.country = country; } 
    String country;
    @JsonProperty("phone") 
    public String getPhone() { 
		 return this.phone; } 
    public void setPhone(String phone) { 
		 this.phone = phone; } 
    String phone;
    @JsonProperty("email") 
    public String getEmail() { 
		 return this.email; } 
    public void setEmail(String email) { 
		 this.email = email; } 
    String email;
    @JsonProperty("metadata") 
    public String getMetadata() { 
		 return this.metadata; } 
    public void setMetadata(String metadata) { 
		 this.metadata = metadata; } 
    String metadata;
    @JsonProperty("messages") 
    public Object getMessages() { 
		 return this.messages; } 
    public void setMessages(Object messages) { 
		 this.messages = messages; } 
    Object messages;
    @JsonProperty("validationResults") 
    public ValidationResults getValidationResults() { 
		 return this.validationResults; } 
    public void setValidationResults(ValidationResults validationResults) { 
		 this.validationResults = validationResults; } 
    ValidationResults validationResults;
    @JsonProperty("is_Residential") 
    public Object getIs_Residential() { 
		 return this.is_Residential; } 
    public void setIs_Residential(Object is_Residential) { 
		 this.is_Residential = is_Residential; } 
    Object is_Residential;
}
