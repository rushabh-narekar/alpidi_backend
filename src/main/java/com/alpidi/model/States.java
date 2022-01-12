package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "States")
public class States {
	String stateid;
	String name;
	String countryid;
	String countrycode;
	String statecode;
	String latitude;
	String longitude;
	
	public String getstateid() {
	    return stateid;
	}
	public void setstateid(String stateid) {
	    this.stateid = stateid;
	}
	public String getname() {
	    return name;
	}
	public void setname(String name) {
	    this.name = name;
	}
	public String getcountryid() {
	    return countryid;
	}
	public void setcountryid(String countryid) {
	    this.countryid = countryid;
	}
	public String getcountrycode() {
	    return countrycode;
	}
	public void setcountrycode(String countrycode) {
	    this.countrycode = countrycode;
	}
	public String getstatecode() {
	    return statecode;
	}
	public void setstatecode(String statecode) {
	    this.statecode = statecode;
	}
	public String getlatitude() {
	    return latitude;
	}
	public void setlatitude(String latitude) {
	    this.latitude = latitude;
	}
	public String getlongitude() {
	    return longitude;
	}
	public void setlongitude(String longitude) {
	    this.longitude = longitude;
	}
}
