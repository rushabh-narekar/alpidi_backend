package com.alpidi.security.sevices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.AssignOrdersPrintHouse;
import com.alpidi.model.CustomizedOrders;
import com.alpidi.model.CutomizedOrderTransaction;
import com.alpidi.model.Etsy;
import com.alpidi.model.ListingInventory;
import com.alpidi.model.ListingProducts;
import com.alpidi.model.ListingResult;
import com.alpidi.model.OrderCustomized;
import com.alpidi.model.Orders;
import com.alpidi.model.ShipingProfileDetails;
import com.alpidi.model.ShipingProfiles;
import com.alpidi.model.OrderDetails;
import com.alpidi.model.OrderTotalprice;
import com.alpidi.model.OrderTransaction;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.AssignedPrintHouseRepository;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.ListingInventoryRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.OrderCutomizationRepository;
import com.alpidi.repository.OrdersRepository;
import com.alpidi.repository.ShipingProfilesRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.repository.ListingPropertiesRepository;
import com.alpidi.security.jwt.JwtUtils;

@Service
public class OrderServices {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ShipingProfilesRepository shipingProfilesRepository;
	@Autowired
	OrdersRepository orderRepository;
	@Autowired
	EtsyRepository etsyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ListingPropertiesRepository listingPropertiesRepository;
	@Autowired
	ListingInventoryRepository listingInventoryRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	AssignedPrintHouseRepository printhouseRepository;
	@Autowired
	AdditionalSettingsRepository additionalSettingsRepository;
	@Autowired
	OrderCutomizationRepository orderCutomizationRepository;
	
	@Autowired
	private EtsyServices etsyServices;
	@Autowired
	private OrderServices orderServices;
	@Autowired
	private ShoplistingService shoplistingService;
	@Autowired
	SettingsSerivces settingService;
	@Autowired 
	AssignPrinthouseServices assignPrinthouseServices;
	
	@Value("${alpidi.app.etsy_token_url}")
	private String etsy_token_url;
	@Value("${alpidi.app.etsy_parent_url}")
	private String esty_parent_url;	
	@Value("${alpidi.app.clientid}")
	private String client_id;
	
	//Date format Fundtions
		private static final List<Integer> NON_BUSINESS_DAYS = Arrays.asList(
		            Calendar.SATURDAY,
		            Calendar.SUNDAY
		);
		 
