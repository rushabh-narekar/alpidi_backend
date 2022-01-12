package com.alpidi.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "shops")
public class Shops {
	 @JsonProperty("shop_id") 
	    public String getShop_id() { 
			 return this.shopid; } 
	    public void setShop_id(String shop_id) { 
			 this.shopid = shop_id; } 
	    String shopid;
	    @JsonProperty("shop_name") 
	    public String getShop_name() { 
			 return this.shop_name; } 
	    public void setShop_name(String shop_name) { 
			 this.shop_name = shop_name; } 
	    String shop_name;
	    @JsonProperty("loginuserid") 
	    public String getLoginuserid() { 
			 return this.loginuserid; } 
	    public void setLoginuserid(String loginuserid) { 
			 this.loginuserid = loginuserid; } 
	    String loginuserid;
	    @JsonProperty("user_id") 
	    public String getUser_id() { 
			 return this.userid; } 
	    public void setUser_id(String user_id) { 
			 this.userid = user_id; } 
	    String userid;
	    @JsonProperty("create_date") 
	    public int getCreate_date() { 
			 return this.create_date; } 
	    public void setCreate_date(int create_date) { 
			 this.create_date = create_date; } 
	    int create_date;
	    @JsonProperty("title") 
	    public String getTitle() { 
			 return this.title; } 
	    public void setTitle(String title) { 
			 this.title = title; } 
	    String title;
	    @JsonProperty("announcement") 
	    public String getAnnouncement() { 
			 return this.announcement; } 
	    public void setAnnouncement(String announcement) { 
			 this.announcement = announcement; } 
	    String announcement;
	    @JsonProperty("currency_code") 
	    public String getCurrency_code() { 
			 return this.currency_code; } 
	    public void setCurrency_code(String currency_code) { 
			 this.currency_code = currency_code; } 
	    String currency_code;
	    @JsonProperty("is_vacation") 
	    public boolean getIs_vacation() { 
			 return this.is_vacation; } 
	    public void setIs_vacation(boolean is_vacation) { 
			 this.is_vacation = is_vacation; } 
	    boolean is_vacation;
	    @JsonProperty("vacation_message") 
	    public Object getVacation_message() { 
			 return this.vacation_message; } 
	    public void setVacation_message(Object vacation_message) { 
			 this.vacation_message = vacation_message; } 
	    Object vacation_message;
	    @JsonProperty("sale_message") 
	    public Object getSale_message() { 
			 return this.sale_message; } 
	    public void setSale_message(Object sale_message) { 
			 this.sale_message = sale_message; } 
	    Object sale_message;
	    @JsonProperty("digital_sale_message") 
	    public Object getDigital_sale_message() { 
			 return this.digital_sale_message; } 
	    public void setDigital_sale_message(Object digital_sale_message) { 
			 this.digital_sale_message = digital_sale_message; } 
	    Object digital_sale_message;
	    @JsonProperty("update_date") 
	    public int getUpdate_date() { 
			 return this.update_date; } 
	    public void setUpdate_date(int update_date) { 
			 this.update_date = update_date; } 
	    int update_date;
	    @JsonProperty("listing_active_count") 
	    public int getListing_active_count() { 
			 return this.listing_active_count; } 
	    public void setListing_active_count(int listing_active_count) { 
			 this.listing_active_count = listing_active_count; } 
	    int listing_active_count;
	    @JsonProperty("digital_listing_count") 
	    public int getDigital_listing_count() { 
			 return this.digital_listing_count; } 
	    public void setDigital_listing_count(int digital_listing_count) { 
			 this.digital_listing_count = digital_listing_count; } 
	    int digital_listing_count;
	    @JsonProperty("login_name") 
	    public String getLogin_name() { 
			 return this.login_name; } 
	    public void setLogin_name(String login_name) { 
			 this.login_name = login_name; } 
	    String login_name;
	    @JsonProperty("accepts_custom_requests") 
	    public boolean getAccepts_custom_requests() { 
			 return this.accepts_custom_requests; } 
	    public void setAccepts_custom_requests(boolean accepts_custom_requests) { 
			 this.accepts_custom_requests = accepts_custom_requests; } 
	    boolean accepts_custom_requests;
	    @JsonProperty("vacation_autoreply") 
	    public Object getVacation_autoreply() { 
			 return this.vacation_autoreply; } 
	    public void setVacation_autoreply(Object vacation_autoreply) { 
			 this.vacation_autoreply = vacation_autoreply; } 
	    Object vacation_autoreply;
	    @JsonProperty("url") 
	    public String getUrl() { 
			 return this.url; } 
	    public void setUrl(String url) { 
			 this.url = url; } 
	    String url;
	    @JsonProperty("image_url_760x100") 
	    public Object getImage_url_760x100() { 
			 return this.image_url_760x100; } 
	    public void setImage_url_760x100(Object image_url_760x100) { 
			 this.image_url_760x100 = image_url_760x100; } 
	    Object image_url_760x100;
	    @JsonProperty("num_favorers") 
	    public int getNum_favorers() { 
			 return this.num_favorers; } 
	    public void setNum_favorers(int num_favorers) { 
			 this.num_favorers = num_favorers; } 
	    int num_favorers;
	    @JsonProperty("languages") 
	    public List<String> getLanguages() { 
			 return this.languages; } 
	    public void setLanguages(List<String> languages) { 
			 this.languages = languages; } 
	    List<String> languages;
	    @JsonProperty("icon_url_fullxfull") 
	    public String getIcon_url_fullxfull() { 
			 return this.icon_url_fullxfull; } 
	    public void setIcon_url_fullxfull(String icon_url_fullxfull) { 
			 this.icon_url_fullxfull = icon_url_fullxfull; } 
	    String icon_url_fullxfull;
	    @JsonProperty("is_using_structured_policies") 
	    public boolean getIs_using_structured_policies() { 
			 return this.is_using_structured_policies; } 
	    public void setIs_using_structured_policies(boolean is_using_structured_policies) { 
			 this.is_using_structured_policies = is_using_structured_policies; } 
	    boolean is_using_structured_policies;
	    @JsonProperty("has_onboarded_structured_policies") 
	    public boolean getHas_onboarded_structured_policies() { 
			 return this.has_onboarded_structured_policies; } 
	    public void setHas_onboarded_structured_policies(boolean has_onboarded_structured_policies) { 
			 this.has_onboarded_structured_policies = has_onboarded_structured_policies; } 
	    boolean has_onboarded_structured_policies;
	    @JsonProperty("include_dispute_form_link") 
	    public boolean getInclude_dispute_form_link() { 
			 return this.include_dispute_form_link; } 
	    public void setInclude_dispute_form_link(boolean include_dispute_form_link) { 
			 this.include_dispute_form_link = include_dispute_form_link; } 
	    boolean include_dispute_form_link;
	    @JsonProperty("is_direct_checkout_onboarded") 
	    public boolean getIs_direct_checkout_onboarded() { 
			 return this.is_direct_checkout_onboarded; } 
	    public void setIs_direct_checkout_onboarded(boolean is_direct_checkout_onboarded) { 
			 this.is_direct_checkout_onboarded = is_direct_checkout_onboarded; } 
	    boolean is_direct_checkout_onboarded;
	    @JsonProperty("is_opted_in_to_buyer_promise") 
	    public boolean getIs_opted_in_to_buyer_promise() { 
			 return this.is_opted_in_to_buyer_promise; } 
	    public void setIs_opted_in_to_buyer_promise(boolean is_opted_in_to_buyer_promise) { 
			 this.is_opted_in_to_buyer_promise = is_opted_in_to_buyer_promise; } 
	    boolean is_opted_in_to_buyer_promise;
	    @JsonProperty("is_calculated_eligible") 
	    public boolean getIs_calculated_eligible() { 
			 return this.is_calculated_eligible; } 
	    public void setIs_calculated_eligible(boolean is_calculated_eligible) { 
			 this.is_calculated_eligible = is_calculated_eligible; } 
	    boolean is_calculated_eligible;
	    @JsonProperty("is_shop_us_based") 
	    public boolean getIs_shop_us_based() { 
			 return this.is_shop_us_based; } 
	    public void setIs_shop_us_based(boolean is_shop_us_based) { 
			 this.is_shop_us_based = is_shop_us_based; } 
	    boolean is_shop_us_based;
	    @JsonProperty("transaction_sold_count") 
	    public int getTransaction_sold_count() { 
			 return this.transaction_sold_count; } 
	    public void setTransaction_sold_count(int transaction_sold_count) { 
			 this.transaction_sold_count = transaction_sold_count; } 
	    int transaction_sold_count;
	    @JsonProperty("shipping_from_country_iso") 
	    public String getShipping_from_country_iso() { 
			 return this.shipping_from_country_iso; } 
	    public void setShipping_from_country_iso(String shipping_from_country_iso) { 
			 this.shipping_from_country_iso = shipping_from_country_iso; } 
	    String shipping_from_country_iso;
	    @JsonProperty("shop_location_country_iso") 
	    public String getShop_location_country_iso() { 
			 return this.shop_location_country_iso; } 
	    public void setShop_location_country_iso(String shop_location_country_iso) { 
			 this.shop_location_country_iso = shop_location_country_iso; } 
	    String shop_location_country_iso;
	    @JsonProperty("policy_welcome") 
	    public Object getPolicy_welcome() { 
			 return this.policy_welcome; } 
	    public void setPolicy_welcome(Object policy_welcome) { 
			 this.policy_welcome = policy_welcome; } 
	    Object policy_welcome;
	    @JsonProperty("policy_payment") 
	    public Object getPolicy_payment() { 
			 return this.policy_payment; } 
	    public void setPolicy_payment(Object policy_payment) { 
			 this.policy_payment = policy_payment; } 
	    Object policy_payment;
	    @JsonProperty("policy_shipping") 
	    public Object getPolicy_shipping() { 
			 return this.policy_shipping; } 
	    public void setPolicy_shipping(Object policy_shipping) { 
			 this.policy_shipping = policy_shipping; } 
	    Object policy_shipping;
	    @JsonProperty("policy_refunds") 
	    public Object getPolicy_refunds() { 
			 return this.policy_refunds; } 
	    public void setPolicy_refunds(Object policy_refunds) { 
			 this.policy_refunds = policy_refunds; } 
	    Object policy_refunds;
	    @JsonProperty("policy_additional") 
	    public Object getPolicy_additional() { 
			 return this.policy_additional; } 
	    public void setPolicy_additional(Object policy_additional) { 
			 this.policy_additional = policy_additional; } 
	    Object policy_additional;
	    @JsonProperty("policy_seller_info") 
	    public Object getPolicy_seller_info() { 
			 return this.policy_seller_info; } 
	    public void setPolicy_seller_info(Object policy_seller_info) { 
			 this.policy_seller_info = policy_seller_info; } 
	    Object policy_seller_info;
	    @JsonProperty("policy_update_date") 
	    public int getPolicy_update_date() { 
			 return this.policy_update_date; } 
	    public void setPolicy_update_date(int policy_update_date) { 
			 this.policy_update_date = policy_update_date; } 
	    int policy_update_date;
	    @JsonProperty("policy_has_private_receipt_info") 
	    public boolean getPolicy_has_private_receipt_info() { 
			 return this.policy_has_private_receipt_info; } 
	    public void setPolicy_has_private_receipt_info(boolean policy_has_private_receipt_info) { 
			 this.policy_has_private_receipt_info = policy_has_private_receipt_info; } 
	    boolean policy_has_private_receipt_info;
	    @JsonProperty("has_unstructured_policies") 
	    public boolean getHas_unstructured_policies() { 
			 return this.has_unstructured_policies; } 
	    public void setHas_unstructured_policies(boolean has_unstructured_policies) { 
			 this.has_unstructured_policies = has_unstructured_policies; } 
	    boolean has_unstructured_policies;
	    @JsonProperty("policy_privacy") 
	    public Object getPolicy_privacy() { 
			 return this.policy_privacy; } 
	    public void setPolicy_privacy(Object policy_privacy) { 
			 this.policy_privacy = policy_privacy; } 
	    Object policy_privacy;
	    @JsonProperty("review_average") 
	    public Object getReview_average() { 
			 return this.review_average; } 
	    public void setReview_average(Object review_average) { 
			 this.review_average = review_average; } 
	    Object review_average;
	    @JsonProperty("review_count") 
	    public Object getReview_count() { 
			 return this.review_count; } 
	    public void setReview_count(Object review_count) { 
			 this.review_count = review_count; } 
	    Object review_count;
	    
	    @JsonProperty("is_deleted") 
	    public Boolean getisdeleted() { 
			 return this.isdeleted; } 
	    public void setisdeleted(Boolean isdeleted) { 
			 this.isdeleted = isdeleted; } 
	    Boolean isdeleted = false;
	    }
