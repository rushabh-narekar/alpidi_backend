package com.alpidi.controller;


import java.net.InetSocketAddress;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.ApiResponse;
import com.alpidi.model.Response;
import com.alpidi.model.User;
import com.alpidi.payload.request.LoginRequest;
import com.alpidi.payload.request.SignupRequest;
import com.alpidi.payload.response.JwtResponse;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.RoleRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.UserDetailsImpl;
import com.google.gson.Gson;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	EtsyRepository etsyRepository;
	
	@Autowired
	AdditionalSettingsRepository additionalSettingsRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) 
	{
		Gson gson = new Gson();
		JwtResponse jwtResponse = null;
		try {
			Optional<User> userData = userRepository.findByEmail(loginRequest.getEmail());	
			if(!userData.isEmpty()) {
				if(userData.get().getProvider()=="GOOGLE")
				{
					Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), ""));
					SecurityContextHolder.getContext().setAuthentication(authentication);	
					UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();				
					String jwt = jwtUtils.generateJwtToken(authentication);				
					jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(),userDetails.getFirstname(),userDetails.getLastname(),userDetails.getEmail());
					
				}
				else
				{
					Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
					if(authentication.isAuthenticated()) {
						SecurityContextHolder.getContext().setAuthentication(authentication);				
						UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();						
						String jwt = jwtUtils.generateJwtToken(authentication);				
						jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(),userDetails.getFirstname(),userDetails.getLastname(),userDetails.getEmail());				
					}	
					else {
						return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Email and Password was not match!", null), HttpStatus.OK);
					}
				}
			}
			else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "User was not registered!", null), HttpStatus.OK);
			}
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "", gson.toJson(jwtResponse)), HttpStatus.OK);

		}
		catch(Exception ex) {
			if(ex.getMessage() == "Bad credentials") {
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Bad credentials!", null), HttpStatus.OK);
			}
			
			return new ResponseEntity<ApiResponse>(new ApiResponse(500, "internal Server Error!", null), HttpStatus.OK);
		}
	}
	
	@PostMapping("/me")
	public ResponseEntity<?> userinfo(@RequestHeader HttpHeaders headers) {
	    try
	    {
	    	String token = headers.getFirst("Authorization").replace("Bearer ", "");
	    	if(jwtUtils.validateJwtToken(token))
			{
				Gson gson = new Gson();
				Optional<User> userData = userRepository.findByEmail(jwtUtils.getUserNameFromJwtToken(token));
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "", gson.toJson(userData.get())), HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<ApiResponse>(new ApiResponse(401, "User authorization failed!", null), HttpStatus.OK);
			}
	    }
	    catch(Exception ex)
	    {
	    	return new ResponseEntity<ApiResponse>(new ApiResponse(500, "Internal Server Error!", null), HttpStatus.OK);
	    }
		
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		System.out.println(signUpRequest.getProvider());
		var provider=signUpRequest.getProvider();
		Gson gson = new Gson();
		JwtResponse jwtResponse = null;
		if(provider==null || provider.indexOf("GOOGLE") <= -1)
		{	
			
			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Email is already in use!", null), HttpStatus.OK);
			}
			User user = new User(signUpRequest.getFirstname(),signUpRequest.getLastname(), signUpRequest.getEmail(),
					encoder.encode(signUpRequest.getPassword()),signUpRequest.getRole(),signUpRequest.getPhoto(),signUpRequest.getProvider(),signUpRequest.getAuthtoken(),signUpRequest.getSocialid());

			
			userRepository.save(user);
			
			try {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
								
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(),userDetails.getFirstname(),userDetails.getLastname(),userDetails.getEmail());
			
				AdditionalSettings newsetting = new AdditionalSettings();
				newsetting.setApprovedduration("48");
				newsetting.setDurationtype("hh");
				newsetting.setmanualoption("manual");
				newsetting.setUserid(userDetails.getId());
				newsetting.setPrintHouseUserid(userDetails.getId());
				
				Boolean checkStatus = additionalSettingsRepository.existsByUserid(userDetails.getId());
				if(checkStatus==true)
				{
					Long resultDelete = additionalSettingsRepository.deleteAdditionalSettingsByUserid(userDetails.getId());
				}
				additionalSettingsRepository.save(newsetting);
				
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "", gson.toJson(jwtResponse)), HttpStatus.OK);
				}
				catch(Exception ex) {
					return new ResponseEntity<ApiResponse>( new ApiResponse(500, "Something went wrong!", null), HttpStatus.OK);
				}
		}
		else
		{
			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
								
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(),userDetails.getFirstname(),userDetails.getLastname(),userDetails.getEmail());
				
				AdditionalSettings newsetting = new AdditionalSettings();
				newsetting.setApprovedduration("48");
				newsetting.setDurationtype("hh");
				newsetting.setmanualoption("manual");
				newsetting.setUserid(userDetails.getId());
				newsetting.setPrintHouseUserid(userDetails.getId());
				
				Boolean checkStatus = additionalSettingsRepository.existsByUserid(userDetails.getId());
				if(checkStatus==true)
				{
					Long resultDelete = additionalSettingsRepository.deleteAdditionalSettingsByUserid(userDetails.getId());
				}
				additionalSettingsRepository.save(newsetting);
				
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "", gson.toJson(jwtResponse)), HttpStatus.OK);
			}
			else
			{
				User user = new User(signUpRequest.getFirstname(),signUpRequest.getLastname(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()),signUpRequest.getRole(),signUpRequest.getPhoto(),signUpRequest.getProvider(),signUpRequest.getAuthtoken(),signUpRequest.getSocialid());

				userRepository.save(user);
				
				try {
					Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

					SecurityContextHolder.getContext().setAuthentication(authentication);

					UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
					
					String jwt = jwtUtils.generateJwtToken(authentication);
					
					jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(),userDetails.getFirstname(),userDetails.getLastname(),userDetails.getEmail());
					
					AdditionalSettings newsetting = new AdditionalSettings();
					newsetting.setApprovedduration("48");
					newsetting.setDurationtype("hh");
					newsetting.setmanualoption("manual");
					newsetting.setUserid(userDetails.getId());
					newsetting.setPrintHouseUserid(userDetails.getId());
					
					Boolean checkStatus = additionalSettingsRepository.existsByUserid(userDetails.getId());
					if(checkStatus==true)
					{
						Long resultDelete = additionalSettingsRepository.deleteAdditionalSettingsByUserid(userDetails.getId());
					}
					additionalSettingsRepository.save(newsetting);
					
					return new ResponseEntity<ApiResponse>(new ApiResponse(200, "", gson.toJson(jwtResponse)), HttpStatus.OK);	
				}
				catch(Exception ex) {
					return new ResponseEntity<ApiResponse>(new ApiResponse(500, "Something went wrong!", null), HttpStatus.OK);
				}
			}
			
		}
	}
}
