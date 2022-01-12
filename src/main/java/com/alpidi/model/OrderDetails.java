package com.alpidi.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "Orders")
public class OrderDetails {
	@Id
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonProperty("receipt_id") 
    public String getReceipt_id() { 
		 return this.receiptid; } 
    public void setReceipt_id(String receiptid) { 
		 this.receiptid = receiptid; } 
    @Indexed(unique=true)
    String receiptid;
    @JsonProperty("receipt_type") 
    public int getReceipt_type() { 
		 return this.receipttype; } 
    public void setReceipt_type(int receipttype) { 
		 this.receipttype = receipttype; } 
    int receipttype;
    @JsonProperty("seller_user_id") 
    public String getSeller_user_id() { 
		 return this.selleruserid; } 
    public void setSeller_user_id(String selleruserid) { 
		 this.selleruserid = selleruserid; } 
    String selleruserid;
    @JsonProperty("login_user_id") 
    public String getLogin_user_id() { 
		 return this.loginuserid; } 
    public void setLogin_user_id(String loginuserid) { 
		 this.loginuserid = loginuserid; } 
    String loginuserid;
    @JsonProperty("seller_email") 
    public String getSeller_email() { 
		 return this.selleremail; } 
    public void setSeller_email(String selleremail) { 
		 this.selleremail = selleremail; } 
    String selleremail;
    @JsonProperty("buyer_user_id") 
    public int getBuyer_user_id() { 
		 return this.buyeruserid; } 
    public void setBuyer_user_id(int buyeruserid) { 
		 this.buyeruserid = buyeruserid; } 
    int buyeruserid;
    @JsonProperty("buyer_email") 
    public String getBuyer_email() { 
		 return this.buyeremail; } 
    public void setBuyer_email(String buyeremail) { 
		 this.buyeremail = buyeremail; } 
    String buyeremail;
    @JsonProperty("name") 
    public String getName() { 
		 return this.name; } 
    public void setName(String name) { 
		 this.name = name; } 
    String name;
    @JsonProperty("first_line") 
    public String getFirst_line() { 
		 return this.firstline; } 
    public void setFirst_line(String firstline) { 
		 this.firstline = firstline; } 
    String firstline;
    @JsonProperty("second_line") 
    public String getSecond_line() { 
		 return this.secondline; } 
    public void setSecond_line(String secondline) { 
		 this.secondline = secondline; } 
    String secondline;
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
    @JsonProperty("formatted_address") 
    public String getFormatted_address() { 
		 return this.formattedaddress; } 
    public void setFormatted_address(String formattedaddress) { 
		 this.formattedaddress = formattedaddress; } 
    String formattedaddress;
    @JsonProperty("country_iso") 
    public String getCountry_iso() { 
		 return this.countryiso; } 
    public void setCountry_iso(String countryiso) { 
		 this.countryiso = countryiso; } 
    String countryiso;
    @JsonProperty("payment_method") 
    public String getPayment_method() { 
		 return this.paymentmethod; } 
    public void setPayment_method(String paymentmethod) { 
		 this.paymentmethod = paymentmethod; } 
    String paymentmethod;
    @JsonProperty("payment_email") 
    public String getPayment_email() { 
		 return this.paymentemail; } 
    public void setPayment_email(String paymentemail) { 
		 this.paymentemail = paymentemail; } 
    String paymentemail;
    @JsonProperty("message_from_payment") 
    public Object getMessage_from_payment() { 
		 return this.messagefrompayment; } 
    public void setMessage_from_payment(Object messagefrompayment) { 
		 this.messagefrompayment = messagefrompayment; } 
    Object messagefrompayment;
    @JsonProperty("message_from_seller") 
    public Object getMessage_from_seller() { 
		 return this.messagefromseller; } 
    public void setMessage_from_seller(Object messagefromseller) { 
		 this.messagefromseller = messagefromseller; } 
    Object messagefromseller;
    @JsonProperty("message_from_buyer") 
    public String getMessage_from_buyer() { 
		 return this.messagefrombuyer; } 
    public void setMessage_from_buyer(String messagefrombuyer) { 
		 this.messagefrombuyer = messagefrombuyer; } 
    String messagefrombuyer;
    @JsonProperty("is_shipped") 
    public boolean getIs_shipped() { 
		 return this.isshipped; } 
    public void setIs_shipped(boolean isshipped) { 
		 this.isshipped = isshipped; } 
    boolean isshipped;
    @JsonProperty("is_paid") 
    public boolean getIs_paid() { 
		 return this.ispaid; } 
    public void setIs_paid(boolean ispaid) { 
		 this.ispaid = ispaid; } 
    boolean ispaid;
    @JsonProperty("create_timestamp") 
    public int getCreate_timestamp() { 
		 return this.createtimestamp; } 
    public void setCreate_timestamp(int createtimestamp) { 
		 this.createtimestamp = createtimestamp; } 
    int createtimestamp;
    @JsonProperty("update_timestamp") 
    public int getUpdate_timestamp() { 
		 return this.updatetimestamp; } 
    public void setUpdate_timestamp(int updatetimestamp) { 
		 this.updatetimestamp = updatetimestamp; } 
    int updatetimestamp;
    @JsonProperty("gift_message") 
    public String getGift_message() { 
		 return this.giftmessage; } 
    public void setGift_message(String giftmessage) { 
		 this.giftmessage = giftmessage; } 
    String giftmessage;
    @JsonProperty("grandtotal") 
    public OrderGrandtotal getGrandtotal() { 
		 return this.grandtotal; } 
    public void setGrandtotal(OrderGrandtotal grandtotal) { 
		 this.grandtotal = grandtotal; } 
    OrderGrandtotal grandtotal;
  
