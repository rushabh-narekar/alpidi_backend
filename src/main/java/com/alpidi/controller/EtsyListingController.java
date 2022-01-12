package com.alpidi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.Etsy;
import com.alpidi.model.Listing;
import com.alpidi.model.ListingInventory;
import com.alpidi.model.ListingProperties;
import com.alpidi.model.ListingResult;
import com.alpidi.model.Response;
import com.alpidi.model.ShopImage;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
import com.alpidi.payload.request.DeleteVectoFileRequest;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.payload.response.UploadFileResponse;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.ListingInventoryRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.OrdersRepository;
import com.alpidi.repository.ProductionPartnerRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.repository.ListingPropertiesRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.EtsySync;
import com.alpidi.security.sevices.FileStorageService;
import com.alpidi.security.sevices.OrderServices;
import com.alpidi.security.sevices.ShoplistingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Authenticator.Result;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Configuration
@EnableScheduling
public class EtsyListingController {

    private static final Logger logger = LoggerFactory.getLogger(EtsyListingController.class);
    
    @Autowired
	EtsyRepository etsyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	ListingInventoryRepository listingInventoryRepository;
    @Autowired
    ListingPropertiesRepository listingPropertiesRepository;
    @Autowired
    ProductionPartnerRepository productionPartnerRepository;
    @Autowired
    AdditionalSettingsRepository additionalSettingRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ShoplistingService shoplistingService;
    @Autowired
    OrdersRepository orderRepository;
    @Autowired
	RestTemplate restTemplate;	
    @Autowired
    EtsySync etsyAsync;
	
	@Scheduled(cron = "0 0 3 * * *")
	//@Scheduled(cron = "0 0/10 * * * ?")
	@RequestMapping(value = "/etsy/runlistingjob", method = RequestMethod.GET, produces = "application/json")
	public void scheduleFixedRateTask() {
		System.out.println("Listing Cron job run at : " + new Date());
		List<User> users = userRepository.findByRole("shopowner");
		for(int i=0; i < users.size(); i++)
    	{
			shoplistingService.updateshopslisting(users.get(i),true);
    	}
		System.out.println("Listing Cron job Done.");
	}	
	
	//@Scheduled(cron = "0 0 3 * * *")
	public void scheduleListingInventory() {
		try
		{
			etsyAsync.asyncInventoryMethod();
		}
		catch(Exception ex)
		{
			System.out.println("scheduleListingInventory : "+ex.getMessage());
		}
	}	
	
