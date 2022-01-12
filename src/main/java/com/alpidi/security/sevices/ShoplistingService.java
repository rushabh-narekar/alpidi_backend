package com.alpidi.security.sevices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.groups.Default;

import org.apache.tomcat.util.digester.SetPropertiesRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.Etsy;
import com.alpidi.model.Listing;
import com.alpidi.model.ListingInventory;
import com.alpidi.model.ListingProperties;
import com.alpidi.model.ListingResult;
import com.alpidi.model.ListingShopImg;
import com.alpidi.model.ProductionPartner;
import com.alpidi.model.PropertyResult;
import com.alpidi.model.ShipingProfiles;
import com.alpidi.model.ShopImage;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
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
import com.alpidi.repository.ShipingProfilesRepository;

@Service
public class ShoplistingService {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	EtsyRepository etsyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ListingPropertiesRepository listingPropertiesRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	OrdersRepository orderRepository;
	@Autowired
	AdditionalSettingsRepository additionalSettingsRepository;
	@Autowired
	ListingInventoryRepository listingInventoryRepository;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private EtsyServices etsyServices;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ShipingProfilesRepository shipingProfilesRepository;
	@Autowired
	private ShoplistingService shoplistingService;
	@Autowired
	ProductionPartnerRepository productionPartnerRepository;
	@Autowired
	EtsySync etsySync;
	
	@Value("${alpidi.app.etsy_token_url}")
	private String etsy_token_url;
	@Value("${alpidi.app.etsy_parent_url}")
	private String esty_parent_url;	
	@Value("${alpidi.app.clientid}")
	private String client_id;
	
	//API related functions
	public Listing getListingsByListingIds(String listing_ids) {
		try {
			String url = esty_parent_url + "listings/batch?listing_ids="+listing_ids+"&includes=Images,Shop,User";	
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("charset", "utf-8");
			headers.add("x-api-key", client_id);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			ResponseEntity<Listing> response = restTemplate.exchange(url, HttpMethod.GET, entity, Listing.class);
			
			return response.getBody();
			
		}
		catch(Exception ex) {
			System.out.println("getListingsByListingIds : " + ex.getMessage());
			return null;
		}
	}
	
	
	public Listing getListingsByShop(String shopid,Etsy etsy,int limit, int offset,String state) { 
		try {
			String url = esty_parent_url + "shops/"+shopid+"/listings?limit="+limit+"&offset="+offset+"&state="+state;	
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("charset", "utf-8");
			headers.add("x-api-key", client_id);
			headers.add("Authorization", "Bearer " + etsy.getAccesstoken());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			ResponseEntity<Listing> response = restTemplate.exchange(url, HttpMethod.GET, entity, Listing.class);
			
			return response.getBody();
		}
		catch(Exception ex) {
			System.out.println("getListingsByShop : " + ex.getMessage());
			return null;
		}
	}
	
	
	