		public static Date businessDaysFrom(Date date, int businessDays) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        for (int i = 0; i < Math.abs(businessDays);) {
	            calendar.add(Calendar.DAY_OF_MONTH, businessDays > 0 ? 1 : -1);
	            if (!NON_BUSINESS_DAYS.contains(calendar.get(Calendar.DAY_OF_WEEK))){
	                i++;
	            }
	        }
	        return calendar.getTime();
	    }
	    
	    public Date toDate(long timestamp) {
	        Date date = new Date(timestamp * 1000);
	        return date;
	    }
	    
	    public long getDateDiffrenceInHour(Date startDate, Date endDate){
	        long different = endDate.getTime() - startDate.getTime();

	        long secondsInMilli = 1000;
	        long minutesInMilli = secondsInMilli * 60;
	        long hoursInMilli = minutesInMilli * 60;
	        long daysInMilli = hoursInMilli * 24;

	        //long elapsedDays = different / daysInMilli;
	        //different = different % daysInMilli;

	        long elapsedHours = different / hoursInMilli;
	        different = different % hoursInMilli;

	        long elapsedMinutes = different / minutesInMilli;
	        different = different % minutesInMilli;

	        long elapsedSeconds = different / secondsInMilli;
	        
	        return elapsedHours;
	    }
	
	//API related code
		public Orders GetOrders(String shopid,Etsy etsy, int offset) {
			try {
				String url = esty_parent_url + "shops/"+shopid+"/receipts?limit=25&offset="+ offset;
				
				Etsy objEtsy = etsyServices.refreshtoken(etsy);
				if(objEtsy!=null)
				{
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
					headers.add("charset", "utf-8");
					headers.add("x-api-key", client_id);	
					headers.add("Authorization", "Bearer " + objEtsy.getAccesstoken());
					HttpEntity<String> entity = new HttpEntity<String>(headers);
					
					ResponseEntity<Orders> result = restTemplate.exchange(url, HttpMethod.GET, entity, Orders.class);			
					return result.getBody();
				}
				else
				{
					return null;
				}
				
			}
			catch(Exception ex) {
				System.out.println("GetOrders : " + ex.getMessage());
				return null;
			}
		}		
		
		public ShipingProfileDetails getShopShippingProfile(String listingid,String shipping_profile_id) {		// fetch record from db
			var etsyuserid = listingRepository.findByListingid(listingid).get().getUserid();
			var shopid = listingRepository.findByListingid(listingid).get().getShopid();
			try
			{
				Optional<Etsy> etsyData = etsyRepository.findByEtsyuserid(etsyuserid);
				Etsy etsy=etsyServices.refreshtoken(etsyData.get());
				if(etsy!=null)
				{
					String url = esty_parent_url + "shops/"+shopid+"/shipping-profiles/"+shipping_profile_id;	
					HttpHeaders headers = new HttpHeaders();
					headers.add("charset", "utf-8");
					headers.add("x-api-key", client_id);
					headers.add("Authorization", "Bearer " + etsy.getAccesstoken());
					HttpEntity<String> entity = new HttpEntity<String>(headers);
						
					ResponseEntity<ShipingProfileDetails> response = restTemplate.exchange(url, HttpMethod.GET, entity, ShipingProfileDetails.class);
					return response.getBody();		 
				}
				else
				{
					return null;
				}
			}
			catch(Exception ex)
			{
				System.out.println("getShopShippingProfile : "+ex.getMessage());
				return null;
			}
		}
		
		public ListingProducts getListingProduct(String listingid,String productid) {		
			var etsyuserid = listingRepository.findByListingid(listingid).get().getUserid();
			//var shopid = listingRepository.findByListingid(listingid).get().getShopid();
			try
			{
				Optional<Etsy> etsyData = etsyRepository.findByEtsyuserid(etsyuserid);
				Etsy etsy=etsyServices.refreshtoken(etsyData.get());
				if(etsy!=null)
				{
					String url = esty_parent_url + "listings/"+listingid+"/inventory/products/"+productid;	
					HttpHeaders headers = new HttpHeaders();
					headers.add("charset", "utf-8");
					headers.add("x-api-key", client_id);
					headers.add("Authorization", "Bearer " + etsy.getAccesstoken());
					HttpEntity<String> entity = new HttpEntity<String>(headers);
						
					ResponseEntity<ListingProducts> response = restTemplate.exchange(url, HttpMethod.GET, entity, ListingProducts.class);
					return response.getBody();		 
				}
				else
				{
					return null;
				}
			}
			catch(Exception ex)
			{
				System.out.println("getListingProduct : "+ex.getMessage());
				return null;
			}
		}
	
	
    
    public String getShopidFromOrderid(String orderid)  {
		try
		{
			Optional<OrderDetails> objOrder = orderRepository.findByreceiptid(orderid);
			Optional<ListingResult> objListing = listingRepository.findByListingid(objOrder.get().getTransactions().get(0).getListing_id());
			return objListing.get().getShopid();
		}
		catch(Exception ex)
		{
			System.out.println("getShopidFromOrderid : "+ex.getMessage());
			return null;
		}
	}
    
    public Date SetShipDate(String shipingProfileId,int createdTimeStamp)
	{
    	try {
			Optional<ShipingProfileDetails> objShipingProfile = shipingProfilesRepository.findByshippingprofileid(shipingProfileId);
			if(!objShipingProfile.isEmpty())
			{
				Date date = orderServices.toDate(createdTimeStamp);
		    	int businessDays = objShipingProfile.get().getMax_processing_days();
		    	Date ShipDate = businessDaysFrom(date, businessDays);
		    	
		    	return ShipDate;
			}
			else
			{
				return null;
			}
    	}
    	catch(Exception e) {
    		System.out.println("Error coming from ship date");
    		return null;
    	}
	}
	//Mongo DB related code	
	public Boolean storeOrder(User userdata)  {
		try
		{
			List<Etsy> lstetsy = etsyRepository.findByUserid(userdata.getId());
			Optional<AdditionalSettings> objSettings= settingService.getAdditionalSettings(userdata.getId());
			for(int e = 0; e < lstetsy.size(); e++)	{	
				Etsy objEtsy = lstetsy.get(e);
				if(objEtsy != null)
				{
					List<Shops> lstShops = shopRepository.findByUserid(objEtsy.getEtsyuserid());
					for (int s = 0; s < lstShops.size(); s++) {
						
						int process_count = 25;
						int totalrecord = 25;
						int i=0;	
						do {
							int offset = process_count * i;
							
							Orders orders = GetOrders(lstShops.get(s).getShop_id(), objEtsy, offset);							
							totalrecord = orders.getCount();
							AddUpdateOrders(orders.getResults(), objSettings, objEtsy.getUserid());							
							i++;
						}while (totalrecord > process_count*i); 
					}
				}
			}
		}
		catch(Exception e)
		{
			return false;
		}		   
		return true;
	}
	public OrderDetails OrderProcess(OrderDetails order_details, Optional<AdditionalSettings> objSettings) {
		try
		{
			Date ApprovalDate = new Date();
			String duration = "24";
			String printHouseUserId = order_details.getLogin_user_id();
			if(!objSettings.isEmpty())
		    {
				duration = objSettings.get().getApprovedduration(); 	
				printHouseUserId = objSettings.get().getPrintHouseUserid() != null ?  objSettings.get().getPrintHouseUserid() : printHouseUserId;
				
				if(objSettings.get().getmanualoption().equals("auto"))
				{					
					Boolean IsApproval = IsApproval(order_details, duration);
					order_details.setIsApproved(IsApproval);
					//fetch existing custmized order if yes hen do not set anything and if not there means not find then genrated and save
					
					if(!orderCutomizationRepository.existsByOrderid(order_details.getReceipt_id()))
					{
						CustomizedOrders newCustomizedOrder = new CustomizedOrders();
						
						newCustomizedOrder.setuserid(order_details.getLogin_user_id());
						newCustomizedOrder.setorderid(order_details.getReceipt_id());
						newCustomizedOrder.setisAssigned(IsApproval);
						
						List<CutomizedOrderTransaction> listTransaction = new ArrayList<CutomizedOrderTransaction>();
								
						for(int i=0;i<order_details.getTransactions().size();i++)
						{
							String listingid = order_details.getTransactions().get(i).getListing_id();
							Optional<ListingResult> objListing= listingRepository.findByListingid(listingid);
							
							CutomizedOrderTransaction objtransaction = new CutomizedOrderTransaction();
							
							objtransaction.setListing_id(listingid);
							
							Optional<ListingInventory> objInventory = listingInventoryRepository.findOneByListingid(listingid);
							if(!objInventory.isEmpty())
							{
								objtransaction.setskunumber(objInventory.get().getProducts().get(0).getSku());
							}
							
							if(!objListing.isEmpty())
							{
								objtransaction.setprinthouseid(objListing.get().getDefaultprinthouse());
							}
							
							listTransaction.add(objtransaction);
						}						
						newCustomizedOrder.settransaction(listTransaction);
							
						Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderid(order_details.getReceipt_id());
						if(!objCustomizedOrder.isEmpty())
						{
							newCustomizedOrder.setId(objCustomizedOrder.get().getId());	
						}
						orderCutomizationRepository.save(newCustomizedOrder);
					}
				}
				else if(objSettings.get().getmanualoption().equals("manual"))
				{
					order_details.setIsApproved(false);
					
					if(!orderCutomizationRepository.existsByOrderid(order_details.getReceipt_id()))
					{
						CustomizedOrders newCustomizedOrder = new CustomizedOrders();
						
						newCustomizedOrder.setuserid(order_details.getLogin_user_id());
						newCustomizedOrder.setorderid(order_details.getReceipt_id());
						newCustomizedOrder.setisAssigned(false);
						
						List<CutomizedOrderTransaction> listTransaction = new ArrayList<CutomizedOrderTransaction>();
								
						for(int i=0;i<order_details.getTransactions().size();i++)
						{
							String listingid = order_details.getTransactions().get(i).getListing_id();
							
							Optional<ListingResult> objListing= listingRepository.findByListingid(listingid);
							CutomizedOrderTransaction objtransaction = new CutomizedOrderTransaction();
							
							Optional<ListingInventory> objInventory = listingInventoryRepository.findOneByListingid(listingid);
							if(!objInventory.isEmpty())
							{
								objtransaction.setskunumber(objInventory.get().getProducts().get(0).getSku());
							}
							
							objtransaction.setListing_id(listingid);
							if(!objListing.isEmpty())
							{
								objtransaction.setprinthouseid(objListing.get().getDefaultprinthouse());
							}
							
							listTransaction.add(objtransaction);
						}						
						newCustomizedOrder.settransaction(listTransaction);
							
						Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderid(order_details.getReceipt_id());
						if(!objCustomizedOrder.isEmpty())
						{
							newCustomizedOrder.setId(objCustomizedOrder.get().getId());	
						}
						orderCutomizationRepository.save(newCustomizedOrder);
					}
				}
				else
				{
					order_details.setIsApproved(false);
					
					if(!orderCutomizationRepository.existsByOrderid(order_details.getReceipt_id()))
					{
						CustomizedOrders newCustomizedOrder = new CustomizedOrders();
						
						newCustomizedOrder.setuserid(order_details.getLogin_user_id());
						newCustomizedOrder.setorderid(order_details.getReceipt_id());
						newCustomizedOrder.setisAssigned(false);
						
						List<CutomizedOrderTransaction> listTransaction = new ArrayList<CutomizedOrderTransaction>();
								
						for(int i=0;i<order_details.getTransactions().size();i++)
						{
							String listingid = order_details.getTransactions().get(i).getListing_id();
							
							Optional<ListingResult> objListing= listingRepository.findByListingid(listingid);
							CutomizedOrderTransaction objtransaction = new CutomizedOrderTransaction();
							
							
							Optional<ListingInventory> objInventory = listingInventoryRepository.findOneByListingid(listingid);
							if(!objInventory.isEmpty())
							{
								objtransaction.setskunumber(objInventory.get().getProducts().get(0).getSku());
							}
							
							objtransaction.setListing_id(listingid);
							if(!objListing.isEmpty())
							{
								objtransaction.setprinthouseid(objListing.get().getDefaultprinthouse());
							}
							
							listTransaction.add(objtransaction);
						}						
						newCustomizedOrder.settransaction(listTransaction);
							
						Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderid(order_details.getReceipt_id());
						if(!objCustomizedOrder.isEmpty())
						{
							newCustomizedOrder.setId(objCustomizedOrder.get().getId());	
						}
						orderCutomizationRepository.save(newCustomizedOrder);
					}
				}
				
//				if(order_details.getIsApproved() && objSettings.get().getmanualoption()=="auto") {
//					AssignPrintHouseUserID(order_details.getReceipt_id(), printHouseUserId, order_details.getLogin_user_id());
//				}
		    }
			
			ApprovalDate = findApprovalDate(order_details.getCreate_timestamp(), duration);	
			order_details.setArrovalDate(ApprovalDate);						
		}
		catch(Exception ex) {
			System.out.println("OrderProcess Error : " + ex.getMessage());
		}
		return order_details;
	}
	
	public Boolean storeOrderByConnectedId(String connectedId)  {
		try
		{
			Optional<Etsy> objEtsy = etsyRepository.findByEtsyuserid(connectedId);
			Optional<AdditionalSettings> objSettings= settingService.getAdditionalSettings(objEtsy.get().getUserid());
			
			if(objEtsy != null)
			{
				List<Shops> lstShops = shopRepository.findByUserid(objEtsy.get().getEtsyuserid());
				for (int s = 0; s < lstShops.size(); s++) {
					
					int process_count = 25;
					int totalrecord = 25;
					int i=0;	
					do {
						int offset = process_count * i;						
						Orders orders = GetOrders(lstShops.get(s).getShop_id(), objEtsy.get(), offset);						
						totalrecord = orders.getCount();	
						
						AddUpdateOrders(orders.getResults(), objSettings,  objEtsy.get().getUserid());	
						i++;
					}while (totalrecord > process_count*i); 
				}
			}
			
		}
		catch(Exception e)
		{
			return false;
		}		   
		return true;
	}
	public Date findApprovalDate(int Craeted_timestemp, String Approval_duration) {
		try {
			Date creatdate = orderServices.toDate(Craeted_timestemp);
	    	Calendar cal = Calendar.getInstance(); 
	    	cal.setTime(creatdate); 
	    	cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(Approval_duration));	    	
	    	Date ArrovalDate = cal.getTime();
	    	return ArrovalDate;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	
	
	public Boolean saveListing(ListingResult listing)
	{
		try
		{	
			if(listingRepository.existsByListingidAndShopid(listing.getListingid(), listing.getShopid())) 
			{
				listingRepository.deleteListingByListingidAndShopid(listing.getListingid(), listing.getShopid());
			}
			listingRepository.save(listing);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println("saveInventory : " + ex.getMessage());
			return false;
		}
	}
	
	public boolean findSkuNumber(List<OrderTransaction> transaction) {
		try
		{
			for(int i=0; i <transaction.size(); i++)
			{
				String listingid = transaction.get(i).getListing_id();
				
				Optional<ListingResult> objListing = listingRepository.findByListingid(listingid);
				if(objListing.get().getSku_number()!=null)
				{
					ListingProducts objProduct = getListingProduct(listingid,transaction.get(i).getProduct_id());
					objListing.get().setSku_number(objProduct.getSku());
					
					saveListing(objListing.get());
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			System.out.println("find SkuNumber : "+ ex.getMessage());
			return false;
		}
	}
	
	public boolean AddUpdateOrders(List<OrderDetails> orders, Optional<AdditionalSettings> objSettings, String loginUserID) {
		try {		
			for(int j=0; j < orders.size(); j++) {	
				orders.get(j).setLogin_user_id(loginUserID);
				
				OrderDetails order_details = OrderProcess(orders.get(j), objSettings);
				orders.set(j, order_details);
				
				findSkuNumber(orders.get(j).getTransactions());
				
				Optional<ListingResult> objListing = listingRepository.findByListingid(orders.get(j).getTransactions().get(0).getListing_id());
				if(!objListing.isEmpty())
				{
//					if(!shoplistingService.checkIsVectorFileUploaded(objListing.get().getListingid(),objListing.get().getShopid()))
//					{
//						objListing.get().setPriority(1);						
//						listingRepository.deleteListingByListingidAndShopid(objListing.get().getListingid(), objListing.get().getShopid());
//						listingRepository.save(objListing.get());
//					}
					Date shipdate = SetShipDate(objListing.get().getShipping_profile_id(), order_details.getCreate_timestamp());
					if(shipdate!=null)
					{
						orders.get(j).setShipdate(shipdate);
					}
				}
				Optional<OrderDetails> old_order = orderRepository.findByreceiptid(orders.get(j).getReceipt_id());
				
				if(!old_order.isEmpty()) {
					//orderRepository.deleteOrdersByreceiptid(orders.get(j).getReceipt_id());
					orders.get(j).setId(old_order.get().getId());
				}
				
				orders.get(j).setisvalidaddress(settingService.CheckValidAdressOrNot(orders.get(j)));
			}
			orderRepository.saveAll(orders);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("AddUpdateOrders : " + e.getMessage());
			return false;
		}
	}
	public Boolean IsApproval(OrderDetails orders, String duration)
	{		
		 try {		 
			if(orders.getShipments().isEmpty())
			{
				
				Date creatdate = orderServices.toDate(orders.getCreate_timestamp());
			    Date now = new Date();
			    
			    long diffHour = getDateDiffrenceInHour(creatdate,now);	
		    	int ApprovedDuration = Integer.parseInt(duration);
		    	if(diffHour >= ApprovedDuration) {			    				    		
		    		return true;
		    	}
		    	else
		    	{
		    		return false;
		    	}
			}
			else
			{
				return false;
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception From IsApproval : "+ex.getMessage());
			return false;
		}	
	}	
	public Boolean AssignPrintHouseUserID(String orderid, String PrintHouseUserid, String shopUserId) {
		try {
			AssignOrdersPrintHouse objAssignPrinthouse = new AssignOrdersPrintHouse();
			
			objAssignPrinthouse.setOrderid(orderid);
			objAssignPrinthouse.setPrintHouseUserid(PrintHouseUserid);
			objAssignPrinthouse.setShopUserId(shopUserId);    			
	    	Boolean checkStatus = printhouseRepository.existsByOrderid(orderid);
	    	if(checkStatus==true)
	    	{
	    		printhouseRepository.deleteAssignedOrdersPrintHouseByOrderid(orderid);
	    	}
	    	printhouseRepository.save(objAssignPrinthouse);
	    	return true;
		}
		catch(Exception ex) {
			System.out.println("Exception From Assign Print House : "+ex.getMessage());
			return false;
		}
	}
	public Boolean SetOrderApprove(String orderid) {
		try {
			Optional<OrderDetails> order_details = orderRepository.findByreceiptid(orderid);
			order_details.get().setIsApproved(true);
			if(!order_details.isEmpty()) {
				orderRepository.deleteOrdersByreceiptid(orderid);
				System.out.println("Order deleted successfully");
			}
	    	orderRepository.save(order_details.get());
	    	return true;
		}
		catch(Exception ex) {
			System.out.println("Exception From Assign Print House : "+ex.getMessage());
			return false;
		}
	}
}
