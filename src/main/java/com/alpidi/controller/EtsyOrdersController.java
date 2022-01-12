package com.alpidi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alpidi.model.Activities;
import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.AssignOrdersPrintHouse;
import com.alpidi.model.ConfigureShipment;
import com.alpidi.model.CustomizedOrders;
import com.alpidi.model.Etsy;
import com.alpidi.model.Inventory;
import com.alpidi.model.ListingInventory;
import com.alpidi.model.ListingResult;
import com.alpidi.model.OrderCustomized;
import com.alpidi.model.OrderDetails;
import com.alpidi.model.OrderTransaction;
import com.alpidi.model.Response;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.payload.response.UploadFileResponse;
import com.alpidi.repository.ActivitiesRepository;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.AssignedPrintHouseRepository;
import com.alpidi.repository.ConfigureShipmentRepository;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.InventoryRepository;
import com.alpidi.repository.ListingInventoryRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.OrderCutomizationRepository;
import com.alpidi.repository.OrdersRepository;
import com.alpidi.repository.ShipingProfilesRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.repository.ListingPropertiesRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.AssignPrinthouseServices;
import com.alpidi.security.sevices.EtsySync;
import com.alpidi.security.sevices.OrderServices;
import com.alpidi.security.sevices.PrinthouseOrderServices;
import com.alpidi.security.sevices.SettingsSerivces;
import com.alpidi.security.sevices.ShoplistingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Configuration
@EnableScheduling
public class EtsyOrdersController {
	private static final Logger logger = LoggerFactory.getLogger(EtsyListingController.class);
	@Autowired
	EtsyRepository etsyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	ListingRepository listingRepository;
    @Autowired
    ListingPropertiesRepository listingPropertiesRepository;
    @Autowired
    ListingInventoryRepository listingInventoryRepository;
    @Autowired
    OrderCutomizationRepository orderCutomizationRepository;
    @Autowired
   	OrdersRepository orderRepository;
    @Autowired
	ShipingProfilesRepository shipingProfilesRepository;
    @Autowired
    ConfigureShipmentRepository configureShipmentRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ActivitiesRepository activitiesRepository;
    @Autowired
    private ShoplistingService shoplistingService;
    @Autowired
    EtsySync etsyAsync;
    @Autowired
    private AdditionalSettingsRepository additionalSettingsRepository;
    @Autowired
    private OrderServices orderService;
    @Autowired
	AssignedPrintHouseRepository printhouseRepository;
	@Autowired
	SettingsSerivces settingService;
	@Autowired 
	AssignPrinthouseServices assignPrinthouseServices;
	@Autowired
	PrinthouseOrderServices printhouseOrderServices;
    @Autowired
	RestTemplate restTemplate;	
    
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    //@Scheduled(cron = "0 0 0/12 * * *")
    //@Scheduled(cron = "0 0/2 * * * ?")
	@RequestMapping(value = "/etsy/runOrdersJob", method = RequestMethod.GET, produces = "application/json")
	public void scheduleFixedRateTask() {		
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<User> users = userRepository.findByRole("shopowner");
			for(int i=0; i < users.size(); i++)
		   	{
				orderService.storeOrder(users.get(i));
		   	}
		}
		catch(Exception e) {
			System.out.println("runOrdersJob : "+e.getMessage());
		}
	}	
	
    @RequestMapping(value = "/etsy/refreshOrders", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> getAllOrders(@RequestParam("authtoken") String authtoken) {
   		if(jwtUtils.validateJwtToken(authtoken))
   		{
   			try 
   			{	
   				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
   				Optional<User> userdata= userRepository.findByEmail(email);
   				Boolean result = orderService.storeOrder(userdata.get());
   					
	   			if(result)
	   			{
	   				return ResponseEntity.ok(new MessageResponse("Order list updated."));	   				
	   			}
	   			else
	   			{
	   				return ResponseEntity.badRequest().body(new MessageResponse("Orders store failed!"));
	   			}
   			} catch (Exception e) {
   		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
   		   	}
   		}
   		else
   		{
   			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
   			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
   		}
   	}
    
    @RequestMapping(value = "/etsy/refreshOrdersbyConnectedId", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> getAllOrdersByShops(@RequestParam("authtoken") String authtoken, @RequestParam("ConnectedId") String ConnectedId) {
   		if(jwtUtils.validateJwtToken(authtoken))
   		{
   			try 
   			{
   				Boolean result = orderService.storeOrderByConnectedId(ConnectedId);
	   			if(result)
	   			{
	   				return ResponseEntity.ok(new MessageResponse("Order list updated."));
	   			}
	   			else
	   			{
	   				return ResponseEntity.badRequest().body(new MessageResponse("Orders store failed!"));
	   			}
   			} catch (Exception e) {
   		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
   		   	}
   		}
   		else
   		{
   			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
   			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
   		}
   	}
    
    @RequestMapping(value = "/etsy/getorders", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getOrderList(
			  @RequestParam("authtoken") String authtoken,
			  @RequestParam(required = false) String query,
			  @RequestParam(required = false) Boolean isShipped,
			  @RequestParam Boolean isApproved,
			  @RequestParam(required = false) Date startdate,
			  @RequestParam(required = false) Date enddate,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "10") int size) {	
    	if(jwtUtils.validateJwtToken(authtoken))
		{
    		try {	
		      var email = jwtUtils.getUserNameFromJwtToken(authtoken);
		      Optional<User> userdata= userRepository.findByEmail(email);
			  List<Etsy> lstetsy = etsyRepository.findByUserid(userdata.get().getId());
				
			  Pageable paging = PageRequest.of(page, size);		      
		      Page<OrderDetails> pageOrders;
		      query = query == null ? "" : query;  
		      
		      ArrayList<String> Seller_Ids=new ArrayList<String>();
		      lstetsy.forEach((x) -> { Seller_Ids.add(x.getEtsyuserid()); });
		      
		      if(startdate!= null && enddate != null)
		      {
		    	  pageOrders = orderRepository.findBySelleruseridInAndIsshippedAndShipdateBetweenAndTransactionsTitleContainingOrderByUpdatetimestampDesc(Seller_Ids,isShipped,startdate,enddate, query , paging);
		      }
		      else
		      {
		    	  pageOrders = orderRepository.findBySelleruseridInAndIsshippedAndTransactionsTitleContainingOrderByUpdatetimestampDesc(Seller_Ids,isShipped,query , paging);
		      }
		      
		      List<OrderDetails> listOrders = new ArrayList<OrderDetails>();
		      
		      if(isShipped==true)
		      {
		    	  listOrders = pageOrders.getContent();
		      }
		      else
		      {
		    	  for(int c=0;c<pageOrders.getContent().size();c++)
			      {
			    	  Optional<CustomizedOrders> obj = orderCutomizationRepository.findByOrderidAndIsAssigned(pageOrders.getContent().get(c).getReceipt_id(), isApproved);
			    	  if(!obj.isEmpty())
			    	  {		    		  
			    		  listOrders.add(pageOrders.getContent().get(c));
			    	  }
			    	  
			      }
		      }
		      
		      for(int i=0;i<listOrders.size();i++)
		      {		    	  
		    	  String orderid = listOrders.get(i).getReceipt_id();		    	  
		    	  
		    	  List<OrderTransaction> listTransactions= listOrders.get(i).getTransactions();
		    	  
		    	  for(int t=0;t < listTransactions.size();t++)
		    	  {
		    		  String listingid = listTransactions.get(t).getListing_id();
		    		  Optional<ListingResult>  objListing = listingRepository.findByListingid(listingid);
		    		  Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderid(orderid);
		    		  
		    		  if(!objCustomizedOrder.isEmpty())
		    		  {
		    			  if(objCustomizedOrder.get().gettransaction()!=null)
		    			  {
		    				  for(int v=0;v<objCustomizedOrder.get().gettransaction().size();v++)
			    			  {
			    				  listTransactions.get(t).setnoteforseller(objCustomizedOrder.get().gettransaction().get(v).getnoteforseller());
			    				  
			    				  Optional<User> user = userRepository.findById(objCustomizedOrder.get().gettransaction().get(v).getprinthouseid());
				    			  listTransactions.get(t).setprinthouseid(user.get().getId());
				    			  listTransactions.get(t).setprinthousename(user.get().getFirstname()+" "+user.get().getLastname());
				    			  
				    			  Optional<Inventory> objInventory = inventoryRepository.findByloginuseridAndSizeAndColor(objCustomizedOrder.get().gettransaction().get(v).getprinthouseid(), listTransactions.get(t).getVariations().get(0).getFormatted_value(), listTransactions.get(t).getVariations().get(1).getFormatted_value());
				    			  if(!objInventory.isEmpty())
			    				  {
			    					  listTransactions.get(t).setproductionCost(objInventory.get().getproductionCost());
			    				  }
				    			  listTransactions.get(t).setisApprovedByPrinthouse(objCustomizedOrder.get().gettransaction().get(v).getisApprovedByPrinthouse());
				    			  
				    			  listTransactions.get(t).setSku_number(objCustomizedOrder.get().gettransaction().get(v).getskunumber());
//				    			  if(!objCustomizedOrder.get().gettransaction().get(v).gettransitstatus().isEmpty())
//		    					  {
//		    						  listTransactions.get(t).settransitstatus(objCustomizedOrder.get().gettransaction().get(v).gettransitstatus());
//		    					  }
				    			  
			    				  if(objCustomizedOrder.get().gettransaction().get(v).getVariations()!=null)
			    				  {
			    					  listTransactions.get(t).setVariations(objCustomizedOrder.get().gettransaction().get(v).getVariations());  
			    				  }
			    			  }
		    			  }
		    		  }
		    		  
		    		  if(!objListing.isEmpty())
		    		  {		    		    			  
		    			  listTransactions.get(t).setImg75(objListing.get().getimg75());
		    			  
			    		  Boolean isvectoruploaded = shoplistingService.checkIsVectorFileUploaded(listingid, objListing.get().getShopid());
				    	  if(isvectoruploaded)
				      	  {			      	 				    		  
				      	    List<UploadFileResponse> vectorfiles = shoplistingService.getVectorFiles(listingid, objListing.get().getShopid());
				      	    if(vectorfiles.size()>0)
				      	    {
				      	    	listTransactions.get(t).setvectorfiles(vectorfiles);
				      	    }
				      	  }			
				    	  Optional<ListingInventory> objInventory = listingInventoryRepository.findOneByListingid(listingid);
				    	  
				    	  if(!objInventory.isEmpty())
				    	  {
				    		  listTransactions.get(t).setinventory(objInventory.get());
				    	  }
		    		  }		    	  
		    	  }
		      }
		     
		      Map<String, Object> response = new HashMap<>();
		      
		      response.put("results", listOrders);
		      response.put("currentPage", pageOrders.getNumber());
		      response.put("count", pageOrders.getTotalElements());
		      response.put("totalPages", pageOrders.getTotalPages());

		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
	        	System.out.println("Get Orders : " + e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}    
    
    @RequestMapping(value = "/etsy/getordersforexport", method = RequestMethod.GET, produces = "application/json")
    public HashMap<String, Object> getOrdersForExport(@RequestParam("authtoken") String authtoken,@RequestParam(defaultValue = "") String[] userids) {
    	
    	if(jwtUtils.validateJwtToken(authtoken))
		{
    		try {	
		      var email = jwtUtils.getUserNameFromJwtToken(authtoken);
		      Optional<User> userdata= userRepository.findByEmail(email);
		      String loginuserid = userdata.get().getId();
		      
		      HashMap<String, Object> map = new HashMap<>();
		      HashMap<String, List<OrderDetails>> response = new HashMap<>(); 
		      
		      if(userdata.get().getRole().equals("admin"))
		      {
		    	  System.out.println(userids.length);
		    	  for(int i=0;i<userids.length;i++)
		    	  {
		    		  Optional<User> objuser = userRepository.findById(userids[i]);
		    		  System.out.println(objuser.get().getRole());
		    		  if(!objuser.isEmpty())
		    		  {
		    			  if(objuser.get().getRole().equals("shopowner"))
		    		      {
		    				  response = exportShopownerOrders(userids[i]);
		    		      }
		    			  else if(objuser.get().getRole().equals("printhouse"))
		    			  {
		    				  response = printhouseOrderServices.exportPrintHouseOrders(userids[i]);
		    			  }
		    			  map.put(userids[i], response.clone());
		    		  }
		    	  }
		      }
		      else if(userdata.get().getRole().equals("shopowner"))
		      {
		    	 response = exportShopownerOrders(loginuserid);
		    	 map.put(loginuserid, response.clone());
		      }
		      else
		      {
		    	  response = printhouseOrderServices.exportPrintHouseOrders(loginuserid);
		    	  map.put(loginuserid, response.clone());
		      }
			  return map;
		    } catch (Exception e) {
	        	System.out.println("get Orders For Export : " + e.getMessage());
	        	return null;
		    }
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			System.out.println("get Orders For Export : " +resp);
			return null;
		}
	}   
    
    
    public HashMap<String, List<OrderDetails>> exportShopownerOrders(String userid)
    {
    	List<Etsy> lstetsy = etsyRepository.findByUserid(userid);

		  ArrayList<String> Seller_Ids=new ArrayList<String>();
	      lstetsy.forEach((x) -> { Seller_Ids.add(x.getEtsyuserid()); });
	      
	      HashMap<String, List<OrderDetails>> map = new HashMap<>();
	      List<OrderDetails> listorders = orderRepository.findBySelleruseridInAndIsshippedOrderByUpdatetimestampDesc(Seller_Ids,true);
	      for(int i=0;i<listorders.size();i++)
	  	  {		    	 
	  		  Optional<CustomizedOrders> obj = orderCutomizationRepository.findByOrderid(listorders.get(i).getReceipt_id());
	  		  if(!obj.isEmpty())
	  		  {				    	  
		    	  for(int t=0;t<listorders.get(i).getTransactions().size();t++)
		    	  {
		    		  for(int ct=0;ct < obj.get().gettransaction().size();ct++)
				  	  {
		    			  listorders.get(i).getTransactions().get(t).setSku_number(obj.get().gettransaction().get(ct).getskunumber());			    		  
				  	  }
		    	  }
	  		  }
	  	  }
	      map.put("completed", listorders);
	      
	      
	      listorders = orderRepository.findBySelleruseridInAndIsshippedOrderByUpdatetimestampDesc(Seller_Ids,false);
	      List<OrderDetails> inprogressorders = new ArrayList<OrderDetails>();
	      List<OrderDetails> neworders = new ArrayList<OrderDetails>();
  	  
	      for(int i=0;i<listorders.size();i++)
	  	  {		    	 
	  		  Optional<CustomizedOrders> obj = orderCutomizationRepository.findByOrderid(listorders.get(i).getReceipt_id());
		    	  if(!obj.isEmpty())
		    	  {				    	  
		    		  for(int t=0;t<listorders.get(i).getTransactions().size();t++)
		    		  {
		    			  for(int ct=0;ct < obj.get().gettransaction().size();ct++)
				    	  {
		    				  listorders.get(i).getTransactions().get(t).setSku_number(obj.get().gettransaction().get(ct).getskunumber());			    		  
				    	  }
		    		  }
			    	  
		    		  if(obj.get().getisAssigned())
		    		  {
		    			  inprogressorders.add(listorders.get(i));  
		    		  }
		    		  else
		    		  {
		    			  neworders.add(listorders.get(i));
		    		  }
			      }
	  	  }
	      map.put("new", neworders);
	      map.put("inprogress", inprogressorders);
		  
	      return map;
    }
    
    @RequestMapping(value = "/etsy/cutomizedorder", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> cutomizedOrder(@RequestBody AssignOrdersPrintHouse assignOrdersPrintHouse){
    	try
    	{
    		assignPrinthouseServices.AssignOrderToPrinhouse(assignOrdersPrintHouse);
			
			return ResponseEntity.ok(new MessageResponse("Save successfully."));
    	}
    	catch(Exception ex) {
    		Response resp = new Response(505, "Some internal serve error!", null, null, null, null, null,null);
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    @RequestMapping(value = "/etsy/gettransactionfromcustomizedorder", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getTransactionFromCustomizedOrder(@RequestParam("orderid") String orderid){
    	try
    	{
    		 Optional<CustomizedOrders> objOrderCustomized = orderCutomizationRepository.findByOrderid(orderid);
    		 if(!objOrderCustomized.isEmpty())
    		 {
    			 return ResponseEntity.ok(objOrderCustomized.get().gettransaction()); 
    		 }
    		 else
    		 {
    			 return null;
    		 }			
    	}
    	catch(Exception ex) {
    		Response resp = new Response(505, "Some internal serve error!", null, null, null, null, null,null);
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
   
    @RequestMapping(value = "/etsy/savecutomizedorder", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> saveCutomizedOrder(@RequestBody CustomizedOrders customizedOrders){
    	try
    	{
    		if(orderCutomizationRepository.existsByOrderid(customizedOrders.getorderid()))
    		{
    			orderCutomizationRepository.deleteCustomizedOrdersByOrderid(customizedOrders.getorderid());
    		}
    		orderCutomizationRepository.save(customizedOrders);
    		
    		for(int i=0;i<customizedOrders.gettransaction().size();i++)
    		{
    			Activities newActivities = new Activities();
    			 
    			String printhouseid = customizedOrders.gettransaction().get(i).getprinthouseid();
    			newActivities.setloginuserid(printhouseid);
    			newActivities.setorderid(customizedOrders.getorderid());
        		
        		Optional<OrderDetails> objOrder = orderRepository.findByreceiptid(customizedOrders.getorderid());
        		if(!objOrder.isEmpty())
        		{
        			for(int t=0;t<objOrder.get().getTransactions().size();t++)
        			{
        				newActivities.setsendQuantity(objOrder.get().getTransactions().get(t).getQuantity());
        				
    					Optional<Inventory> objInventory = inventoryRepository.findByloginuseridAndSizeAndColor(printhouseid, objOrder.get().getTransactions().get(t).getVariations().get(0).getFormatted_value(), objOrder.get().getTransactions().get(t).getVariations().get(1).getFormatted_value());
    					
    					if(!objInventory.isEmpty())
    					{
    						newActivities.setinventoryid(objInventory.get().getid());
    					}
        			}
        		}
        		newActivities.setorderStatus(1);
        		
        		if(activitiesRepository.existsByloginuseridAndOrderidAndInventoryid(printhouseid, newActivities.getorderid(),newActivities.getinventoryid()))
        		{
        			activitiesRepository.deleteByloginuseridAndOrderidAndInventoryid(printhouseid, newActivities.getorderid(),newActivities.getinventoryid());
        		}
        		activitiesRepository.save(newActivities);
    		}
    		
    		
			
			return ResponseEntity.ok(new MessageResponse("Save successfully."));
    	}
    	catch(Exception ex) {
    		Response resp = new Response(505, "Some internal serve error!", null, null, null, null, null,null);
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
   
   
    public long printDifference(Date startDate, Date endDate){

        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        
        return elapsedHours;

    }
    
    public long getDateDifference(Date startDate, Date endDate){

        long duration = endDate.getTime() - startDate.getTime();
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        //long diffInSeconds = duration / 1000 % 60;
        
        return diffInSeconds;

    }


}
