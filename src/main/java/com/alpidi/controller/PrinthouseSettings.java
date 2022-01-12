package com.alpidi.controller;

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

import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.PrinthouseLabelSettings;
import com.alpidi.model.Response;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.PrinthouseLabelSettingRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class PrinthouseSettings {
	@Autowired
	PrinthouseLabelSettingRepository printhouseLabelRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/saveprinthouselabelsettings")
	public ResponseEntity<?> savePrinthouseLabelSettings(@RequestBody PrinthouseLabelSettings printhouseLabelSettings) {
		try
		{			
			System.out.println(printhouseLabelSettings);
			Boolean checkStatus = printhouseLabelRepository.existsByPrinthouseuserid(printhouseLabelSettings.getprinthouseuserid());
			if(checkStatus==true)
			{
				printhouseLabelRepository.deletePrinthouseLabelSettingsByPrinthouseuserid(printhouseLabelSettings.getprinthouseuserid());
			}
			printhouseLabelRepository.save(printhouseLabelSettings);
			return ResponseEntity.ok(new MessageResponse("Printhouse label settings saved."));
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return ResponseEntity.badRequest().body(new MessageResponse("Printhouse label settings saved failed!"));
		}
	}
	
	@GetMapping("/getprinthouselabelsettings")
	public ResponseEntity<?> getPrinthouseLabelSettings(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();
				
				Optional<PrinthouseLabelSettings> objPrinthouselabel= printhouseLabelRepository.findByPrinthouseuserid(loginuserid);
				return ResponseEntity.ok(objPrinthouselabel);
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
}
