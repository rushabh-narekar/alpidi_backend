package com.alpidi.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alpidi.exception.APIConnectionException;
import com.alpidi.exception.APIException;
import com.alpidi.exception.AuthenticationException;
import com.alpidi.exception.InvalidRequestException;
import com.alpidi.model.Activities;
import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.AssignOrdersPrintHouse;
import com.alpidi.model.Cities;
import com.alpidi.model.ConfigureShipment;
import com.alpidi.model.Countries;
import com.alpidi.model.CustomizedOrders;
import com.alpidi.model.Etsy;
import com.alpidi.model.Inventory;
import com.alpidi.model.ListingProperties;
import com.alpidi.model.ListingResult;
import com.alpidi.model.OrderDetails;
import com.alpidi.model.OrderTransaction;
import com.alpidi.model.PrinthouseLabelSettings;
import com.alpidi.model.Rate;
import com.alpidi.model.Rate2;
import com.alpidi.model.Response;
import com.alpidi.model.ShipAddress;
import com.alpidi.model.Shipment;
import com.alpidi.model.Shipments;
import com.alpidi.model.Shippo;
import com.alpidi.model.Shops;
import com.alpidi.model.States;
import com.alpidi.model.Transaction;
import com.alpidi.model.Transaction2;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.payload.response.UploadFileResponse;
import com.alpidi.repository.ActivitiesRepository;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.AddressRepository;
import com.alpidi.repository.AssignedPrintHouseRepository;
import com.alpidi.repository.CitiesRepository;
import com.alpidi.repository.ConfigureShipmentRepository;
import com.alpidi.repository.CountriesRepository;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.InventoryRepository;
import com.alpidi.repository.ListingPropertiesRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.OrderCutomizationRepository;
import com.alpidi.repository.OrdersRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.StatesRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.AssignPrinthouseServices;
import com.alpidi.security.sevices.PrinthouseOrderServices;
import com.google.gson.Gson;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class PrinthouseOrderController {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	CountriesRepository countriesRepository;
	@Autowired
	StatesRepository statesRepository;
	@Autowired
	CitiesRepository citiesRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	AssignedPrintHouseRepository printhouseRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	ConfigureShipmentRepository configureShipmentRepository;
	@Autowired
    private AdditionalSettingsRepository additionalSettingsRepository;
	@Autowired
	ListingPropertiesRepository listingPropertiesRepository;
	@Autowired
	ActivitiesRepository activitiesRepository;
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	PrinthouseOrderServices printhouseOrderServices;
	@Autowired
	AssignPrinthouseServices assignPrinthouseServices;
	@Autowired
	OrderCutomizationRepository orderCutomizationRepository;

	@Autowired
	AddressRepository addressRepository;
	@Autowired
	OrdersRepository ordersRepository;
	
	@Value("${shippo_url}")
	private String shippo_url;	
	@Value("${shippo_auth_token}")
	private String shippo_auth_token;
	
	@GetMapping("/getassignedorders")
	public ResponseEntity<?> getAssignedOrders(
			@RequestParam("authtoken") String authtoken,
			@RequestParam(required = false) String query,
			@RequestParam("proccess") int proccess,
			@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "10") int size) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();
