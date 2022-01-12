package com.alpidi.security.sevices;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alpidi.controller.EtsyAuth;
import com.alpidi.model.Etsy;
import com.alpidi.model.EtsyTokenDetails;
import com.alpidi.model.Response;
import com.alpidi.model._Error;
import com.alpidi.repository.EtsyRepository;
import com.google.gson.Gson;

@Service
public class EtsyServices {
	@Autowired
	EtsyRepository etsyRepository;
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${alpidi.app.etsy_token_url}")
	private String etsy_token_url;
	@Value("${alpidi.app.etsy_parent_url}")
	private String esty_parent_url;	
	@Value("${alpidi.app.clientid}")
	private String client_id;
	
	public Etsy refreshtoken(Etsy etsyData)  {
		try {
			HttpHeaders refreshtokenheaders = new HttpHeaders();
			refreshtokenheaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			refreshtokenheaders.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			MultiValueMap<String, String> reqMap = new LinkedMultiValueMap<>();
			reqMap.add("grant_type", "refresh_token");
			reqMap.add("client_id", client_id);
			reqMap.add("refresh_token", etsyData.getRefreshtoken());
			
			HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(reqMap, refreshtokenheaders);
			
			ResponseEntity<String> refresh_response = restTemplate.exchange(etsy_token_url, HttpMethod.POST, formEntity, String.class);
			
			String refresh_responseBody = refresh_response.getBody();
			Gson gson = new Gson();
			var token = gson.fromJson(refresh_responseBody, EtsyTokenDetails.class);
			
			etsyData.setAccesstoken(token.getAccess_token());
			etsyData.setRefreshtoken(token.getRefresh_token());
			
			etsyRepository.save(etsyData);
			
			return etsyData;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public String getEtsyUser(Etsy objEtsy)
	{
		try {
			String url = esty_parent_url+"users/"+ objEtsy.getEtsyuserid();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("charset", "utf-8");
			headers.add("x-api-key", client_id);
			headers.add("Authorization", "Bearer " + refreshtoken(objEtsy));
			
			try {
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
				String responseBody = response.getBody();
				return responseBody;
			} catch (HttpClientErrorException ex) {			
				return null;
			}
		}
		catch(Exception ex) {
			return null;
		}
	}
	
}
