package com.alpidi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpidi.model.Address;
import com.alpidi.model.ApiResponse;
import com.alpidi.model.AutomationRules;
import com.alpidi.model.Response;
import com.alpidi.model.ShipAddress;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.AddressRepository;
import com.alpidi.repository.AutomationRulesRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AutomationRulesController {
	@Autowired
	AutomationRulesRepository automationruleRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AddressRepository addressRepository;
	
	@PostMapping("/saveautomationrules")
	public ResponseEntity<?> saveAutomationRules(@RequestBody AutomationRules automationRules) {
		try
		{	
			System.out.println(automationRules);
			Boolean checkStatus = automationruleRepository.existsByLoginuserid(automationRules.getloginuserid());
			/*
			 * if(checkStatus==true) {
			 * automationruleRepository.deleteAutomationRulesByLoginuserid(automationRules.
			 * getloginuserid()); }
			 */
			automationruleRepository.save(automationRules);		
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Save automation rules saved", null), HttpStatus.OK);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Save automation rules failed!", null), HttpStatus.OK);
		}
	}
	
	@GetMapping("/getautomationrules")
	public ResponseEntity<?> getAutomationRules(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();
				
				List<AutomationRules> listAutomationRules= automationruleRepository.findByLoginuseridAndIsdelete(loginuserid,false);
				for(int i=0;i<listAutomationRules.size();i++)
				{
					if(!listAutomationRules.get(i).getaddressid().isEmpty())
					{
						Optional<ShipAddress> objAddress = addressRepository.findById(listAutomationRules.get(i).getaddressid());
						listAutomationRules.get(i).setaddressess(objAddress.get());
					}
				}
				return ResponseEntity.ok(listAutomationRules);
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
	
	@GetMapping("/deleteautomationrule")
	public ResponseEntity<?> deleteAutomationRul(@RequestParam("automationruleid") String automationruleid) {
		
			try {
				Optional<AutomationRules> objrule= automationruleRepository.findById(automationruleid);
				if(!objrule.isEmpty())
				{
					objrule.get().setisdelete(true);
					automationruleRepository.save(objrule.get());
				}
				return ResponseEntity.ok("Automation rule deleted successfully.");
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(new MessageResponse("Automation rule delete failed!"));
			}
	}
}
