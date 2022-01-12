package com.alpidi.security.sevices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpidi.model.CarrierAccountDetails;
import com.alpidi.model.CarrierAccountResult;
import com.alpidi.model.Etsy;
import com.alpidi.model.Listing;
import com.alpidi.model.Shops;
import com.alpidi.repository.CarrierAccountRepository;

@Service
public class CarrierAccountServices {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	CarrierAccountRepository carrierAccountRepository;
	
	@Value("${shippo_url}")
	private String shippo_url;	
		//API related functions
		public CarrierAccountResult getAllCarrierAccounts(int page) {
			try {
				
				String url = shippo_url+"/carrier_accounts?page="+page;	
					
				HttpHeaders headers = new HttpHeaders();
				headers.add("charset", "utf-8");
				headers.add("Authorization", "ShippoToken shippo_test_f07a976b3e236e392e8be94656d650b35f23b4d5");
				HttpEntity<String> entity = new HttpEntity<String>(headers);
					
				ResponseEntity<CarrierAccountResult> carrierResult = restTemplate.exchange(url, HttpMethod.GET, entity, CarrierAccountResult.class);			
				
				return carrierResult.getBody();
			}
			catch(Exception ex) {
				System.out.println("getAllCarrierAccounts : " + ex.getMessage());
				return null;
			}
		}
		
		//DB Oprations
		public Boolean AddUpdateCarrierAccounts(CarrierAccountDetails carrierAccount)
		{
			try
			{
				if(carrierAccountRepository.existsByObjectid(carrierAccount.getObject_id()))
				{
					carrierAccountRepository.deleteCarrierAccountDetailsByObjectid(carrierAccount.getObject_id());
				}
				carrierAccountRepository.save(carrierAccount);
				return true;
			}
			catch(Exception ex)
			{
				System.out.println("AddUpdateCarrierAccounts : " + ex.getMessage());
				return false;
			}
		}
}
