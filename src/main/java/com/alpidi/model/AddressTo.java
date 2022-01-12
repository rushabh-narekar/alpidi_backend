package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressTo {
	@JsonProperty("object_id") 
    public String getObject_id() { 
		 return this.object_id; } 
    public void setObject_id(String object_id) { 
		 this.object_id = object_id; } 
    String object_id;
    @JsonProperty("is_complete") 
    public boolean getIs_complete() { 
		 return this.is_complete; } 
    public void setIs_complete(boolean is_complete) { 
		 this.is_complete = is_complete; } 
    boolean is_complete;
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
    @JsonProperty("street_no") 
    public String getStreet_no() { 
		 return this.street_no; } 
    public void setStreet_no(String street_no) { 
		 this.street_no = street_no; } 
    String street_no;
    @JsonProperty("street1") 
    public String getStreet1() { 
		 return this.street1; } 
    public void setStreet1(String street1) { 
		 this.street1 = street1; } 
    String street1;
    @JsonProperty("validation_results") 
    public ValidationResults getValidation_results() { 
		 return this.validation_results; } 
    public void setValidation_results(ValidationResults validation_results) { 
		 this.validation_results = validation_results; } 
    ValidationResults validation_results;
    @JsonProperty("street2") 
    public String getStreet2() { 
		 return this.street2; } 
    public void setStreet2(String street2) { 
		 this.street2 = street2; } 
    String street2;
    @JsonProperty("street3") 
    public String getStreet3() { 
		 return this.street3; } 
    public void setStreet3(String street3) { 
		 this.street3 = street3; } 
    String street3;
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
    @JsonProperty("is_residential") 
    public Object getIs_residential() { 
		 return this.is_residential; } 
    public void setIs_residential(Object is_residential) { 
		 this.is_residential = is_residential; } 
    Object is_residential;
    @JsonProperty("test") 
    public boolean getTest() { 
		 return this.test; } 
    public void setTest(boolean test) { 
		 this.test = test; } 
    boolean test;
}
