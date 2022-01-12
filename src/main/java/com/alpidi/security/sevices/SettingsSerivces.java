package com.alpidi.security.sevices;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alpidi.exception.APIConnectionException;
import com.alpidi.exception.APIException;
import com.alpidi.exception.AuthenticationException;
import com.alpidi.exception.InvalidRequestException;
import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.Address;
import com.alpidi.model.Cities;
import com.alpidi.model.Countries;
import com.alpidi.model.OrderDetails;
import com.alpidi.model.Shippo;
import com.alpidi.model.States;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.CitiesRepository;
import com.alpidi.repository.CountriesRepository;
import com.alpidi.repository.StatesRepository;
import com.alpidi.security.jwt.JwtUtils;

@Service
public class SettingsSerivces {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	AdditionalSettingsRepository additionalSettingsRepository;
	@Autowired
	StatesRepository statesRepository;
	@Autowired
	CitiesRepository citiesRepository;
	@Autowired
	CountriesRepository countryRepository;
	
	@Value("${shippo_url}")
	private String shippo_url;	
	@Value("${shippo_auth_token}")
	private String shippo_auth_token;
	
	public Boolean CheckValidAdressOrNot(OrderDetails orderDetails) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException
	{
		try
		{
			Shippo.setApiKey(shippo_auth_token);
			//Shippo.setDEBUG(true);
			
			HashMap<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("name", orderDetails.getFirst_line());
			//addressMap.put("company", shipAddress.getcompany());
			addressMap.put("street1", orderDetails.getFirst_line());
			
			addressMap.put("city", orderDetails.getCity());
			addressMap.put("state", orderDetails.getState());
			addressMap.put("zip", orderDetails.getZip());
			addressMap.put("country", orderDetails.getCountry_iso());
			
			//addressMap.put("phone", shipAddress.getphone());
			addressMap.put("email", orderDetails.getBuyer_email());
			addressMap.put("validate", "true");

			Address createAddress = Address.create(addressMap);
			
			return createAddress.getValidationResults().getIsValid();
		}
		catch(Exception ex)
		{
			System.out.println("CheckValidAdressOrNot : "+ex.getMessage());
			return false;
		}
	}
	
	public Optional<AdditionalSettings> getAdditionalSettings(String userid)
	{
		try {
			Optional<AdditionalSettings> objAdditionalSettings= additionalSettingsRepository.findByuserid(userid);
			return objAdditionalSettings;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
