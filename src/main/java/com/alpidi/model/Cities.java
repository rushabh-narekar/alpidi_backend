package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Cities")
public class Cities {
	String cityid;
	String name;
	String stateid;
	String statecode;
	String countryid;
	String countrycode;
	String latitude;
	String longitude;
	String wikiDataId;
	
	public String getcityid() {
	    return cityid;
	}
	public void setcityid(String cityid) {
	    this.cityid = cityid;
	}
	public String getname() {
	    return name;
	}
	public void setname(String name) {
	    this.name = name;
	}
	public String getstateid() {
	    return stateid;
	}
	public void setstateid(String stateid) {
	    this.stateid = stateid;
	}
	public String getstatecode() {
	    return statecode;
	}
	public void setstatecode(String statecode) {
	    this.statecode = statecode;
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
	
	public String getwikiDataId() {
	    return wikiDataId;
	}
	public void setwikiDataId(String wikiDataId) {
	    this.wikiDataId = wikiDataId;
	}
}
