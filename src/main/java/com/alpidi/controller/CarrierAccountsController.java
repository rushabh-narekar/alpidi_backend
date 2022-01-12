package com.alpidi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpidi.model.ApiResponse;
import com.alpidi.model.CarrierAccountResult;
import com.alpidi.model.Response;
import com.alpidi.repository.CarrierAccountRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.CarrierAccountServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class CarrierAccountsController {
	@Autowired
	CarrierAccountRepository carrierAccountRepository;
	
	@Autowired
	CarrierAccountServices carrierAccountServices;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Value("${shippo_url}")
	private String shippo_url;	
	
	@RequestMapping(value = "/updatecarrieraccounts", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> updateCarrierAccounts(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			int i=0;
			int page=1;
			do{
				System.out.println("Page : " + page);
				CarrierAccountResult resultCarrierAccounts= carrierAccountServices.getAllCarrierAccounts(page);
				System.out.println(resultCarrierAccounts.getResults());
				for(int c=0;c<resultCarrierAccounts.getResults().size();c++)
				{
					carrierAccountServices.AddUpdateCarrierAccounts(resultCarrierAccounts.getResults().get(c));
				}
				
				if(!(resultCarrierAccounts.getNext()==null))
				{
					page++;
				}
				i++;
			}while (i<page); 
			
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Carrier Account Updated.", null), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<ApiResponse>(new ApiResponse(404, "User Authentication failed!", null), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getcarrieraccounts", method = RequestMethod.GET, produces = "application/json")
   	public ResponseEntity<?> getCarrierAccounts(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			return ResponseEntity.ok(carrierAccountRepository.findAll());
		}
		else
		{
			return new ResponseEntity<ApiResponse>(new ApiResponse(404, "User Authentication failed!", null), HttpStatus.OK);
		}
	}
}