	@RequestMapping(value = "/etsy/getalllisting", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllListing(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try 
			{	
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> user= userRepository.findByEmail(email);
				System.out.println(user.get().getEmail());
				Boolean result = shoplistingService.updateshopslisting(user.get(),false);
				if(result)
				{
					return ResponseEntity.ok(new MessageResponse("Listing updated."));
				}
				else
				{
					return ResponseEntity.badRequest().body(new MessageResponse("Listing update failed!"));
				}
			} catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		   	}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/etsy/getlisting", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getListing(
			  @RequestParam("authtoken") String authtoken,
			  @RequestParam(required = false) String query,
			  @RequestParam(required = false) String state,
			  @RequestParam(required = false) String sectionid,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "10") int size) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			 var email = jwtUtils.getUserNameFromJwtToken(authtoken);
		     Optional<User> userdata= userRepository.findByEmail(email);
		     String loginuserid = userdata.get().getId();
		     
		     try {
		    	 
		      List<ListingResult> list = new ArrayList<ListingResult>();
		      Pageable paging = PageRequest.of(page, size);		      
		      Page<ListingResult> pageListing;
		      query = query == null ? "" : query;
		      
		      if (state == null && sectionid == null ) {
		    	  pageListing = listingRepository.findAll(paging);
		      }
		      else if(sectionid != "" && state != "") {
		    	  pageListing = listingRepository.findByShopsectionidAndStateAndTitleContainingOrderByPriorityDescLastmodifiedtimestampDesc(sectionid, state, query, paging);
		      }
		      else if(state != "") {
		    	  pageListing = listingRepository.findByStateAndTitleContainingOrderByPriorityDesc(state, query, paging);
		      }
		      else {
		    	  pageListing = listingRepository.findByTitleContainingOrderByPriorityDesc(query, paging);
		      }
		    	  
		      list = pageListing.getContent();
		      
		      for(int i=0;i<list.size();i++)
		      {
		    	  String listingid = list.get(i).getListingid();
		    	  String shopid = list.get(i).getShopid();
		    	  boolean isvectorfileuploaded  = shoplistingService.checkIsVectorFileUploaded(listingid,shopid);			
		    	  list.get(i).setPriority(shoplistingService.chkPriority(listingid, shopid, isvectorfileuploaded));
					
		    	  list.get(i).setisvectorfileupload(isvectorfileuploaded);
		    	  
	    		  List<UploadFileResponse> vectorfiles = shoplistingService.getVectorFiles(listingid, shopid);
	    		  list.get(i).setvectorfiles(vectorfiles);
		      }
		     
		      Map<String, Object> response = new HashMap<>();
		      
		      response.put("results", list);
		      response.put("currentPage", pageListing.getNumber());
		      response.put("count", pageListing.getTotalElements());
		      response.put("totalPages", pageListing.getTotalPages());

		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value = "/etsy/listingImages", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getListingImages(@RequestParam("shopid") String shopid, @RequestParam(defaultValue = "false") Boolean isExecuteInventory) { // set flag that execute inventory api or not 
		Optional<Shops> objShop = shopRepository.findByshopid(shopid);		
		List<ListingResult> listResult = listingRepository.findByShopidAndUserid(shopid, objShop.get().getUser_id());
		int l=0;
		int maxpage = 50;
		do
		{
			maxpage = l + maxpage < listResult.size() ? l + maxpage : listResult.size();
			StringBuilder str = new StringBuilder("");
			for(; l < maxpage ; l++)
			{
				str.append(listResult.get(l).getListingid()).append(",");
			}
			String listingids = str.toString();
			
			Listing listing= shoplistingService.getListingsByListingIds(listingids);
			for(int j = 0 ; j < listing.getResults().size(); j++)
			{
				String listingid = listing.getResults().get(j).getListingid();
				listing.getResults().get(j).setimg75(listing.getResults().get(j).getImages().get(0).getUrl_75x75());
				boolean isvectorfileuploaded  = shoplistingService.checkIsVectorFileUploaded(listingid,shopid);	
				listing.getResults().get(j).setPriority(shoplistingService.chkPriority(listingid, shopid, isvectorfileuploaded));
				listing.getResults().get(j).setisvectorfileupload(isvectorfileuploaded);
				
				 Optional<AdditionalSettings> objAdditionalSettings= additionalSettingRepository.findByuserid(objShop.get().getLoginuserid());
	    		 if(objAdditionalSettings.isEmpty()==false)
	    		 {
	    			 listing.getResults().get(j).setDefaultprinthouse(objAdditionalSettings.get().getPrintHouseUserid());
	    		 }
	    		 else
	    		 {
	    			 listing.getResults().get(j).setDefaultprinthouse(objShop.get().getLoginuserid());
	    		 }
			}
			shoplistingService.AddUpdateListingValues(listing.getResults(), listingids.split(","));
		}while (l < listResult.size());		
		
		if(isExecuteInventory) { /* call async method */ }
		
		return ResponseEntity.ok(new MessageResponse("Shops listing updated."));
		//return lstResult;
	}
	
	@RequestMapping(value = "/etsy/getinventory", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getinventory(@RequestParam("listingid") String listingid) {
		try
		{
			Optional<ListingInventory> objInventory = listingInventoryRepository.findOneByListingid(listingid);
			if(!objInventory.isEmpty())
			{
				return ResponseEntity.ok(objInventory.get());	
			}
			else
			{
				return null;
			}
		}
		catch(Exception ex)
		{
			Response resp = new Response(505, "Internal Server Error!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@RequestMapping(value = "/etsy/getListingInventory", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getListingInventory(@RequestParam("authtoken") String authtoken,@RequestParam("listingid") String listingid) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
				Optional<ListingResult> objListing = listingRepository.findByListingid(listingid);

				ListingInventory listInventory = shoplistingService.getListingInventory(listingid);
				listInventory.setListingid(listingid);
				
				shoplistingService.saveInventory(listInventory);
					
				objListing.get().setSku_number(listInventory.getProducts().get(0).getSku());
				if(listingRepository.existsByListingidAndShopid(listingid, objListing.get().getShopid()))
				{
					listingRepository.deleteListingByListingidAndShopid(listingid, objListing.get().getShopid());
				}
				listingRepository.save(objListing.get());
				
			return ResponseEntity.ok(new MessageResponse("Inventory updated successfully"));		
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/etsy/changelistingprinthouse", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> changeListingPrinthouse(@RequestParam("listingid") String listingid, @RequestParam("printhouseid") String printhouseid) {
		
			try 
			{	
				Optional<ListingResult> objListing = listingRepository.findByListingid(listingid);
				objListing.get().setDefaultprinthouse(printhouseid);
				
				listingRepository.deleteListingByListingidAndShopid(listingid, objListing.get().getShopid());	
				
				listingRepository.save(objListing.get());	
				
				return ResponseEntity.ok(new MessageResponse("Printhouse changed successfully"));
			} catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		   	}
	}
	

	@RequestMapping(value = "/etsy/getlistingbyshop", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllListingbyShop(@RequestParam("authtoken") String authtoken, @RequestParam("ConnectedId") String ConnectedId) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try 
			{	
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> user= userRepository.findByEmail(email);
				shoplistingService.updateListingsbyShop(ConnectedId);
				
				
				return ResponseEntity.ok(new MessageResponse("Shops listing updated."));
			} catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		   	}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/etsy/getstatuscount", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getStatusCount(@RequestParam("shopid") String shopid) {
		
			try 
			{	
				Map<String, Integer> countResult = new HashMap<>();
			       
				List<ListingResult> activecount=listingRepository.findByShopidAndState(shopid,"active");
				countResult.put("active", activecount.size());
				List<ListingResult> draftcount=listingRepository.findByShopidAndState(shopid,"draft");
				countResult.put("draft", draftcount.size());
				List<ListingResult> inactivecount=listingRepository.findByShopidAndState(shopid,"inactive");
				countResult.put("inactive", inactivecount.size());
				List<ListingResult> soldoutcount=listingRepository.findByShopidAndState(shopid,"sold_out");
				countResult.put("soldout", soldoutcount.size());
				List<ListingResult> expiredcount=listingRepository.findByShopidAndState(shopid,"expired");
				countResult.put("expired", expiredcount.size());
					
				return ResponseEntity.ok(countResult);
			} catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		   	}
		
	}

	
	
    @RequestMapping(value = "/etsy/getShopSections", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getShopSections(@RequestParam("shopid") String shopid) {			
			try {
				String result = shoplistingService.getShopSections(shopid);
				return ResponseEntity.ok(result);

			} catch (Exception e) {
				Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("listingid") String listingid,@RequestParam("shopid") String shopid) {
        String fileName = fileStorageService.storeFile(file, listingid, shopid);

        String fileDownoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/auth/downloadFile/")
                .path("/" + shopid + "/" + listingid + "/")
                .path(fileName)
                .toUriString();
        

        return new UploadFileResponse(fileName,fileDownoadUri);
    }

    @PostMapping("/uploadVectorFiles")
    public List<UploadFileResponse> uploadVectorFiles(@RequestParam("files") MultipartFile[] files,@RequestParam("listingid") String listingid,@RequestParam("shopid") String shopid) throws JSONException, JsonProcessingException {
    	
    	List<UploadFileResponse> result = Arrays.asList(files).stream().map(file -> uploadFile(file, listingid, shopid)).collect(Collectors.toList());
    	
    	Optional<ListingResult> objListing = listingRepository.findByListingidAndShopid(listingid,shopid);    	
    	for(int i=0;i<result.size();i++)
    	{
            if(listingPropertiesRepository.existsByListingidAndShopid(listingid,shopid) == true)
            {
            	Optional<ListingProperties> listingproperties = listingPropertiesRepository.findBylistingid(listingid);            	
            	ListingProperties vectorfile = listingproperties.get();
            	ArrayList<String> filename = new ArrayList<String>(vectorfile.getfilename());            	
            	filename.add(result.get(i).getFileName());               
                vectorfile.setfilename(filename);
                vectorfile.setListingImages(objListing.get().getImages().get(0).getListing_image_id());
            	
                shoplistingService.updateVectorFiles(listingid,shopid,vectorfile);
            }
            else
            {
            	
            	ArrayList<String> filename = new ArrayList<String>();            	
            	filename.add(result.get(i).getFileName());            	
            	ListingProperties vertorfiles=new ListingProperties(shopid,listingid,filename,objListing.get().getImages().get(0).getListing_image_id());
                
                shoplistingService.updateVectorFiles(listingid,shopid,vertorfiles);
            }
    	}
    	Optional<ListingProperties> listingproperties = listingPropertiesRepository.findBylistingid(listingid);
    	
    	ListingProperties vectorfile = listingproperties.get();
    	var vetorfilename=vectorfile.getfilename();

    	List<UploadFileResponse> newres = new ArrayList<>();
    	for(int i=0;i<vetorfilename.size();i++)
    	{
    		String fileDownoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/auth/downloadFile/")
                    .path("/" + shopid + "/" + listingid + "/")
                    .path(vetorfilename.get(i))
                    .toUriString();
    		
    		newres.add(new UploadFileResponse(vetorfilename.get(i), fileDownoadUri));
    	}
        
    	return newres;
    }
    
    
    @PostMapping("/deletevectorfile")
    public ResponseEntity<?> deletevectorfile(@Valid @RequestBody DeleteVectoFileRequest deletevector){
    	
    	Optional<ListingProperties> listingproperties = listingPropertiesRepository.findBylistingid(deletevector.getlistingid());
    	try
    	{
	    	 if(listingproperties.get().getfilename().contains(deletevector.getfilename())==true)
	         {
	    		 listingproperties.get().getfilename().remove(deletevector.getfilename());
	         }         
	    	 
	    	 ListingProperties vectorfiles = listingproperties.get();
	    	 vectorfiles.setfilename(listingproperties.get().getfilename());
	    	 
	    	 listingPropertiesRepository.save(vectorfiles);
	    	 
	    	 ListingProperties vectorfile = listingproperties.get();
	     	var vetorfilename=vectorfile.getfilename();

	     	List<UploadFileResponse> newres = new ArrayList<>();
	     	for(int i=0;i<vetorfilename.size();i++)
	     	{
	     		String fileDownoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                     .path("/api/auth/downloadFile/")
	                     .path("/" + listingproperties.get().getShopid() + "/" + deletevector.getlistingid() + "/")
	                     .path(vetorfilename.get(i))
	                     .toUriString();
	     		
	     		newres.add(new UploadFileResponse(vetorfilename.get(i), fileDownoadUri));
	     	}
			 
			 return ResponseEntity.ok(newres);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
    }
    
   
    @GetMapping("/downloadFile/{listingid}/{shopid}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String listingid,@PathVariable String shopid,@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName, listingid, shopid);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
}

