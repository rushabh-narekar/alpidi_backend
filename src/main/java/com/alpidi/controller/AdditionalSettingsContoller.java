package com.alpidi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.Address;
import com.alpidi.model.ApprovalTime;
import com.alpidi.model.Cities;
import com.alpidi.model.Countries;
import com.alpidi.model.ListingResult;
import com.alpidi.model.Response;
import com.alpidi.model.ShipAddress;
import com.alpidi.model.Shippo;
import com.alpidi.model.Shops;
import com.alpidi.model.States;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.AddressRepository;
import com.alpidi.repository.ApprovalTimeRepository;
import com.alpidi.repository.CitiesRepository;
import com.alpidi.repository.CountriesRepository;
import com.alpidi.repository.StatesRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.SettingsSerivces;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AdditionalSettingsContoller {
	@Autowired
	AdditionalSettingsRepository additionalSettingsRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ApprovalTimeRepository approvalTimeRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	CountriesRepository countriesRepository;
	@Autowired
	StatesRepository statesRepository;
	@Autowired
	CitiesRepository citiesRepository;
	@Autowired
	SettingsSerivces settingService;	
	
	@Value("${shippo_auth_token}")
	private String shippo_auth_token;
	
	@PostMapping("/saveAdditionalSetting")
	public ResponseEntity<?> saveAdditionalSettings(@RequestBody AdditionalSettings additionalSettings) {
		try
		{
			String userid=additionalSettings.getUserid();
			String first="";
			String second = "";
			
			String[] arrSplit = additionalSettings.getApprovedduration().split(" ");
		
			first =arrSplit[0];
			second = arrSplit.length>1 ? arrSplit[1] : "";
			
			additionalSettings.setApprovedduration(first);
			additionalSettings.setDurationtype(second);
			
			Boolean checkStatus = additionalSettingsRepository.existsByUserid(userid);
			if(checkStatus==true)
			{
				Long resultDelete = additionalSettingsRepository.deleteAdditionalSettingsByUserid(userid);
			}
			additionalSettingsRepository.save(additionalSettings);
			return ResponseEntity.ok(new MessageResponse("Additional settings saved."));
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return ResponseEntity.badRequest().body(new MessageResponse("Additional settings saved failed!"));
		}
	}
	
	@GetMapping("/getadditionalsettings")
	public ResponseEntity<?> getAdditionalSettings(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();
				
				Optional<AdditionalSettings> objSettings= settingService.getAdditionalSettings(loginuserid);
				return ResponseEntity.ok(objSettings);
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
	
	@RequestMapping(value = "/getallselectedprinthouseuser", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllSelectedPrintHouseUser(@RequestParam("authtoken") String authtoken) {
	
		if(jwtUtils.validateJwtToken(authtoken))
		{	var email = jwtUtils.getUserNameFromJwtToken(authtoken);
			Optional<User> login_user= userRepository.findByEmail(email);
			
			
			
			Optional<AdditionalSettings> objSettings= settingService.getAdditionalSettings(login_user.get().getId());
			System.out.println(objSettings);
			if(!objSettings.isEmpty())
			{
				List<User> listUsers = new ArrayList<User>();
				
				String[] selectedPrinhouse = objSettings.get().getselectedprinthouseid();
				if(objSettings.get().getselectedprinthouseid()!=null)
				{
					for(int i=0;i<objSettings.get().getselectedprinthouseid().length;i++)
					{
						Optional<User> user = userRepository.findByIdAndIsdeleted(selectedPrinhouse[i],false);	
						listUsers.add(user.get());
					}
				}
				
				//listUsers.add(login_user.get());
				return ResponseEntity.ok(listUsers);
			}
			else
			{
				List<User> lstUsers = userRepository.findByRoleAndIsdeleted("printhouse",false);	
				lstUsers.add(login_user.get());
				return ResponseEntity.ok(lstUsers);
			}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/checkdefaultaddress")
	public ResponseEntity<?> CheckDefaultAddress(@RequestParam("authtoken") String authtoken) {
		try
		{
			if(jwtUtils.validateJwtToken(authtoken))
			{
				Optional<ShipAddress> objAddress = addressRepository.findByIsdefaultaddress(true);
				return ResponseEntity.ok(objAddress);
			}
			else
			{
				Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(Exception ex)
		{
			System.out.println("CheckDefaultAddress : " + ex.getMessage());
			return null;
		}
	}
	
	@PostMapping("/saveAddress")
	public ResponseEntity<?> saveAddress(@RequestBody ShipAddress shipAddress) {
		try
		{
			
			Shippo.setApiKey(shippo_auth_token);
			Shippo.setDEBUG(true);
			
			HashMap<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("name", shipAddress.getlocationname());
			addressMap.put("company", shipAddress.getcompany());
			addressMap.put("street1", shipAddress.getstreetaddress1());
			Optional<Cities> objCity = citiesRepository.findBycityid(shipAddress.getcity());
			if(!objCity.isEmpty())
			{
				addressMap.put("city", objCity.get().getname());
			}
			Optional<States> objState = statesRepository.findByStateid(shipAddress.getstate());
			if(!objState.isEmpty())
			{
				addressMap.put("state", objState.get().getstatecode());
			}
			addressMap.put("zip", shipAddress.getzipcode());

			Optional<Countries> objCountry = countriesRepository.findByCountryid(shipAddress.getcountry());
			if(!objCountry.isEmpty())
			{
				addressMap.put("country", objCountry.get().getiso2());
			}
			addressMap.put("phone", shipAddress.getphone());
			addressMap.put("email", shipAddress.getemail());
			addressMap.put("validate", "true");

			Address createAddress = Address.create(addressMap);
			System.out.println(createAddress.getValidationResults().getIsValid());
			
			shipAddress.setisvalidaddress(createAddress.getValidationResults().getIsValid());
			
			if(shipAddress.getisdefaultaddress())
			{
				List<ShipAddress> listAddress = addressRepository.findAll();
				
				for(int i=0;i<listAddress.size();i++)
				{
					listAddress.get(i).setisdefaultaddress(false);
					addressRepository.save(listAddress.get(i));
				}
			}
			addressRepository.save(shipAddress);
			
			return ResponseEntity.ok(new MessageResponse("Address save successfully"));
		}
		catch(Exception ex)
		{
			System.out.println("saveAddress : " + ex.getMessage());
			return ResponseEntity.badRequest().body(new MessageResponse("Address save failed!"));
		}
	}
	
	@GetMapping("/getaddress")
	public ResponseEntity<?> getAddress(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try
			{
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();
				
				List<ShipAddress> shipAddresses= addressRepository.findByLoginuseridAndIsdelete(loginuserid,false);
				
				for(int i=0;i<shipAddresses.size();i++)
				{
					Optional<Countries> objCountry = countriesRepository.findByCountryid(shipAddresses.get(i).getcountry());
					if(!objCountry.isEmpty())
					{
						shipAddresses.get(i).setcountryname(objCountry.get().getname());
					}
					Optional<States> objState = statesRepository.findByStateid(shipAddresses.get(i).getstate());
					if(!objState.isEmpty())
					{
						shipAddresses.get(i).setstatename(objState.get().getname());
					}
					Optional<Cities> objCity = citiesRepository.findBycityid(shipAddresses.get(i).getcity());
					if(!objCity.isEmpty())
					{
						shipAddresses.get(i).setcityname(objCity.get().getname());
					}
					
				}
				return ResponseEntity.ok(shipAddresses);
			}
			catch(Exception ex)
			{
				System.out.println("getAddress : " + ex.getMessage());
				return null;
			}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getalladdressess")
	public ResponseEntity<?> getAllAddress(@RequestParam("authtoken") String authtoken) {
			try
			{	
				if(jwtUtils.validateJwtToken(authtoken))
				{
					List<ShipAddress> shipAddresses= addressRepository.findByIsdelete(false);
					
					for(int i=0;i<shipAddresses.size();i++)
					{
						Optional<Countries> objCountry = countriesRepository.findByCountryid(shipAddresses.get(i).getcountry());
						if(!objCountry.isEmpty())
						{
							shipAddresses.get(i).setcountryname(objCountry.get().getname());
						}
						Optional<States> objState = statesRepository.findByStateid(shipAddresses.get(i).getstate());
						if(!objState.isEmpty())
						{
							shipAddresses.get(i).setstatename(objState.get().getname());
						}
						Optional<Cities> objCity = citiesRepository.findBycityid(shipAddresses.get(i).getcity());
						if(!objCity.isEmpty())
						{
							shipAddresses.get(i).setcityname(objCity.get().getname());
						}
						
					}
					return ResponseEntity.ok(shipAddresses);
				}
				else
				{
					Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
					return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			catch(Exception ex)
			{
				System.out.println("getAllAddress : " + ex.getMessage());
				return null;
			}
	}
	
	@GetMapping("/deleteaddress")
	public ResponseEntity<?> deleteAddress(@RequestParam("addressid") String addressid) {
		try
		{
			Optional<ShipAddress> shipAddresses= addressRepository.findById(addressid);
			if(!shipAddresses.isEmpty())
			{
				shipAddresses.get().setisdelete(true);
				addressRepository.save(shipAddresses.get());
			}
			return ResponseEntity.ok("Address Deleted Successfully.");
		}
		catch(Exception ex)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Address delete failed!"));
		}
	}
	@GetMapping("/getapprovaltime")
	public ResponseEntity<?> getApprovalTime() {
			try {
				
				List<ApprovalTime> listApprovalTime= approvalTimeRepository.findAll();
				return ResponseEntity.ok(listApprovalTime);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@GetMapping("/getcountries")
	public ResponseEntity<?> getCountries(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try
			{			
				List<Countries> listCountries= countriesRepository.findAll();
				return ResponseEntity.ok(listCountries);
			}
			catch(Exception ex)
			{
				System.out.println("getCountries : " + ex.getMessage());
				return null;
			}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getstates")
	public ResponseEntity<?> getStates(@RequestParam("countryid") String countryid) {
			try
			{			
				List<States> listStates= statesRepository.findByCountryid(countryid);
				return ResponseEntity.ok(listStates);
			}
			catch(Exception ex)
			{
				System.out.println("getStates : " + ex.getMessage());
				return null;
			}
	}
	
	@GetMapping("/getcities")
	public ResponseEntity<?> getCities(@RequestParam("stateid") String stateid) {
			try
			{			
				List<Cities> listCity= citiesRepository.findBystateid(stateid);
				return ResponseEntity.ok(listCity);
			}
			catch(Exception ex)
			{
				System.out.println("getCities : " + ex.getMessage());
				return null;
			}
	}
}
