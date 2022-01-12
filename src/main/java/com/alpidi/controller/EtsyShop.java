package com.alpidi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.alpidi.model.Etsy;
import com.alpidi.model.ListingResult;
import com.alpidi.model.ProductionPartner;
import com.alpidi.model.Response;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.ListingimagesRepository;
import com.alpidi.repository.ProductionPartnerRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.repository.ListingPropertiesRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.ShoplistingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class EtsyShop {
	@Autowired
	EtsyRepository etsyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	ListingPropertiesRepository listingPropertiesRepository;
	@Autowired
	ListingimagesRepository listingimagesRepository;
	@Autowired
	ProductionPartnerRepository productionPartnerRepository;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private ShoplistingService shopListingService;
	@Autowired
	JwtUtils jwtUtils;

	private static final Logger log = LoggerFactory.getLogger(EtsyShop.class);

	@RequestMapping(value = "/etsy/getshopbyuserId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getShopsByUserID(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();
				
				List<Shops> shopData = shopRepository.findByloginuserid(loginuserid);	
				return ResponseEntity.ok(shopData);
			} catch (Exception e) {
				log.error(EtsyAuth.class.getName(), e.getMessage());
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


	@RequestMapping(value = "/etsy/getshops", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getShops(@RequestParam("authtoken") String authtoken, @RequestParam("etsy_userid") String etsy_userid) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();	
				
				Optional<Etsy> etsyData = etsyRepository.findByEtsyuserid(etsy_userid);
					
				List<Shops> shops = shopListingService.getShops(etsy_userid);
				shopListingService.AddUpdateProductionPartners(etsyData.get());
				
				for(int s = 0; s < shops.size(); s++)
				{
					if(shops.get(s).getShop_id() != null)
					{
						Optional<Shops> checkshop = shopRepository.findByshopid(shops.get(s).getShop_id());
						if(!checkshop.isEmpty())
						{
							if(checkshop.get().getLoginuserid().equals(loginuserid))
							{
								return ResponseEntity.ok(new MessageResponse("Shop is already registered!"));
							}
							else
							{
								return ResponseEntity.ok(new MessageResponse("Shop is not registered becuase it connected to another account!"));
							}
						}
						else
						{
							shops.get(s).setLoginuserid(loginuserid);								
							shopRepository.save(shops.get(s));
							return ResponseEntity.ok(shops);
						}
					}
				}
				
				return ResponseEntity.ok(new MessageResponse("No any Etsy account was connected!"));
			} catch (Exception e) {
				log.error(EtsyAuth.class.getName(), e.getMessage());
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
	@RequestMapping(value = "/etsy/checkupdate", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> checkUpdateShop(@RequestParam("authtoken") String authtoken, @RequestParam("shopid") String shopid) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			var email = jwtUtils.getUserNameFromJwtToken(authtoken);
			Optional<User> userdata= userRepository.findByEmail(email);
			String loginuserid=userdata.get().getId();	
			
			try {			
				ObjectMapper mapper = new ObjectMapper();				
				Shops shops = shopListingService.getShopById(shopid);				
				shops.setLoginuserid(loginuserid);				
				Optional<Shops> existingshops = shopRepository.findByshopid(shopid);
				System.out.println(mapper.writeValueAsString(shops));
				System.out.println(mapper.writeValueAsString(existingshops.get()));
				return ResponseEntity.ok(!mapper.writeValueAsString(shops).equals(mapper.writeValueAsString(existingshops.get())));
			} catch (Exception e) {
				log.error(EtsyAuth.class.getName(), e.getMessage());
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
	
	@RequestMapping(value = "/etsy/getShippingProfiles", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getShippingProfiles(@RequestParam("authtoken") String authtoken, @RequestParam("etsyid") String etsyid) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {							
				List<Shops> shopData = shopRepository.findByUserid(etsyid);
				for(int s = 0; s < shopData.size(); s++) {
					shopListingService.getShippingProfiles(shopData.get(s).getShop_id());
				}
				return ResponseEntity.ok(true);
			} catch (Exception e) {
				log.error(EtsyAuth.class.getName(), e.getMessage());
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
	@RequestMapping(value = "/etsy/getListingDetails", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getListingDetails(@RequestParam("authtoken") String authtoken,@RequestParam("listingid") String listingid) {			
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				Optional<ListingResult> listingData = shopListingService.getListingDetails(listingid);
				System.out.println(listingData);
				if(!listingData.isEmpty())
				{
					listingData.get().setproduction_partner(productionPartnerRepository.findAll());
			    	return ResponseEntity.ok(listingData);
				}
				else
				{
					Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
					return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				log.error(EtsyAuth.class.getName(), e.getMessage());
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
}
