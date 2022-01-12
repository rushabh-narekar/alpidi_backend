package com.alpidi.security.sevices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.mapper.Mapper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpidi.exception.APIConnectionException;
import com.alpidi.exception.APIException;
import com.alpidi.exception.AuthenticationException;
import com.alpidi.exception.InvalidRequestException;
import com.alpidi.model.AssignOrdersPrintHouse;
import com.alpidi.model.Cities;
import com.alpidi.model.ConfigureShipment;
import com.alpidi.model.Countries;
import com.alpidi.model.CustomizedOrders;
import com.alpidi.model.ListingResult;
import com.alpidi.model.OrderDetails;
import com.alpidi.model.OrderTransaction;
import com.alpidi.model.Rate;
import com.alpidi.model.Rate2;
import com.alpidi.model.ShipAddress;
import com.alpidi.model.Shipment;
import com.alpidi.model.Shipments;
import com.alpidi.model.Shippo;
import com.alpidi.model.States;
import com.alpidi.repository.AddressRepository;
import com.alpidi.repository.AssignedPrintHouseRepository;
import com.alpidi.repository.CitiesRepository;
import com.alpidi.repository.CountriesRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.OrderCutomizationRepository;
import com.alpidi.repository.OrdersRepository;
import com.alpidi.repository.StatesRepository;
import com.google.gson.JsonObject;

@Service
public class PrinthouseOrderServices{
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	CountriesRepository countriesRepository;
	@Autowired
	StatesRepository statesRepository;
	@Autowired
	CitiesRepository citiesRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	OrdersRepository ordersRepository;
	@Autowired
	OrderCutomizationRepository orderCutomizationRepository;
	
	@Value("${shippo_url}")
	private String shippo_url;	
	@Value("${shippo_auth_token}")
	private String shippo_auth_token;
	
	public Shipments createShipment(ConfigureShipment configureShipment) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException
	{
		String url = shippo_url+"/shipments/";	
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("charset", "utf-8");
		headers.add("Authorization", "ShippoToken "+shippo_auth_token);
		
		Optional<ShipAddress> objshipaddress = addressRepository.findById(configureShipment.getshipaddressid());
		
		// to address
		Map<String, Object> toAddressMap = new HashMap<String, Object>();
		if(!objshipaddress.isEmpty())
		{
			toAddressMap.put("name", objshipaddress.get().getlocationname());
			toAddressMap.put("company", objshipaddress.get().getcompany());
			toAddressMap.put("street1", objshipaddress.get().getstreetaddress1());
			Optional<Cities> objCity = citiesRepository.findBycityid(objshipaddress.get().getcity());
			if(!objCity.isEmpty())
			{
				toAddressMap.put("city", objCity.get().getname());
			}
			Optional<States> objState = statesRepository.findByStateid(objshipaddress.get().getstate());
			if(!objState.isEmpty())
			{
				toAddressMap.put("state", objState.get().getstatecode());
			}
			toAddressMap.put("zip", objshipaddress.get().getzipcode());

			Optional<Countries> objCountry = countriesRepository.findByCountryid(objshipaddress.get().getcountry());
			if(!objCountry.isEmpty())
			{
				toAddressMap.put("country", objCountry.get().getiso2());
			}
			toAddressMap.put("phone", objshipaddress.get().getphone());
			toAddressMap.put("email", objshipaddress.get().getemail());
			
//			toAddressMap.put("name", "Mr Hippo");
//			toAddressMap.put("company", "Shippo");
//			toAddressMap.put("street1", "216 Clayton St.");
//			toAddressMap.put("city", "San Francisco");
//			toAddressMap.put("state", "CA");
//			toAddressMap.put("zip", "94117");
//			toAddressMap.put("country", "US");
//			toAddressMap.put("phone", "+1 555 341 9393");
//			toAddressMap.put("email", "mrhippo@goshipppo.com");
		}
		System.out.println("toAddressMap : "+toAddressMap);
		
		Optional<OrderDetails> objOrder = ordersRepository.findByreceiptid(configureShipment.getorderid());
		
		System.out.println(objOrder.get().getCountry_iso());
		// from address
		Map<String, Object> fromAddressMap = new HashMap<String, Object>();
		if(!objOrder.isEmpty())
		{
			fromAddressMap.put("name", objOrder.get().getFirst_line());
			fromAddressMap.put("company", objOrder.get().getName());
			fromAddressMap.put("street1", objOrder.get().getFirst_line());
			fromAddressMap.put("city", objOrder.get().getCity());
			fromAddressMap.put("state", objOrder.get().getState());
			fromAddressMap.put("zip", objOrder.get().getZip());
			fromAddressMap.put("country", objOrder.get().getCountry_iso());
			fromAddressMap.put("email", objOrder.get().getSeller_email());
			fromAddressMap.put("phone", "+1 619 231 1515");
			fromAddressMap.put("metadata", "Customer ID 123456");
			
//			fromAddressMap.put("name", "Ms Hippo");
//			fromAddressMap.put("company", "San Diego Zoo");
//			fromAddressMap.put("street1", "2921 Zoo Drive");
//			fromAddressMap.put("city", "San Diego");
//			fromAddressMap.put("state", "CA");
//			fromAddressMap.put("zip", "92101");
//			fromAddressMap.put("country", "US");
//			fromAddressMap.put("email", "mshippo@goshipppo.com");
//			fromAddressMap.put("phone", "+1 619 231 1515");
//			fromAddressMap.put("metadata", "Customer ID 123456");
		}
		System.out.println("fromAddressMap : "+fromAddressMap);

		// parcel
		Map<String, Object> parcelMap = new HashMap<String, Object>();
		parcelMap.put("length", configureShipment.getlength());
		parcelMap.put("width", configureShipment.getwidth());
		parcelMap.put("height", configureShipment.getheight());
		parcelMap.put("distance_unit", "in");
		parcelMap.put("weight", configureShipment.getweight());
		parcelMap.put("mass_unit", configureShipment.getmassunit());
		List<Map<String, Object>> parcels = new ArrayList<Map<String, Object>>();
		parcels.add(parcelMap);

		Map<String, Object> shipmentMap = new HashMap<String, Object>();
		shipmentMap.put("address_to", toAddressMap);
		shipmentMap.put("address_from", fromAddressMap);
		shipmentMap.put("parcels", parcels);
		shipmentMap.put("async", false);
		
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(shipmentMap,headers);
		
		ResponseEntity<Shipments> response = restTemplate.exchange(url, HttpMethod.POST, entity, Shipments.class);	
		
		return response.getBody();
	}
	
