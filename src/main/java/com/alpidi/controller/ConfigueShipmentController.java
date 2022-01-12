package com.alpidi.controller;

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
import com.alpidi.model.ConfigureShipment;
import com.alpidi.repository.ConfigureShipmentRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class ConfigueShipmentController {
	@Autowired
	ConfigureShipmentRepository configureShipmentRepository;
	
	@PostMapping("/saveconfigureshipement")
	public ResponseEntity<?> saveConfigureShipement(@RequestBody ConfigureShipment configureShipment) {
		try
		{	
			System.out.println(configureShipment);
			Boolean checkStatus = configureShipmentRepository.existsByLoginuseridAndOrderid(configureShipment.getloginuserid(),configureShipment.getorderid());
			
			if(checkStatus==true) {
				configureShipmentRepository.deleteConfigureShipmentByLoginuseridAndOrderid(configureShipment.getloginuserid(),configureShipment.getorderid());
			}
			configureShipmentRepository.save(configureShipment);		
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Configure Shipment Saved.", null), HttpStatus.OK);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Configure Shipment save failed!", null), HttpStatus.OK);
		}
	}
}
