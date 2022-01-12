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

import com.alpidi.model.DefaultConfiureShipment;
import com.alpidi.model.Response;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.SettingConfigureShipmentRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class SettingConfigureShipment {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SettingConfigureShipmentRepository settingConfigureShipmentRepository;
	
	@PostMapping("/savedefaultconfigureshipment")
	public ResponseEntity<?> saveDefaultConfigureShipment(@RequestBody DefaultConfiureShipment defaultconfigureshipment) {
		try
		{		
			System.out.println(defaultconfigureshipment);
			if(settingConfigureShipmentRepository.existsByLoginuserid(defaultconfigureshipment.getloginuserid()))
			{
				settingConfigureShipmentRepository.deleteDefaultConfiureShipmentShipmentByLoginuserid(defaultconfigureshipment.getloginuserid());
			}
			settingConfigureShipmentRepository.save(defaultconfigureshipment);
			return ResponseEntity.ok(new MessageResponse("Settings saved."));
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return ResponseEntity.badRequest().body(new MessageResponse("Settings saved failed!"));
		}
	}
	
	@GetMapping("/getdefaultshipmentsetting")
	public ResponseEntity<?> getDefaultShipmentSetting(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				String loginuserid=userdata.get().getId();
				
				Optional<DefaultConfiureShipment> objSettings= settingConfigureShipmentRepository.findByLoginuserid(loginuserid);
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
}
