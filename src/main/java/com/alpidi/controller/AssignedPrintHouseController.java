package com.alpidi.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.alpidi.model.AssignOrdersPrintHouse;
import com.alpidi.model.ConfigureShipment;
import com.alpidi.model.CustomizedOrders;
import com.alpidi.model.CutomizedOrderTransaction;
import com.alpidi.model.ListingResult;
import com.alpidi.model.OrderDetails;
import com.alpidi.model.OrderTransaction;
import com.alpidi.model.Response;
import com.alpidi.model.ShipingProfileDetails;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.payload.response.UploadFileResponse;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.AssignedPrintHouseRepository;
import com.alpidi.repository.ConfigureShipmentRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.OrderCutomizationRepository;
import com.alpidi.repository.ShipingProfilesRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.AssignPrinthouseServices;
import com.alpidi.security.sevices.OrderServices;
import com.alpidi.security.sevices.ShoplistingService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AssignedPrintHouseController {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	AssignedPrintHouseRepository printhouseRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
    private AdditionalSettingsRepository additionalSettingsRepository;
	@Autowired
	ConfigureShipmentRepository configureShipmentRepository;
	@Autowired
	ShipingProfilesRepository shipingProfilesRepository;
	@Autowired
	OrderCutomizationRepository orderCutomizationRepository;
	
	@Autowired
	OrderServices orderServices;
	@Autowired
	AssignPrinthouseServices assignPrinthouseServices;
	@Autowired
    private ShoplistingService shoplistingService;
	
	@PostMapping("/assignordertoprinthouse")
	public ResponseEntity<?> AssignOrderToPrinthouse(@RequestBody AssignOrdersPrintHouse assignOrdersPrintHouse) {
		try
		{
			String orderid = assignOrdersPrintHouse.getOrderid();
			String Shopid = orderServices.getShopidFromOrderid(orderid);
			assignOrdersPrintHouse.setShopid(Shopid);
			
			if(orderCutomizationRepository.existsByOrderid(orderid))
			{
				Optional<CustomizedOrders> objCustomizedOrder = orderCutomizationRepository.findByOrderidAndTransactionListingidContaining(orderid,assignOrdersPrintHouse.getlistingid());
				
				for(int i=0;i<objCustomizedOrder.get().gettransaction().size();i++)
				{
					if(objCustomizedOrder.get().gettransaction().get(i).getListing_id().equals(assignOrdersPrintHouse.getlistingid()))
					{
						objCustomizedOrder.get().gettransaction().get(i).setprinthouseid(assignOrdersPrintHouse.getPrintHouseUserid());
					}
				}
				orderCutomizationRepository.save(objCustomizedOrder.get());
			}
			
			return ResponseEntity.ok(new MessageResponse("Order Assign to printhouse successfully."));
		}
		catch(Exception ex)
		{
			
			System.out.println("AssignOrderToPrinthouse : "+ex.getMessage());
			return ResponseEntity.badRequest().body(new MessageResponse("Order Assign to printhouse failed!"));
		}
	}
}