//				Pageable paging = PageRequest.of(page, size);		      
//			    Page<OrderDetails> pageOrders;
			 
				//List<AssignOrdersPrintHouse> listOrders= printhouseRepository.findByPrintHouseUserId(loginuserid);
				List<OrderDetails> listOrders = printhouseOrderServices.getPrintHouseOrders(loginuserid,proccess);
				
				if(!listOrders.isEmpty())
				{
					for(int i=0;i<listOrders.size();i++)
				      {
						Optional<ConfigureShipment> objconfigueshipment= configureShipmentRepository.findByOrderid(listOrders.get(i).getReceipt_id());
						if(!objconfigueshipment.isEmpty())
						{
							listOrders.get(i).setconfigureshipment(objconfigueshipment.get());
						}
						
				    	  List<OrderTransaction> listTransactions= listOrders.get(i).getTransactions();
				    	  for(int t=0;t < listTransactions.size();t++)
				    	  {
				    		  Optional<ListingResult>  objListing = listingRepository.findByListingid(listTransactions.get(t).getListing_id());
				    		  listTransactions.get(t).setImg75(objListing.get().getimg75());
				    		  listTransactions.get(t).setSku_number(objListing.get().getSku_number());
				    		  
				    		  Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderid(listOrders.get(i).getReceipt_id());
				    		  
				    		  if(!objCustomizedOrder.isEmpty())
				    		  {
				    			  if(objCustomizedOrder.get().gettransaction()!=null)
				    			  {
				    				  for(int v=0;v<objCustomizedOrder.get().gettransaction().size();v++)
					    			  {
				    					  listTransactions.get(t).setnoteforseller(objCustomizedOrder.get().gettransaction().get(v).getnoteforseller());
//				    					  if(!objCustomizedOrder.get().gettransaction().get(v).gettransitstatus().isEmpty())
//				    					  {
//				    						  listTransactions.get(t).settransitstatus(objCustomizedOrder.get().gettransaction().get(v).gettransitstatus());
//				    					  }
					    			  }
				    			  }
				    		  }
				    	  }
				    	  
				    	  
				    	  
				    	  Optional<AssignOrdersPrintHouse> objShipingDetails= printhouseRepository.findByOrderid(listOrders.get(i).getReceipt_id());
				    	  if(objShipingDetails.isEmpty())
				    	  {
				    		  if(additionalSettingsRepository.existsByUserid(loginuserid))
					    	  {
					    		  Optional<AdditionalSettings> objAdditionalSettings= additionalSettingsRepository.findByuserid(loginuserid);
					    		  if(objAdditionalSettings.isEmpty()==false)
					    		  {
					    			  Optional<User> objUser = userRepository.findById(objAdditionalSettings.get().getPrintHouseUserid());
						    		  listOrders.get(i).setPrinthouseId(objAdditionalSettings.get().getPrintHouseUserid());
						    		  listOrders.get(i).setPrinthouseName(objUser.get().getFirstname()+" "+objUser.get().getLastname());
					    		  }
					    	  }	 
				    	  }
				    	  else
				    	  {
				    		  Optional<User> objUser = userRepository.findById(objShipingDetails.get().getPrintHouseUserid());
				    		  listOrders.get(i).setPrinthouseId(objUser.get().getId());
				    		  listOrders.get(i).setPrinthouseName(objUser.get().getFirstname()+" "+objUser.get().getLastname());
				    	  }
				      }
				}
				
				
				return ResponseEntity.ok(listOrders);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/calculateshipinglabelrate", method = RequestMethod.POST, produces = "application/json")
   	public ResponseEntity<?> calculateShipingLabelRate(@RequestBody ConfigureShipment configureShipment) {
		try
		{
			Shipments objShipments = printhouseOrderServices.createShipment(configureShipment);
			return ResponseEntity.ok(objShipments.getRates());
		}
		catch(Exception ex)
		{
			System.out.println("calculate shiping label rate : " + ex.getMessage());
			return null;
		}	
	}
	
	@RequestMapping(value = "/createconfigureshipment", method = RequestMethod.POST, produces = "application/json")
   	public ResponseEntity<?> createConfigureShipment(@RequestBody ConfigureShipment configureShipment) {
		try
		{			
			Shipments objShipments = printhouseOrderServices.createShipment(configureShipment);

			List<Rate2> listRate= objShipments.getRates();
			String rateobjectid=listRate.get(0).getObject_id();
			
			for(int i=0;i<listRate.size();i++)
			{
				if(configureShipment.getservice().equals(listRate.get(i).getServicelevel().gettoken()))
				{
					rateobjectid=listRate.get(i).getObject_id();
				}
			}
			System.out.println("rateobjectid : " + rateobjectid);
			Map<String, Object> transParams = new HashMap<String, Object>();
			transParams.put("rate", rateobjectid);
			transParams.put("async", false);
			
			String url = shippo_url+"/transactions/";	
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("charset", "utf-8");
			headers.add("Authorization", "ShippoToken "+shippo_auth_token);
			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(transParams,headers);
			
			ResponseEntity<Transaction2> response = restTemplate.exchange(url, HttpMethod.POST, entity, Transaction2.class);	
			
			configureShipment.setshipingTransaction(response.getBody());
			
			if(configureShipmentRepository.existsByLoginuseridAndOrderid(configureShipment.getloginuserid(),configureShipment.getorderid()))
			{
				configureShipmentRepository.deleteConfigureShipmentByLoginuseridAndOrderid(configureShipment.getloginuserid(),configureShipment.getorderid());
			}
			configureShipmentRepository.save(configureShipment);
			
			if(orderCutomizationRepository.existsByOrderid(configureShipment.getorderid()))
			{
				Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderid(configureShipment.getorderid());
				
				for(int i=0;i<objCustomizedOrder.get().gettransaction().size();i++)
				{
					objCustomizedOrder.get().gettransaction().get(i).setprogress(3);
					objCustomizedOrder.get().gettransaction().get(i).settransitstatus("pretransit");
				}
				orderCutomizationRepository.save(objCustomizedOrder.get());
			}
			
			if(ordersRepository.existsByReceiptid(configureShipment.getorderid()))
			{
				Optional<OrderDetails> objOrderDetails = ordersRepository.findByreceiptid(configureShipment.getorderid());
				objOrderDetails.get().setIs_shipped(true);
				ordersRepository.save(objOrderDetails.get());
			}
			
			Optional<Activities> objQuantity = activitiesRepository.findByLoginuseridAndOrderid(configureShipment.getloginuserid(),configureShipment.getorderid());
			
			if(!objQuantity.isEmpty())
			{
				objQuantity.get().setorderStatus(3);
			}
			activitiesRepository.save(objQuantity.get());
			
			Optional<OrderDetails> objOrder = ordersRepository.findByreceiptid(configureShipment.getorderid());
			
			for(int o=0;o<objOrder.get().getTransactions().size();o++)
			{
				Optional<Inventory> objInventory= inventoryRepository.findByloginuseridAndSizeAndColor(configureShipment.getloginuserid(),objOrder.get().getTransactions().get(o).getVariations().get(0).getFormatted_value(),objOrder.get().getTransactions().get(o).getVariations().get(1).getFormatted_value());
				if(!objInventory.isEmpty())
				{
					
					int newqty = objInventory.get().getquntity().intValue() - objOrder.get().getTransactions().get(o).getQuantity();
					objInventory.get().setquntity(newqty);
				}
				inventoryRepository.save(objInventory.get());
			}
			
			
			return ResponseEntity.ok(response.getBody());
		}
		catch(Exception ex)
		{
			System.out.println("calculate shiping label rate : " + ex.getMessage());
			return null;
		}	
	}
	@GetMapping("/getshipingtransaction")
   	public ResponseEntity<?> getShipingTransaction(@RequestParam("orderid") String orderid) {
		try
		{			
			Optional<ConfigureShipment> objConfigure = configureShipmentRepository.findByOrderid(orderid);
			if(!objConfigure.isEmpty())
			{
				System.out.println(objConfigure.get().getshipingTransaction().getobject_id());
				String url = shippo_url+"/transactions/"+objConfigure.get().getshipingTransaction().getobject_id();	
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("charset", "utf-8");
				headers.add("Authorization", "ShippoToken "+shippo_auth_token);
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				
				System.out.println(entity);
				ResponseEntity<Transaction2> response = restTemplate.exchange(url, HttpMethod.GET, entity, Transaction2.class);
				
				return ResponseEntity.ok(response.getBody());
			}
			else
			{
				Response resp = new Response(505, "No data found!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		catch (Exception e) {
			Response resp = new Response(505, e.getMessage(), null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getconfigureshipment")
   	public ResponseEntity<?> getConfigureShipment(@RequestParam("orderid") String orderid) {
		try
		{			
			Optional<ConfigureShipment> objConfigure = configureShipmentRepository.findByOrderid(orderid);
			if(!objConfigure.isEmpty())
			{
				return ResponseEntity.ok(objConfigure.get());
			}
			else
			{
				Response resp = new Response(505, "No data found!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		catch (Exception e) {
			Response resp = new Response(505, e.getMessage(), null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping("/approvedbyprinthouse")
	public ResponseEntity<?> approvedByPrinthouse(@RequestParam("loginuserid") String loginuserid,@RequestParam("orderid") String[] orderid,@RequestParam("listingid") String listingid){
		try
		{			
			for(int o=0;o<orderid.length;o++)
			{
					if(listingid.equals(""))
					{
						Optional<OrderDetails> objOrderDetails = ordersRepository.findByreceiptid(orderid[o]);
						if(!objOrderDetails.isEmpty())
						{
							for(int t=0;t<objOrderDetails.get().getTransactions().size();t++)
							{
								Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderidAndTransactionListingidContaining(orderid[o],objOrderDetails.get().getTransactions().get(t).getListing_id());
								
								for(int i=0;i<objCustomizedOrder.get().gettransaction().size();i++)
								{
									if(objCustomizedOrder.get().gettransaction().get(i).getListing_id().equals(objOrderDetails.get().getTransactions().get(t).getListing_id()))
									{
										objCustomizedOrder.get().gettransaction().get(i).setprogress(2);
										objCustomizedOrder.get().gettransaction().get(i).setisApprovedByPrinthouse(true);
									}
								}
								orderCutomizationRepository.save(objCustomizedOrder.get());
							}
							
						}
					}
					else
					{
						if(orderCutomizationRepository.existsByOrderid(orderid[o]))
						{
							Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderidAndTransactionListingidContaining(orderid[o],listingid);
							
							for(int i=0;i<objCustomizedOrder.get().gettransaction().size();i++)
							{
								if(objCustomizedOrder.get().gettransaction().get(i).getListing_id().equals(listingid))
								{
									objCustomizedOrder.get().gettransaction().get(i).setprogress(2);
									objCustomizedOrder.get().gettransaction().get(i).setisApprovedByPrinthouse(true);
								}
							}
							
							orderCutomizationRepository.save(objCustomizedOrder.get());
						}
					}
					
					Optional<Activities> objQuantity = activitiesRepository.findByLoginuseridAndOrderid(loginuserid, orderid[o]);
					
					if(!objQuantity.isEmpty())
					{
						objQuantity.get().setorderStatus(2);
					}
					activitiesRepository.save(objQuantity.get());
			}
			
			return ResponseEntity.ok(new MessageResponse("Order approved by printhouse."));
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return ResponseEntity.badRequest().body(new MessageResponse("Printhouse label settings saved failed!"));
		}
	}
	
	@RequestMapping("/getorderbyorderid")
	public ResponseEntity<?> getOrderByOrderId(@RequestParam("orderid") String orderid){
		try
		{			
			Optional<OrderDetails> objOrderDetails = ordersRepository.findByreceiptid(orderid);
			Optional<Shops> objShop = shopRepository.findByuserid(objOrderDetails.get().getSeller_user_id());
			if(!objShop.isEmpty())
			{
				objOrderDetails.get().setshopname(objShop.get().getShop_name());
				objOrderDetails.get().setshoptitle(objShop.get().getTitle());
			}
			
			Optional<ConfigureShipment> objConfigue = configureShipmentRepository.findByOrderid(orderid);
			if(!objConfigue.isEmpty())
			{
				Optional<ShipAddress> objshipaddress= addressRepository.findById(objConfigue.get().getshipaddressid());
				if(!objshipaddress.isEmpty())
				{
					Optional<Countries> objCountry = countriesRepository.findByCountryid(objshipaddress.get().getcountry());
					
					objOrderDetails.get().setshipfrom(objshipaddress.get().getlocationname() + " " + objCountry.get().getiso2() + " " +objshipaddress.get().getzipcode() + " " + objCountry.get().getname());
				}
				
			}
			
			for(int i=0;i<objOrderDetails.get().getTransactions().size();i++)
			{
				Optional<ListingResult> objListing= listingRepository.findByListingid(objOrderDetails.get().getTransactions().get(i).getListing_id());
				if(!objListing.isEmpty())
	  		  	{		    		    			  
					objOrderDetails.get().getTransactions().get(i).setImg75(objListing.get().getimg75());
					objOrderDetails.get().getTransactions().get(i).setSku_number(objListing.get().getSku_number());
					
					 Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderid(orderid);
		    		  
		    		  if(!objCustomizedOrder.isEmpty())
		    		  {
		    			  if(objCustomizedOrder.get().gettransaction()!=null)
		    			  {
		    				  for(int v=0;v<objCustomizedOrder.get().gettransaction().size();v++)
			    			  {
		    					  objOrderDetails.get().getTransactions().get(i).setnoteforseller(objCustomizedOrder.get().gettransaction().get(v).getnoteforseller());
			    			  }
		    			  }
		    		  }
					
					Optional<ListingProperties> vectorfileData = listingPropertiesRepository.findBylistingid(objListing.get().getListingid());
					if(!vectorfileData.isEmpty())
					{
						ListingProperties vectorfile = vectorfileData.get();
						var vetorfilename=vectorfile.getfilename();
						
						List<UploadFileResponse> newres = new ArrayList<>();
						for(int v=0;v<vetorfilename.size();v++)
						{
							String fileDownoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
							.path("/api/auth/downloadFile/")
							.path("/" + objListing.get().getShopid() + "/" + objListing.get().getListingid() + "/")
							.path(vetorfilename.get(i))
							.toUriString();
							
							newres.add(new UploadFileResponse(vetorfilename.get(i), fileDownoadUri));
						}
						objOrderDetails.get().getTransactions().get(i).setvectorfiles(newres); 
					}
	  		  	}
			}
			
			return ResponseEntity.ok(objOrderDetails);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return ResponseEntity.badRequest().body(new MessageResponse("Printhouse label settings saved failed!"));
		}
	}
}
