package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Countries")
public class Countries {
	String id;
	String countryid;
	String name;
	String iso3;
	String iso2;
	String numeric_code;
	String phone_code;
	String capital;
	String currency;
	String currency_symbol;
	String tld;
	String region;
	String subregion;
	String timezones;
	String latitude;
	String longitude;
	String emoji;
	String emojiU;
	
	public String getid() {
	    return id;
	}
	public void setid(String id) {
	    this.id = id;
	}
	public String getcountryid() {
	    return countryid;
	}
	public void setcountryid(String countryid) {
	    this.countryid = countryid;
	}
	public String getname() {
	    return name;
	}
	public void setname(String name) {
	    this.name = name;
	}
	public String getiso3() {
	    return iso3;
	}
	public void setiso3(String iso3) {
	    this.iso3 = iso3;
	}
	
	public String getiso2() {
	    return iso2;
	}
	public void setiso2(String iso2) {
	    this.iso2 = iso2;
	}
	public String getnumeric_code() {
	    return numeric_code;
	}
	public void setnumeric_code(String numeric_code) {
	    this.numeric_code = numeric_code;
	}
	public String getcapital() {
	    return capital;
	}
	public void setcapital(String capital) {
	    this.capital = capital;
	}
	public String getcurrency() {
	    return currency;
	}
	public void setcurrency(String currency) {
	    this.currency = currency;
	}
	public String getcurrency_symbol() {
	    return currency_symbol;
	}
	public void setcurrency_symbol(String currency_symbol) {
	    this.currency_symbol = currency_symbol;
	}
	public String gettld() {
	    return tld;
	}
	public void settld(String tld) {
	    this.tld = tld;
	}
	public String getregion() {
	    return region;
	}
	public void setregion(String region) {
	    this.region = region;
	}
	public String getsubregion() {
	    return subregion;
	}
	public void setsubregion(String subregion) {
	    this.subregion = subregion;
	}
	public String gettimezones() {
	    return timezones;
	}
	public void settimezones(String timezones) {
	    this.timezones = timezones;
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
	public String getemoji() {
	    return emoji;
	}
	public void setemoji(String emoji) {
	    this.emoji = emoji;
	}
	public String getemojiU() {
	    return emojiU;
	}
	public void setemojiU(String emojiU) {
	    this.emojiU = emojiU;
	}
	
}
