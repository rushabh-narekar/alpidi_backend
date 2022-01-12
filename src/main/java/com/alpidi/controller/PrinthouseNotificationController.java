package com.alpidi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpidi.model.Activities;
import com.alpidi.model.ConfigureShipment;
import com.alpidi.model.CustomizedOrders;
import com.alpidi.model.Inventory;
import com.alpidi.model.Notification;
import com.alpidi.model.OrderDetails;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.ActivitiesRepository;
import com.alpidi.repository.ConfigureShipmentRepository;
import com.alpidi.repository.InventoryRepository;
import com.alpidi.repository.OrderCutomizationRepository;
import com.alpidi.repository.OrdersRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class PrinthouseNotificationController {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrderCutomizationRepository orderCutomizationRepository;
	@Autowired
	OrdersRepository ordersRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	ActivitiesRepository activitiesRepository;
	@Autowired
	ConfigureShipmentRepository configureShipmentRepository;

	
	@GetMapping("/getprinthousenotifications")
    public ResponseEntity<?> getPrinthouseNotifications(@RequestParam("authtoken") String authtoken) {
        try
        {
        	var email = jwtUtils.getUserNameFromJwtToken(authtoken);
			Optional<User> userdata= userRepository.findByEmail(email);
			String loginuserid = userdata.get().getId();
			
			List<Notification> listNotification = new ArrayList<Notification>();
			
			//For new orders notifications
        	List<CustomizedOrders> listOrder = orderCutomizationRepository.findByIsAssignedAndTransactionPrinthouseid(true,loginuserid);
        	for(int o=0;o<listOrder.size();o++)
        	{
        		String shopname = "";
        		Optional<OrderDetails> objOrder= ordersRepository.findByreceiptid(listOrder.get(o).getorderid());
        		if(!objOrder.isEmpty())
        		{
        			Optional<User>  user= userRepository.findById(objOrder.get().getLogin_user_id());
            		if(!user.isEmpty())
            		{
            			shopname=user.get().getFirstname() + " " + user.get().getLastname();
            		}
        		}
        		
        		Optional<Activities> objActivities = activitiesRepository.findByLoginuseridAndOrderidAndOrderStatusOrderByUpdatedDateDesc(loginuserid,listOrder.get(o).getorderid(),1);
        		if(!objActivities.isEmpty())
        		{
        			Notification newNotification = new Notification();
        			
    				newNotification.setmessage("#" + objActivities.get().getorderid() +" comes from " + shopname + " is waiting for approval.");
            		newNotification.setmessageType(3);
            		newNotification.setpriotity(1);
            		newNotification.setparams(listOrder.get(o).getorderid());
            	
            		listNotification.add(newNotification);
        		}
        		objActivities = activitiesRepository.findByLoginuseridAndOrderidAndOrderStatusOrderByUpdatedDateDesc(loginuserid,listOrder.get(o).getorderid(),2);
        		if(!objActivities.isEmpty())
        		{
        			Notification newNotification = new Notification();
        			
        				newNotification.setmessage("#" + objActivities.get().getorderid() +" comes from " + shopname + " goes to production.");
                		newNotification.setmessageType(3);
                		newNotification.setpriotity(1);
                		newNotification.setparams(listOrder.get(o).getorderid());
                		
                		listNotification.add(newNotification);
        		}
        		objActivities = activitiesRepository.findByLoginuseridAndOrderidAndOrderStatusOrderByUpdatedDateDesc(loginuserid,listOrder.get(o).getorderid(),3);
        		if(!objActivities.isEmpty())
        		{ 
	        		if(printDifference(objActivities.get().getupdatedDate(),new Date())<=24)
	        		{
	        			       			
	            		Notification newNotification = new Notification();
	        				
	        			Optional<ConfigureShipment> objConfigireShipment = configureShipmentRepository.findByOrderid(listOrder.get(o).getorderid());
	        			if(!objConfigireShipment.isEmpty())
	        			{
	       					newNotification.setmessage("#" + objActivities.get().getorderid() +" comes from " + shopname + " is completed and assigned to carrier service with tracking number is : "+objConfigireShipment.get().getshipingTransaction().gettracking_number());
	                   		newNotification.setmessageType(3);
	                   		newNotification.setpriotity(1);
	                   		newNotification.setparams(listOrder.get(o).getorderid());
	                   		
	                   		listNotification.add(newNotification);
	       				}
	        		}	
        		}
        	}
        	//for order inventory management
			List<Inventory> listInventory = inventoryRepository.findByLoginuserid(loginuserid);
			
			for(int i=0;i<listInventory.size();i++)
			{
				int totalqty = listInventory.get(i).getquntity().intValue();
				int criticalqty = listInventory.get(i).getcriticalqty().intValue();
				int alertqty = listInventory.get(i).getalertqty().intValue();
				
				String size = listInventory.get(i).getsize();
				String color = listInventory.get(i).getcolor();
				
				if(totalqty <= criticalqty)
				{
					Notification newNotification = new Notification();
					
					if(totalqty>0)
					{
						newNotification.setmessage(size +" with "+color+" quantity needs to upgrade because of its reach to " + totalqty);
						newNotification.setmessageType(1);
						newNotification.setpriotity(0);
						newNotification.setparams(listInventory.get(i).getid());
						
						listNotification.add(newNotification);
					}
				}
				if(totalqty <= alertqty && totalqty > criticalqty)
				{
					Notification newNotification = new Notification();
					
					if(totalqty>0)
					{
						newNotification.setmessage(size+" with "+color+" quantity eeds to upgrade because of its reach to "+totalqty);
						newNotification.setmessageType(1);
						newNotification.setpriotity(1);
						newNotification.setparams(listInventory.get(i).getid());
						
						listNotification.add(newNotification);
					}
				}
			}
            return ResponseEntity.ok(listNotification);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Get printhouse notifications failed!"));
        }
    }
	
	public long printDifference(Date startDate, Date endDate){

        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        
        return elapsedHours;

    }
	
	 public long getDateDifference(Date startDate, Date endDate){

	        long duration = endDate.getTime() - startDate.getTime();
	        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
	        //long diffInSeconds = duration / 1000 % 60;
	        
	        return diffInSeconds;

	    }
}