	public Listing GetListing(String shopid,Etsy etsy, int offset) {
		try {
			String url = esty_parent_url + "shops/"+shopid+"/listings?limit=25&offset="+offset;	
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("charset", "utf-8");
			headers.add("x-api-key", client_id);
			headers.add("Authorization", "Bearer " + etsy.getAccesstoken());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			ResponseEntity<Listing> response = restTemplate.exchange(url, HttpMethod.GET, entity, Listing.class);
			
			return response.getBody();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	public ProductionPartner getProductionPartners(Shops shop)  {
		
		Optional<Etsy> etsyData = etsyRepository.findByEtsyuserid(shop.getUser_id());

		Etsy etsy=etsyServices.refreshtoken(etsyData.get());
		
		if(etsy!=null)
		{
			String url = esty_parent_url + "shops/"+shop.getShop_id()+"/production-partners";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("charset", "utf-8");
			headers.add("x-api-key", client_id);	
			headers.add("Authorization", "Bearer " + etsy.getAccesstoken());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			ResponseEntity<ProductionPartner> result = restTemplate.exchange(url, HttpMethod.GET, entity, ProductionPartner.class);
			ProductionPartner productionPartner = result.getBody();
	
			return productionPartner;
		}
		else
		{
			return null;
		}
	}
	public List<Shops> getShops(String etsyuserid)  {
		String url = esty_parent_url + "users/"+etsyuserid+"/shops";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
		headers.add("charset", "utf-8");
		headers.add("x-api-key", client_id);	
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<Shops> result = restTemplate.exchange(url, HttpMethod.GET, entity, Shops.class);
		List<Shops> shops = new ArrayList<Shops>();
		shops.add(result.getBody());
		return shops;
	}
	public ShipingProfiles getShippingProfiles(String shopid) 
	{	
		try
		{
			Optional<Shops> objShops = shopRepository.findByshopid(shopid);
			Optional<Etsy> etsyData = etsyRepository.findByEtsyuserid(objShops.get().getUser_id());
			Etsy etsy=etsyServices.refreshtoken(etsyData.get());
			if(etsy!=null)
			{
				String url = esty_parent_url + "shops/"+shopid+"/shipping-profiles";		
				HttpHeaders headers = new HttpHeaders();
				headers.add("charset", "utf-8");
				headers.add("x-api-key", client_id);
				headers.add("Authorization", "Bearer " + etsy.getAccesstoken());
				HttpEntity<String> entity = new HttpEntity<String>(headers);
					
				ResponseEntity<ShipingProfiles> response = restTemplate.exchange(url, HttpMethod.GET, entity, ShipingProfiles.class);
				storeShipingProfiles(response.getBody());
				return response.getBody();		 
			}
			else
			{
				
				return null;
			}
		}
		catch(Exception ex)
		{
			System.out.println("getShippingProfiles : "+ex.getMessage());
			return null;
		}
	}
	
	public Shops getShopById(String shopid)  {
		String url = esty_parent_url + "shops/" + shopid;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
		headers.add("charset", "utf-8");
		headers.add("x-api-key", client_id);	
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<Shops> result = restTemplate.exchange(url, HttpMethod.GET, entity, Shops.class);
		Shops shops = result.getBody();

		return shops;
	}
	public ListingShopImg getListingImages(String shopid,String listing_id)  {
		String imgurl = esty_parent_url+"shops/"+shopid+"/listings/"+listing_id+"/images" ;
		
		HttpHeaders imageheaders = new HttpHeaders();
		imageheaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		imageheaders.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
		imageheaders.add("charset", "utf-8");
		imageheaders.add("x-api-key", client_id);
		
		HttpEntity<String> img_entity = new HttpEntity<String>(imageheaders);							
		ResponseEntity<ListingShopImg> img_response = restTemplate.exchange(imgurl, HttpMethod.GET, img_entity, ListingShopImg.class);
		
		return img_response.getBody();
	}
	public PropertyResult getListingProperty(String shopid,String listingid,String access_token)
	{
		String getpropertyurl= esty_parent_url + "shops/"+shopid+"/listings/"+listingid+"/properties";		
		HttpHeaders property_headers = new HttpHeaders();
		property_headers.add("charset", "utf-8");
		property_headers.add("x-api-key", client_id);
		property_headers.add("Authorization", "Bearer " + access_token);
		HttpEntity<String> Property_entity = new HttpEntity<String>(property_headers);
		
		ResponseEntity<PropertyResult> propertyresponse = restTemplate.exchange(getpropertyurl, HttpMethod.GET, Property_entity, PropertyResult.class);
		
		return propertyresponse.getBody();
	}
	public String getShopSections(String shopid)
	{
		String url = esty_parent_url + "shops/"+shopid+"/sections";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
		headers.add("charset", "utf-8");
		headers.add("x-api-key", client_id);	

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
								
		return response.getBody();
	}
	public Optional<ListingResult> getListingAPI(String listingid)
	{
		String url = esty_parent_url + "listings/"+listingid+"&includes=Images,Shop,User";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
        headers.add("charset", "utf-8");
        headers.add("x-api-key", client_id);    

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            ResponseEntity<ListingResult> response = restTemplate.exchange(url, HttpMethod.GET, entity, ListingResult.class);            
            Optional<ListingResult> listingresult = Optional.ofNullable(response.getBody());
            return listingresult;
        }
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        	return null;
        }
	}
	
