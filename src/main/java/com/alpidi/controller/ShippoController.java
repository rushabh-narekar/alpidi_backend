package com.alpidi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.CarrierAccountRepository;
import com.alpidi.repository.CitiesRepository;
import com.alpidi.repository.CountriesRepository;
import com.alpidi.repository.StatesRepository;
import com.alpidi.security.sevices.CarrierAccountServices;
import com.alpidi.model.Transaction;
import com.alpidi.model.ValidAddressDetails;
import com.alpidi.exception.APIConnectionException;
import com.alpidi.exception.APIException;
import com.alpidi.exception.AuthenticationException;
import com.alpidi.exception.InvalidRequestException;
import com.alpidi.model.Address;
import com.alpidi.model.CarrierAccount;
import com.alpidi.model.CarrierAccountDetails;
import com.alpidi.model.CarrierAccountResult;
import com.alpidi.model.Cities;
import com.alpidi.model.Countries;
import com.alpidi.model.Rate;
import com.alpidi.model.ShipAddress;
import com.alpidi.model.Shipment;
import com.alpidi.model.Shippo;
import com.alpidi.model.States;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class ShippoController {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	CountriesRepository countriesRepository;
	@Autowired
	StatesRepository statesRepository;
	@Autowired
	CitiesRepository citiesRepository;
	
	@Value("${shippo_url}")
	private String shippo_url;	
	@Value("${shippo_auth_token}")
	private String shippo_auth_token;
	
	@RequestMapping(value = "/createshippinglabel", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> createShippingLabel() throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
		Shippo.setApiKey(shippo_auth_token);

		// Optional defaults to false
		//Shippo.setDEBUG(true);
		
		// to address
		Map<String, Object> toAddressMap = new HashMap<String, Object>();
		toAddressMap.put("name", "Mr Hippo");
		toAddressMap.put("company", "Shippo");
		toAddressMap.put("street1", "216 Clayton St.");
		toAddressMap.put("city", "San Francisco");
		toAddressMap.put("state", "CA");
		toAddressMap.put("zip", "94117");
		toAddressMap.put("country", "US");
		toAddressMap.put("phone", "+1 555 341 9393");
		toAddressMap.put("email", "mrhippo@goshipppo.com");

		// from address
		Map<String, Object> fromAddressMap = new HashMap<String, Object>();
		fromAddressMap.put("name", "Ms Hippo");
		fromAddressMap.put("company", "San Diego Zoo");
		fromAddressMap.put("street1", "2921 Zoo Drive");
		fromAddressMap.put("city", "San Diego");
		fromAddressMap.put("state", "CA");
		fromAddressMap.put("zip", "92101");
		fromAddressMap.put("country", "US");
		fromAddressMap.put("email", "mshippo@goshipppo.com");
		fromAddressMap.put("phone", "+1 619 231 1515");
		fromAddressMap.put("metadata", "Customer ID 123456");

		// parcel
		Map<String, Object> parcelMap = new HashMap<String, Object>();
		parcelMap.put("length", "5");
		parcelMap.put("width", "5");
		parcelMap.put("height", "5");
		parcelMap.put("distance_unit", "in");
		parcelMap.put("weight", "2");
		parcelMap.put("mass_unit", "lb");
		List<Map<String, Object>> parcels = new ArrayList<Map<String, Object>>();
		parcels.add(parcelMap);

		Map<String, Object> shipmentMap = new HashMap<String, Object>();
		shipmentMap.put("address_to", toAddressMap);
		shipmentMap.put("address_from", fromAddressMap);
		shipmentMap.put("parcels", parcels);
		shipmentMap.put("async", false);

		Shipment shipment = Shipment.create(shipmentMap);
		
		System.out.println("Shipments : ");
		System.out.println(shipment);
		
		// select shipping rate according to your business logic
		// we select the first rate in this example
		
		List<Rate> rates = shipment.getRates();
		Rate rate = rates.get(0);

		System.out.println("Getting shipping label..");
		Map<String, Object> transParams = new HashMap<String, Object>();
		//transParams.put("shipment",shipment);
		//transParams.put("carrier_account","db440d1c03db4a3f99b8bc0a741c8a3a");
		//transParams.put("servicelevel_token","ups_standard");
		transParams.put("rate", rate.getObjectId());
		transParams.put("async", false);
		Transaction transaction = Transaction.create(transParams);
		
		
		System.out.println("Transaction : ");
		System.out.println(transaction);
		
		System.out.println("Status : "+transaction.getStatus());
		if (transaction.getStatus().equals("SUCCESS")) {
			System.out.println(String.format("Label url : %s", transaction.getLabelUrl()));
			System.out.println(String.format("Tracking number : %s", transaction.getTrackingNumber()));
		} else {
			System.out.println(String.format("An Error has occured while generating you label. Messages : %s", transaction.getMessages()));
		}
		
		return ResponseEntity.ok(transaction.getLabelUrl());		
	}
	
	
	@RequestMapping(value = "/createaddressvalidation", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> createAddressValidation(@RequestBody ShipAddress shipaddress) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
		Shippo.setApiKey("shippo_test_27b7fe20fa5239649dc3ad8664596710652d6ecd");

		// Optional defaults to false
		Shippo.setDEBUG(true);
		
		HashMap<String, Object> addressMap = new HashMap<String, Object>();
		addressMap.put("name", shipaddress.getlocationname());
		addressMap.put("company", shipaddress.getcompany());
		addressMap.put("street1", shipaddress.getstreetaddress1());
		Optional<Cities> objCity = citiesRepository.findBycityid(shipaddress.getcity());
		if(!objCity.isEmpty())
		{
			addressMap.put("city", objCity.get().getname());
		}
		Optional<States> objState = statesRepository.findByStateid(shipaddress.getstate());
		if(!objState.isEmpty())
		{
			addressMap.put("state", objState.get().getstatecode());
		}
		addressMap.put("zip", shipaddress.getzipcode());

		Optional<Countries> objCountry = countriesRepository.findByCountryid(shipaddress.getcountry());
		if(!objCountry.isEmpty())
		{
			addressMap.put("country", objCountry.get().getiso2());
		}
		addressMap.put("phone", shipaddress.getphone());
		addressMap.put("email", shipaddress.getemail());
		addressMap.put("validate", "true");

		Address createAddress = Address.create(addressMap);
		
		System.out.println(createAddress.getValidationResults().getIsValid());
		return ResponseEntity.ok(createAddress);	

	}
	
	@RequestMapping(value = "/getalladdress", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> getAddresses() {
		String url = shippo_url+"/addresses/";	
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("charset", "utf-8");
		headers.add("Authorization", "ShippoToken shippo_test_b18add29479bae12ffb8b439baa5ac80485ef571");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		return ResponseEntity.ok(response.getBody());		
	}
	
	@RequestMapping(value = "/validateaddress", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> validateAddress(@RequestParam("objectid") String objectid) {
	
		String url = shippo_url+"/addresses/"+objectid+"/validate/";	
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("charset", "utf-8");
		headers.add("Authorization", "ShippoToken shippo_test_b18add29479bae12ffb8b439baa5ac80485ef571");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		return ResponseEntity.ok(new MessageResponse("Address validated successfully."));		
	}
	
	@RequestMapping(value = "/getaddressbyobjectid", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> getAddressByObjectId(@RequestParam("objectid") String objectid) {
		String url = shippo_url+"/addresses/"+objectid;	
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("charset", "utf-8");
		headers.add("Authorization", "ShippoToken shippo_test_b18add29479bae12ffb8b439baa5ac80485ef571");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		return ResponseEntity.ok(response.getBody());		
	}
}