    @JsonProperty("subtotal") 
    public OrderSubtotal getSubtotal() { 
		 return this.subtotal; } 
    public void setSubtotal(OrderSubtotal subtotal) { 
		 this.subtotal = subtotal; } 
    OrderSubtotal subtotal;
    @JsonProperty("total_price") 
    public OrderTotalprice getTotal_price() { 
		 return this.totalprice; } 
    public void setTotal_price(OrderTotalprice totalprice) { 
		 this.totalprice = totalprice; } 
    OrderTotalprice totalprice;
    @JsonProperty("total_shipping_cost") 
    public OrderTotalShippingCost getTotal_shipping_cost() { 
		 return this.totalshippingcost; } 
    public void setTotal_shipping_cost(OrderTotalShippingCost totalshippingcost) { 
		 this.totalshippingcost = totalshippingcost; } 
    OrderTotalShippingCost totalshippingcost;
    @JsonProperty("total_tax_cost") 
    public OrderTotalTaxCost getTotal_tax_cost() { 
		 return this.totaltaxcost; } 
    public void setTotal_tax_cost(OrderTotalTaxCost totaltaxcost) { 
		 this.totaltaxcost = totaltaxcost; } 
    OrderTotalTaxCost totaltaxcost;
    @JsonProperty("total_vat_cost") 
    public OrderTotalVatCost getTotal_vat_cost() { 
		 return this.totalvatcost; } 
    public void setTotal_vat_cost(OrderTotalVatCost totalvatcost) { 
		 this.totalvatcost = totalvatcost; } 
    OrderTotalVatCost totalvatcost;
    @JsonProperty("discount_amt") 
    public OrderDiscountAmt getDiscount_amt() { 
		 return this.discountamt; } 
    public void setDiscount_amt(OrderDiscountAmt discountamt) { 
		 this.discountamt = discountamt; } 
    OrderDiscountAmt discountamt;
    @JsonProperty("gift_wrap_price") 
    public OrderGiftWrapPrice getGift_wrap_price() { 
		 return this.giftwrapprice; } 
    public void setGift_wrap_price(OrderGiftWrapPrice giftwrapprice) { 
		 this.giftwrapprice = giftwrapprice; } 
    OrderGiftWrapPrice giftwrapprice;
    @JsonProperty("shipments") 
    public List<OrderShipment> getShipments() { 
		 return this.shipments; } 
    public void setShipments(List<OrderShipment> shipments) { 
		 this.shipments = shipments; } 
    List<OrderShipment> shipments;
    @JsonProperty("transactions") 
    public List<OrderTransaction> getTransactions() { 
		 return this.transactions; } 
    public void setTransactions(List<OrderTransaction> transactions) { 
		 this.transactions = transactions; } 
    List<OrderTransaction> transactions;
    
    @JsonProperty("shipdate") 
    public Date getShipdate() { 
		 return this.shipdate; } 
    public void setShipdate(Date shipdate) { 
		 this.shipdate = shipdate; } 
    Date shipdate;
    
    @JsonProperty("is_approved") 
    public Boolean getIsApproved() { 
		 return this.isapproved; } 
    public void setIsApproved(Boolean isapproved) { 
		 this.isapproved = isapproved; } 
    Boolean isapproved;
    