	public ListingInventory getListingInventory(String listingid)
	{		
		try
		{
			Optional<ListingResult> objListing = listingRepository.findByListingid(listingid);
			Optional<Etsy> etsyData = etsyRepository.findByEtsyuserid(objListing.get().getUserid());
			Etsy etsy=etsyServices.refreshtoken(etsyData.get());
			
			if(etsy!=null)
			{
				String url = esty_parent_url + "listings/"+listingid+"/inventory";		
				HttpHeaders headers = new HttpHeaders();
				headers.add("charset", "utf-8");
				headers.add("x-api-key", client_id);
				headers.add("Authorization", "Bearer " + etsy.getAccesstoken());
				HttpEntity<String> entity = new HttpEntity<String>(headers);
		
				ResponseEntity<ListingInventory> response = restTemplate.exchange(url, HttpMethod.GET, entity, ListingInventory.class);
				return response.getBody();
			}
			else
			{
				return null;
			}
		}
		catch(Exception ex)
		{
			System.out.println("getListingInventory : "+ex.getMessage());
			return null;
		}
		
	}

	//Mongo repository related functions
	public Boolean saveInventory(ListingInventory inventory)
	{
		try
		{
			if(listingInventoryRepository.existsByListingid(inventory.getListingid())) {
				long result = listingInventoryRepository.deleteListingInventoryByListingid(inventory.getListingid());
			}
			listingInventoryRepository.save(inventory);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println("saveInventory : " + ex.getMessage());
			return false;
		}
	}
	