	public List<OrderDetails> getPrintHouseOrders(String printhouseid,int proccess)
	{
		List<CustomizedOrders> listCustomizedOrder= orderCutomizationRepository.findByIsAssignedAndTransactionPrinthouseid(true,printhouseid);
		List<OrderDetails> listOrders = new ArrayList<OrderDetails>();
		
		if(!listCustomizedOrder.isEmpty())
		{
			for(int i=0;i<listCustomizedOrder.size();i++)
			{
				Optional<OrderDetails> objOrder = ordersRepository.findByreceiptid(listCustomizedOrder.get(i).getorderid());
				if(!objOrder.isEmpty())
				{
					for(int t=0;t<listCustomizedOrder.get(i).gettransaction().size();t++)
					{
						if(listCustomizedOrder.get(i).gettransaction().get(t).getprogress()==proccess)
						{
							listOrders.add(objOrder.get());
						}
					}
					
				}
			}
		}
		
		return listOrders;
	}
	
	public HashMap<String, List<OrderDetails>> exportPrintHouseOrders(String printhouseid)
	{
		HashMap<String, List<OrderDetails>> map = new HashMap<>();
		
		List<CustomizedOrders> listCustomizedOrder= orderCutomizationRepository.findByIsAssignedAndTransactionPrinthouseid(true,printhouseid);
		List<OrderDetails> neworders = new ArrayList<OrderDetails>();
    	List<OrderDetails> inprogressorders = new ArrayList<OrderDetails>();
    	List<OrderDetails> completedorders = new ArrayList<OrderDetails>();
    	
		if(!listCustomizedOrder.isEmpty())
		{
			for(int i=0;i<listCustomizedOrder.size();i++)
			{
				Optional<OrderDetails> objOrder = ordersRepository.findByreceiptid(listCustomizedOrder.get(i).getorderid());
				if(!objOrder.isEmpty())
				{
					for(int t=0;t<listCustomizedOrder.get(i).gettransaction().size();t++)
					{
					    for(int st=0;st<objOrder.get().getTransactions().size();st++)
					    {
					    	objOrder.get().getTransactions().get(st).setSku_number(listCustomizedOrder.get(i).gettransaction().get(t).getskunumber());
					    }
				    	 
						if(listCustomizedOrder.get(i).gettransaction().get(t).getprogress()==1)
						{	
							neworders.add(objOrder.get());  
						}
						else if(listCustomizedOrder.get(i).gettransaction().get(t).getprogress()==2)
						{	
							inprogressorders.add(objOrder.get());  
						}
						else if(listCustomizedOrder.get(i).gettransaction().get(t).getprogress()==3)
						{	
							completedorders.add(objOrder.get());  
						}
					}					
				}
			}
			map.put("new", neworders);
		    map.put("inprogress", inprogressorders);
		    map.put("completed", completedorders);
			  
		}
		
		return map;
	}
}
