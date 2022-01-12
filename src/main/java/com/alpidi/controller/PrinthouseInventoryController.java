package com.alpidi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
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

import com.alpidi.model.Activities;
import com.alpidi.model.Colors;
import com.alpidi.model.Inventory;
import com.alpidi.model.Response;
import com.alpidi.model.Sizes;
import com.alpidi.model.User;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.ActivitiesRepository;
import com.alpidi.repository.ColorsRepository;
import com.alpidi.repository.InventoryRepository;
import com.alpidi.repository.SizesRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class PrinthouseInventoryController {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository userRepository;
	@Autowired
    InventoryRepository inventoryRepository;
	@Autowired
	ActivitiesRepository activitiesRepository;
	@Autowired
	SizesRepository sizesRepository;
	@Autowired
	ColorsRepository colorsRepository;
	
    @PostMapping("/saveinventory")
    public ResponseEntity<?> saveInventory(@RequestBody Inventory inventory) {
        try
        {        	
        	if(inventoryRepository.existsByLoginuseridAndSizeAndColor(inventory.getloginuserid(),inventory.getsize(),inventory.getcolor()))
        	{
        		inventoryRepository.deleteByLoginuseridAndSizeAndColor(inventory.getloginuserid(),inventory.getsize(),inventory.getcolor());
        	}
            inventoryRepository.save(inventory);
            
           
            Optional<Activities> objActivities = activitiesRepository.findByLoginuseridAndInventoryid(inventory.getloginuserid(),inventory.getid());
            if(!objActivities.isEmpty())
            {
            	objActivities.get().setorderStatus(-1);
            }
            activitiesRepository.save(objActivities.get());
                     
            
            return ResponseEntity.ok(new MessageResponse("Inventory saved."));
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Inventory saved failed!"));
        }
    }
    
    @PostMapping("/updateinventory")
    public ResponseEntity<?> updateInventory(@RequestBody Inventory inventory) {
        try
        {
        	Optional<Inventory> objInventory = inventoryRepository.findByloginuseridAndSizeAndColor(inventory.getloginuserid(),inventory.getsize(),inventory.getcolor());
        	if(!objInventory.isEmpty())
        	{
        		objInventory.get().setquntity(inventory.getquntity());
        	}
            inventoryRepository.save(objInventory.get());
            return ResponseEntity.ok(new MessageResponse("Inventory saved."));
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Inventory saved failed!"));
        }
    }
    
   
    
    @RequestMapping("/getallinventory")
    public ResponseEntity<?> getAllInventory(@RequestParam("loginuserid") String loginuserid) {
    	
			List<Inventory> listInventory= inventoryRepository.findByLoginuserid(loginuserid);
			int newQty=0;
			for(int i=0;i<listInventory.size();i++)
			{
				List<Activities> objQuantity = activitiesRepository.findByLoginuseridAndInventoryidAndOrderStatus(listInventory.get(i).getloginuserid(),listInventory.get(i).getid(),1);
				if(!objQuantity.isEmpty())
				{
					newQty=0;
					for(int a=0;a<objQuantity.size();a++)
					{
						newQty += objQuantity.get(a).getsendQuantity().intValue();
					}
					listInventory.get(i).setincomingOrderQuatity(newQty);
				}
				objQuantity = activitiesRepository.findByLoginuseridAndInventoryidAndOrderStatus(listInventory.get(i).getloginuserid(),listInventory.get(i).getid(),2);
				if(!objQuantity.isEmpty())
				{
					newQty=0;
					for(int a=0;a<objQuantity.size();a++)
					{
						newQty += objQuantity.get(a).getsendQuantity().intValue();
					}
					listInventory.get(i).setprocessingQuantity(newQty);
				}
				//System.out.println(listInventory.get(i).getid());
				objQuantity = activitiesRepository.findByLoginuseridAndInventoryidAndOrderStatus(listInventory.get(i).getloginuserid(),listInventory.get(i).getid(),3);
				if(!objQuantity.isEmpty())
				{
					System.out.println(listInventory.get(i).getprocessingQuantity());
//					newQty=listInventory.get(i).getprocessingQuantity().intValue();
//					for(int a=0;a<objQuantity.size();a++)
//					{
//						newQty += objQuantity.get(a).getsendQuantity().intValue();
//					}
//					listInventory.get(i).settotalSellingQuantity(newQty);
				}
			}
			return ResponseEntity.ok(listInventory);
    }
    
    @RequestMapping("/getalertqtyandcriticalqty")
    public HashMap<String, Object> getAlertqtyAndAriticalqty(@RequestParam("authtoken") String authtoken) {
    	if(jwtUtils.validateJwtToken(authtoken))
		{
    		var email = jwtUtils.getUserNameFromJwtToken(authtoken);
		    Optional<User> userdata= userRepository.findByEmail(email);
		    String loginuserid = userdata.get().getId();
	    	List<Inventory> listInventory= inventoryRepository.findByLoginuserid(loginuserid);
	    	
	    	HashMap<String, Object> map = new HashMap<>();
	    	List<Object> alertentities = new ArrayList<Object>();
	    	List<Object> criticalentities = new ArrayList<Object>();
	    	for(int i=0;i<listInventory.size();i++)
			{    		
	    		int alertqty = listInventory.get(i).getalertqty().intValue();
	    		int criticalqty = listInventory.get(i).getcriticalqty().intValue();
	    		int quantity = listInventory.get(i).getquntity().intValue();
	    		int incomingqty = 0;
	    		
	    		List<Activities> listActivities = activitiesRepository.findByLoginuseridAndInventoryidAndOrderStatus(loginuserid,listInventory.get(i).getid(),1);
	    		for(int a=0;a<listActivities.size();a++)
	    		{
	        		incomingqty = incomingqty + listActivities.get(a).getsendQuantity().intValue();
	    		}
	    		int remainqty = quantity - incomingqty;
	    		
	    		if(incomingqty != 0 || quantity != 0 || listActivities.size() > 0)
	    		{
	    			JSONObject entity = new JSONObject();
	    			entity.put("type", "-1");
	    			
	    			if((alertqty >= remainqty) && (remainqty > criticalqty))
	            	{
	    	            entity.put("type", "0");
	        		}
	        		else if(criticalqty >= remainqty)
	            	{
	        			entity.put("type", "1");
	        		}
	    			
	    			if(!entity.get("type").equals("-1")) {
		    			entity.put("size", listInventory.get(i).getsize());
			            entity.put("color", listInventory.get(i).getcolor());
			            entity.put("quantity", listInventory.get(i).getquntity());	
			            if(entity.get("type").equals("0"))
			            {
			            	alertentities.add(entity.toMap());
			            }
			            else  if(entity.get("type").equals("1")){
			            	criticalentities.add(entity.toMap());
			            }
			            
	    			}
	    		}
			}
	    	map.put("alert", alertentities.toArray());
	    	map.put("critical", criticalentities.toArray());
	    	return map;
		}
    	else
    	{
    		return null;
    	}
    }
    
//    @GetMapping("/deleteinventory")
//	public ResponseEntity<?> deleteInventory(@RequestParam("id") String id) {
//		try
//		{
//			Optional<Inventory> objInventory= inventoryRepository.findById(id);
//			if(!objInventory.isEmpty())
//			{
//				objInventory.get().setisdeleted(true);
//				inventoryRepository.save(objInventory.get());
//			}
//			return ResponseEntity.ok("Inventory deleted Successfully.");
//		}
//		catch(Exception ex)
//		{
//			return ResponseEntity.badRequest().body(new MessageResponse("Inventory delete failed!"));
//		}
//	}
    
    @RequestMapping("/savesizes")
    public ResponseEntity<?> saveSizes(@RequestParam("sizes") String[] sizes) {
        try
        {
        	for(int i=0;i<sizes.length;i++)
        	{
        		Sizes objSize = new Sizes();
        		if(sizesRepository.existsBySize(sizes[i]))
        		{
        			Optional<Sizes> oldSize = sizesRepository.findBySize(sizes[i]);
        			
        			objSize.setid(oldSize.get().getid());
        		}
        		objSize.setsize(sizes[i]);
        		sizesRepository.save(objSize);
        	}
        	
            return ResponseEntity.ok(new MessageResponse("Sizes saved."));
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Sizes saved failed!"));
        }
    }
	
	@RequestMapping("/savecolors")
    public ResponseEntity<?> saveColors(@RequestParam("colors") String[] colors) {
        try
        {
        	for(int i=0;i<colors.length;i++)
        	{
        		Colors objColors = new Colors();
        		
        		if(colorsRepository.existsByColor(colors[i]))
        		{
        			//Optional<Colors> oldColor = colorsRepository.findByColor(colors[i]);
        			colorsRepository.deleteColorsByColor(colors[i]);
        			//objColors.setid(oldColor.get().getid());
        		}
        		objColors.setcolor(colors[i]);
        		colorsRepository.save(objColors);
        	}
        	
            return ResponseEntity.ok(new MessageResponse("Colors saved."));
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Colors saved failed!"));
        }
    }
	
	@GetMapping("/getsizes")
    public ResponseEntity<?> getSizes() {
        try
        {
        	List<Sizes> listSizes= sizesRepository.findAll();
            return ResponseEntity.ok(listSizes);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Colors saved failed!"));
        }
    }
	
	@GetMapping("/getcolors")
    public ResponseEntity<?> getColors() {
        try
        {
        	List<Colors> listColors= colorsRepository.findAll();
            return ResponseEntity.ok(listColors);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Colors saved failed!"));
        }
    }
}