	public List<Shops> AddUpdateAllShops(Etsy etsy)
	{
		try {					
			List<Shops> shops = getShops(etsy.getEtsyuserid());
			for(int i = 0; i < shops.size() ; i++) {
				if(shops.get(i).getShop_id() != null)
				{
					Optional<Shops> existsshop = shopRepository.findByshopid(shops.get(i).getShop_id());
					if(!existsshop.isEmpty())
					{
						shopRepository.deleteShopsByShopid(shops.get(i).getShop_id());
					}
					shops.get(i).setLoginuserid(etsy.getUserid());
				}	
				shopRepository.saveAll(shops);
				return shops;	
			}
			return null;
		}
		catch(Exception e) {
			return null;
		}
	}	
	public boolean AddUpdateProductionPartners(Etsy etsy)
	{
		try {					
			List<Shops> shops = getShops(etsy.getEtsyuserid());
			for(int i = 0; i < shops.size() ; i++) {
				if(shops.get(i).getShop_id() != null)
				{
					ProductionPartner productionPartner = getProductionPartners(shops.get(i));
					
					for(int p = 0; p<productionPartner.getResult().size(); p++)
					{
						System.out.println(productionPartner.getResult().get(i).getProduction_partner_id());
						String productionpartnerid = productionPartner.getResult().get(i).getProduction_partner_id();
						if(!productionPartnerRepository.existsByProductionpartnerid(productionpartnerid))
						{
							productionPartnerRepository.deleteProductionPartnerByProductionpartnerid(productionpartnerid);
						}
						
					}
					productionPartnerRepository.saveAll(productionPartner.getResult());
				}
				return true;	
			}
			return false;
		}
		catch(Exception e) {
			return false;
		}
	}	
	public Boolean updateListingsbyShop(String ConnectedId) {		
		try {
			List<Shops> shops = shopRepository.findByUserid(ConnectedId);
			Optional<Etsy> etsyData = etsyRepository.findByEtsyuserid(ConnectedId);
    		for(int i=0;i < shops.size(); i++)
			{    			
    			storeListing(shops.get(i).getShop_id(), etsyData.get());
    			etsySync.asyncListingImagesMethod(shops.get(i).getShop_id(),true);
			}	    		
	    	return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	public Boolean updateshopslisting(User userdata,Boolean isupdateinventory) {		
		try {
			
			System.out.println("updateshopslisting method called");
			List<Etsy> etsyData = etsyRepository.findByUserid(userdata.getId());
			System.out.println(etsyData.size());
	    	for(int e=0; e < etsyData.size(); e++)
	    	{
	    		List<Shops> shopData = AddUpdateAllShops(etsyData.get(e));
	    		
	    		for(int i=0;i < shopData.size(); i++)
				{
	    			System.out.println("Shop ID : " + shopData.get(i).getShop_id());
	    			storeListing(shopData.get(i).getShop_id(), etsyData.get(e));
	    			etsySync.asyncListingImagesMethod(shopData.get(i).getShop_id(),isupdateinventory);
	    			
				}				
	    	}
	    	return true;
		}
		catch(Exception e) {
			System.out.println("updateshopslisting : "+ e.getMessage());
			return false;
		}
	}
	public Boolean storeListing(String shopid,Etsy objEtsy)
	{
		try
		{
			int process_count=50;
			int offset=0;
			int totalrecord=25;
			int i=0;
			int k=0;
			String[] arrState = {"active", "inactive", "sold_out","draft","removed","expired","edit"};  
			objEtsy = etsyServices.refreshtoken(objEtsy);
			
			ArrayList<Integer> params = new ArrayList<Integer>();
			params.add(0);
			
			if(objEtsy != null)
			{
				do{    
					offset = process_count*i;
					Listing objListing = getListingsByShop(shopid, objEtsy,process_count,offset,arrState[k]);		
					totalrecord=objListing.getCount();
					
					if(objListing.getCount()!=0)
					{
						for(int j=0; j < objListing.getResults().size(); j++) {					
				    		  AddUpdateListingValue(objListing.getResults().get(j), shopid, params, objEtsy.getUserid());
						}
					}
					
					i++;
					if(totalrecord <= (process_count * i) && k < arrState.length - 1) //and also check state condition
					{
						process_count=50;
						offset=0;
						totalrecord=25;
						i=0;					
						k++;
					}
				}while (totalrecord > process_count * i); 
			}
			return true;
		}
		catch(Exception ex)
		{
			System.out.println("storeListing : " + ex.getMessage());
			return false;
		}
	}
	
	
	public String getListingIds(String shopid,String etsyuserid)
	{
		List<ListingResult> listResult = listingRepository.findByShopidAndUserid(shopid, etsyuserid);
		
		StringBuilder str = new StringBuilder("");
		for(int i=0 ; i<listResult.size(); i++)
		{
			str.append(listResult.get(i).getListingid()).append(",");
		}
		String commaseparatedlist = str.toString();
		
		return commaseparatedlist;
	}
	
	public Boolean checkIsVectorFileUploaded(String listingid,String shopid)
	{
		try
		{
			Boolean isuploaded = listingPropertiesRepository.existsByListingidAndShopid(listingid,shopid);	
			
			if(isuploaded) {
				Boolean ismatch = false;
				
				//fetch from api
				ListingShopImg imgs = shoplistingService.getListingImages(shopid,listingid);	
				String f_img = imgs.getResults().get(0).getListing_image_id();
			
				//fetch and compare from DB
				Optional<ListingResult> listingdetails =  listingRepository.findByListingidAndShopid(listingid, shopid);
				if(!listingdetails.isEmpty())
				{
					List<ShopImage> images = listingdetails.get().getImages();
					if(images != null) {
						if(!images.isEmpty()) {
							//check id is exists or not
							for(int i=0; i < images.size(); i++)
							{
								if(images.get(i).getListing_image_id().equals(f_img))
								{
									ismatch = true;
									break;
								}
							}
							if(!ismatch) {
								isuploaded = false;
							}
						}
					}
				}	
			}
			return isuploaded;
		}
		catch(Exception ex)
		{
			System.out.println("isvecor file : " + ex.getMessage());
			return false;
		}
		
	}
	public int chkPriority(String listingid,String shopid,Boolean isvectoruploaded)
	{
		try
		{
			int priority = 0;
			if(!isvectoruploaded) {
				if(listingPropertiesRepository.existsByListingidAndShopid(listingid,shopid) || orderRepository.existsByTransactionsListingid(listingid)) {
					priority=1;
				}
			}
			return priority;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return 0;
		}
		
	}
	
	public List<UploadFileResponse> getVectorFiles(String listingid,String shopid)
	{
		try
		{
			Optional<ListingProperties> vectorfileData = listingPropertiesRepository.findBylistingid(listingid);
			if(vectorfileData.isEmpty()==false)
			{
				ListingProperties vectorfile = vectorfileData.get();
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
			else
			{
				return new ArrayList<>();
			}
		}
		catch(Exception ex)
		{
			System.out.println("getVectorFiles : "+ex.getMessage());
			return new ArrayList<>();
		}
		
		
	}
	public Optional<ListingResult> getListingDetails(String listingid)
	{
		try
		{
			Optional<ListingResult> listingData = listingRepository.findByListingid(listingid);
			if(!listingData.isEmpty())
			{
				Optional<Etsy> etsyData = etsyRepository.findByEtsyuserid(listingData.get().getUserid());
			       
		        if(!etsyData.isEmpty())
		        {
		        	Etsy etsy=etsyServices.refreshtoken(etsyData.get());
			        if(etsy!=null)
			        {
			        	PropertyResult propertyresult = shoplistingService.getListingProperty(listingData.get().getShopid(), listingid, etsy.getAccesstoken());
			           
			            listingData.get().setproperty(propertyresult.getResults());
			            
			            Boolean isvectoruploaded = shoplistingService.checkIsVectorFileUploaded(listingid, listingData.get().getShopid());
			    	    if(isvectoruploaded==true)
			    	    {
			    	      listingData.get().setisvectorfileupload(true);
			    	      List<UploadFileResponse> vectorfiles = shoplistingService.getVectorFiles(listingid, listingData.get().getShopid());
			    	      listingData.get().setvectorfiles(vectorfiles);
			    	    }
			        }
		        }
			}
	        
	        return listingData;
		}
		catch(Exception ex)
		{
			System.out.println("getListingDetails : " + ex.getMessage());
			return null;
		}
	}
	public Boolean updateVectorFiles(String listingid,String shopid,ListingProperties vectorfile)
	{
		Optional<ListingResult> listing = AddUpdateListing(listingid, shopid);
		if(listing != null) {
			vectorfile.setListingImages(listing.get().getImages().get(0).getListing_image_id());
		}
		listingPropertiesRepository.save(vectorfile);
		return true;
	}
		
	public Optional<ListingResult> AddUpdateListing(String listingid,String shopid) {
		try {
			ArrayList<Integer> params = new ArrayList<Integer>();
			params.add(2);
			params.add(3);
			Optional<ListingResult> listing = getListingAPI(listingid);	
			
			AddUpdateListingValue(listing.get(), shopid, params, null);
			return listing;
		}
		catch(Exception e)
		{
			System.out.println("AddUpdateListing : " + e.getMessage());
			return null;
		}
	}
	public boolean AddUpdateListingValue(ListingResult listing, String shopid, ArrayList<Integer> setProperties, String loginUserId) { //setProperties = 0 all, 1 images, 2, sku number, 3 = default print house
		try {
			Optional<ListingResult> old_listing = listingRepository.findByListingidAndShopid(listing.getListingid(),shopid);			
			Optional<AdditionalSettings> objAdditionalSettings= additionalSettingsRepository.findByuserid(loginUserId);
			
			String defaultPrinthouseID = objAdditionalSettings.isEmpty() ? loginUserId : objAdditionalSettings.get().getPrintHouseUserid();
			System.out.println("defaultPrinthouseID : " + defaultPrinthouseID);
			listing.setDefaultprinthouse(defaultPrinthouseID);
			
			if(!old_listing.isEmpty()) {
				listing.setImages((setProperties.contains(0) || setProperties.contains(1)) ? old_listing.get().getImages() : listing.getImages());
				listing.setSku_number((setProperties.contains(0) || setProperties.contains(2)) ? old_listing.get().getSku_number() : listing.getSku_number());
				
				listing.setDefaultprinthouse((setProperties.contains(0) || setProperties.contains(3)) ? defaultPrinthouseID : listing.getDefaultprinthouse());				
				
				//listingRepository.deleteListingByListingidAndShopid(listing.getListingid(), shopid);
				listing.setId(old_listing.get().getId());
			}
			
			listingRepository.save(listing);
			
			return true;
		}
		catch(Exception e)
		{
			System.out.println("AddUpdateListingValue : " + e.getMessage());
			return false;
		}
	}
	public boolean AddUpdateListingValues(List<ListingResult> listings, String[] ids) {
		try {			
			//long result = listingRepository.deleteByListingidIn(Arrays.asList(ids));
			//System.out.println("Delete listings : "+result);
			listingRepository.saveAll(listings);
			//System.out.println("save successfully");			
			return true;
		}
		catch(Exception e)
		{
			System.out.println("AddUpdateListingValuesss : " + e.getMessage());
			return false;
		}
	}
	public Boolean storeShipingProfiles(ShipingProfiles shipingProfiles)  {
		try
		{
			for(int sp=0;sp<shipingProfiles.getResults().size();sp++)
			{
				String shipingProfileId= shipingProfiles.getResults().get(sp).getShipping_profile_id();
				Boolean checkStatus = shipingProfilesRepository.existsByshippingprofileid(shipingProfileId);
				if(checkStatus==true)
				{
					shipingProfilesRepository.deleteShipingProfilesByshippingprofileid(shipingProfileId);
				}
				shipingProfilesRepository.save(shipingProfiles.getResults().get(sp));
			}
			return true;
		}
		catch(Exception ex)
		{
			System.out.println("storeShipingProfiles : "+ex.getMessage());
			return null;
		}
	}
}