    @JsonProperty("PrinthouseId") 
    public Object getPrinthouseId() { 
		 return this.PrinthouseId; } 
    public void setPrinthouseId(Object PrinthouseId) { 
		 this.PrinthouseId = PrinthouseId; } 
    Object PrinthouseId;
    
    @JsonProperty("PrinthouseName") 
    public String getPrinthouseName() { 
		 return this.PrinthouseName; } 
    public void setPrinthouseName(String PrinthouseName) { 
		 this.PrinthouseName = PrinthouseName; } 
    String PrinthouseName;
    
    @JsonProperty("DefaultPrinthouseId") 
    public String getDefaultPrinthouseId() { 
		 return this.DefaultPrinthouseId; } 
    public void setDefaultPrinthouseId(String DefaultPrinthouseId) { 
		 this.DefaultPrinthouseId = DefaultPrinthouseId; } 
    String DefaultPrinthouseId;
    
    @JsonProperty("ArrovalDate") 
    public Date getArrovalDate() { 
		 return this.ArrovalDate; } 
    public void setArrovalDate(Date ArrovalDate) { 
		 this.ArrovalDate = ArrovalDate; } 
    Date ArrovalDate;
    
    @JsonProperty("notesforbuyer") 
    public String getnotesforbuyer() { 
		 return this.notesforbuyer; } 
    public void setnotesforbuyer(String notesforbuyer) { 
		 this.notesforbuyer = notesforbuyer; } 
    String notesforbuyer;
    
    @JsonProperty("notesforseller") 
    public String getnotesforseller() { 
		 return this.notesforseller; } 
    public void setnotesforseller(String notesforseller) { 
		 this.notesforseller = notesforseller; } 
    String notesforseller;
    
    @JsonProperty("giftnotes") 
    public String getgiftnotes() { 
		 return this.giftnotes; } 
    public void setgiftnotes(String giftnotes) { 
		 this.giftnotes = giftnotes; } 
    String giftnotes;
    
    @JsonProperty("isvalidaddress") 
    public Boolean getisvalidaddress() { 
		 return this.isvalidaddress; } 
    public void setisvalidaddress(Boolean isvalidaddress) { 
		 this.isvalidaddress = isvalidaddress; } 
    Boolean isvalidaddress;
    
    @JsonProperty("configureshipment") 
    public ConfigureShipment getconfigureshipment() { 
		 return this.configureshipment; } 
    public void setconfigureshipment(ConfigureShipment configureshipment) { 
		 this.configureshipment = configureshipment; } 
    ConfigureShipment configureshipment;
    
    @JsonProperty("mail_class") 
    public String getmail_class() { 
		 return this.mailclass; } 
    public void setmail_class(String mail_class) { 
		 this.mailclass = mail_class; } 
    String mailclass;
    
    @JsonProperty("min_processing_days") 
    public String getmin_processing_days() { 
		 return this.minprocessingdays; } 
   public void setmin_processing_days(String min_processing_days) { 
		 this.minprocessingdays = min_processing_days; } 
   String minprocessingdays;
    
    @JsonProperty("max_processing_days") 
    public String getmax_processing_days() { 
		 return this.maxprocessingdays; } 
    public void setmax_processing_days(String max_processing_days) { 
		 this.maxprocessingdays = max_processing_days; } 
    String maxprocessingdays;
    
    @JsonProperty("shopname") 
    public String getshopname() { 
		 return this.shopname; } 
    public void setshopname(String shopname) { 
		 this.shopname = shopname; } 
    String shopname;
    @JsonProperty("shoptitle") 
    public String getshoptitle() { 
		 return this.shoptitle; } 
    public void setshoptitle(String shoptitle) { 
		 this.shoptitle = shoptitle; } 
    String shoptitle;
    
    @JsonProperty("shipfrom") 
    public String getshipfrom() { 
		 return this.shipfrom; } 
    public void setshipfrom(String shipfrom) { 
		 this.shipfrom = shipfrom; } 
    String shipfrom;
 
	/*
	 * @JsonProperty("ordercutomized") public Optional<CustomizedOrders>
	 * getordercutomized() { return this.ordercutomized; } public void
	 * setordercutomized(Optional<CustomizedOrders> objOrderCutomized) {
	 * this.ordercutomized = objOrderCutomized; } Optional<CustomizedOrders>
	 * ordercutomized;
	 */
}
