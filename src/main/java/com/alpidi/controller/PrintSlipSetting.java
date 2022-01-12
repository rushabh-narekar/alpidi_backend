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

import com.alpidi.model.ApiResponse;
import com.alpidi.model.Response;
import com.alpidi.model.SettingPrintSlip;
import com.alpidi.model.User;
import com.alpidi.repository.PrintSlipRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class PrintSlipSetting {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	PrintSlipRepository printSlipRepository;
	@Autowired
	UserRepository userRepository;
	@PostMapping("/saveprintslipsetting")
	public ResponseEntity<?> savePrintSlipSetting(@RequestBody SettingPrintSlip settingPrintSlip) {
		try
		{	
			System.out.println(settingPrintSlip);
			Boolean checkStatus = printSlipRepository.existsByLoginuserid(settingPrintSlip.getloginuserid());
			
			if(checkStatus==true) {
				printSlipRepository.deleteSettingPrintSlipByLoginuserid(settingPrintSlip.getloginuserid());
			}
			printSlipRepository.save(settingPrintSlip);		
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Print Slip Setting Saved.", null), HttpStatus.OK);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Print Slip Setting save failed!", null), HttpStatus.OK);
		}
	}
	
	@GetMapping("/getprintslipsetting")
	public ResponseEntity<?> getPrintSlipSetting(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();
				
				Optional<SettingPrintSlip> objPrintslip= printSlipRepository.findByLoginuserid(loginuserid);
				return ResponseEntity.ok(objPrintslip);
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
