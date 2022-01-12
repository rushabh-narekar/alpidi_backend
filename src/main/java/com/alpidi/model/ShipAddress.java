package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fromAddress")
public class ShipAddress {
	String id;
	String loginuserid;
	String locationname;
	String inventorysource;
	String fullname;
	String company;
	String country;
	String streetaddress1;
	String streetaddress2;
	String city; 
	String state;
	String zipcode;
	String phone;
	String email;
	String timezone;
	Boolean isdefaultaddress;
	Boolean ispickupaddress;
	Boolean isreturnaddress;
	Boolean isvalidaddress;
	Boolean isdelete=false;
	
	String countryname;
	String cityname;
	String statename;
	
	public ShipAddress(String loginuserid,String locationname,String inventorysource,String fullname,String company,String country,String streetaddress1,
			String streetaddress2,String city,String state,String zipcode,String phone,String email,String timezone,Boolean isdefaultaddress,Boolean ispickupaddress,
			Boolean isreturnaddress,Boolean isvalidaddress) {
		this.loginuserid=loginuserid;
		this.locationname=locationname;
		this.inventorysource=inventorysource;
		this.fullname=fullname;
		this.company=company;
		this.streetaddress1=streetaddress1;
		this.streetaddress2=streetaddress2;
		this.country=country;
		this.city=city;
		this.state=state;
		this.zipcode=zipcode;
		this.phone=phone;
		this.email=email;
		this.timezone=timezone;
		this.isdefaultaddress=isdefaultaddress;
		this.ispickupaddress=ispickupaddress;
		this.isreturnaddress=isreturnaddress;
		this.isvalidaddress=isvalidaddress;
	}
	public String getid() {
	    return id;
	}
	
	public String getloginuserid() {
	    return loginuserid;
	}
	public void setloginuserid(String loginuserid) {
	    this.loginuserid = loginuserid;
	}
	public String getlocationname() {
	    return locationname;
	}
	public void setlocationname(String locationname) {
	    this.locationname = locationname;
	}
	public String getinventorysource() {
	    return inventorysource;
	}
	public void setinventorysource(String inventorysource) {
	    this.inventorysource = inventorysource;
	}
	public String getfullname() {
	    return fullname;
	}
	public void setfullname(String fullname) {
	    this.fullname = fullname;
	}
	public String getcompany() {
	    return company;
	}
	public void setcompany(String company) {
	    this.company = company;
	}
	public String getstreetaddress1() {
	    return streetaddress1;
	}
	public void setstreetaddress1(String streetaddress1) {
	    this.streetaddress1 = streetaddress1;
	}
	public String getstreetaddress2() {
	    return streetaddress2;
	}
	public void setstreetaddress2(String streetaddress2) {
	    this.streetaddress2 = streetaddress2;
	}
	public String getcountry() {
	    return country;
	}
	public void setcountry(String country) {
	    this.country = country;
	}
	public String getcity() {
	    return city;
	}
	public void setcity(String city) {
	    this.city = city;
	}
	public String getstate() {
	    return state;
	}
	public void setstate(String state) {
	    this.state = state;
	}
	public String getzipcode() {
	    return zipcode;
	}
	public void setzipcode(String zipcode) {
	    this.zipcode = zipcode;
	}
	public String getphone() {
	    return phone;
	}
	public void setphone(String phone) {
	    this.phone = phone;
	}
	public String getemail() {
	    return email;
	}
	public void setemail(String email) {
	    this.email = email;
	}
	public String gettimezone() {
	    return timezone;
	}
	public void settimezone(String timezone) {
	    this.timezone = timezone;
	}
	
	public Boolean getisdefaultaddress() {
	    return isdefaultaddress;
	}
	public void setisdefaultaddress(Boolean isdefaultaddress) {
	    this.isdefaultaddress = isdefaultaddress;
	}
	
	public Boolean getispickupaddress() {
	    return ispickupaddress;
	}
	public void setispickupaddress(Boolean ispickupaddress) {
	    this.ispickupaddress = ispickupaddress;
	}
	public Boolean getisreturnaddress() {
	    return isreturnaddress;
	}
	public void setisreturnaddress(Boolean isreturnaddress) {
	    this.isreturnaddress = isreturnaddress;
	}
	
	public String getcountryname() {
	    return countryname;
	}
	public void setcountryname(String countryname) {
	    this.countryname = countryname;
	}
	public String getcityname() {
	    return cityname;
	}
	public void setcityname(String cityname) {
	    this.cityname = cityname;
	}
	public String getstatename() {
	    return statename;
	}
	public void setstatename(String statename) {
	    this.statename = statename;
	}
	public Boolean getisvalidaddress() {
	    return isvalidaddress;
	}
	public void setisvalidaddress(Boolean isvalidaddress) {
	    this.isvalidaddress = isvalidaddress;
	}
	
	public Boolean getisdelete() {
	    return isdelete;
	}
	public void setisdelete(Boolean isdelete) {
	    this.isdelete = isdelete;
	}
}
