package com.alpidi.security.sevices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alpidi.model.AdditionalSettings;
import com.alpidi.model.Etsy;
import com.alpidi.model.Listing;
import com.alpidi.model.ListingInventory;
import com.alpidi.model.ListingResult;
import com.alpidi.model.Orders;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
import com.alpidi.repository.AdditionalSettingsRepository;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.ListingInventoryRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;

@Service
public class EtsyAsyncService implements EtsySync {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	AdditionalSettingsRepository additionalSettingsRepository; 
	@Autowired
	ListingInventoryRepository listingInventoryRepository;
	@Autowired
	private ShoplistingService shoplistingService;
	@Autowired
	SettingsSerivces settingService;
	@Autowired
	OrderServices orderServices;
	@Autowired
	EtsyRepository etsyRepository;
	
		@Async
		@Override
		public void asyncListingImagesMethod(String shopid,Boolean isupdateinventory) throws InterruptedException {
		  System.out.println("async Listing Images Method called");	
		  
		  try
		  {
			  Optional<Shops> objShop = shopRepository.findByshopid(shopid);
			  String loginUserId = objShop.get().getLoginuserid();
			  String defaultPrinthouseID = loginUserId;
			  
				List<ListingResult> listResult = listingRepository.findByShopid(shopid);
				
				if(listResult.size()>0)
				{
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
							
							Optional<ListingResult> old_listing = listingRepository.findByListingidAndShopid(listingid,shopid);
							listing.getResults().get(j).setimg75(listing.getResults().get(j).getImages().get(0).getUrl_75x75());
							boolean isvectorfileuploaded  = shoplistingService.checkIsVectorFileUploaded(listingid,shopid);	
							listing.getResults().get(j).setPriority(shoplistingService.chkPriority(listingid, shopid, isvectorfileuploaded));
							listing.getResults().get(j).setisvectorfileupload(isvectorfileuploaded);
							listing.getResults().get(j).setDefaultprinthouse(old_listing.get().getDefaultprinthouse());
							listing.getResults().get(j).setId(old_listing.get().getId());
						}
						shoplistingService.AddUpdateListingValues(listing.getResults(), listingids.split(","));
					}while (l < listResult.size());
				}
				
				asyncStoreOrders(objShop.get().getLoginuserid(),isupdateinventory);
		  }
		  catch(Exception ex)
		  {
			 System.out.println("asyncListingImagesMethod : "+ex.getMessage()); 
		  }
		}
		
		@Async
		@Override
		public void asyncStoreOrders(String userid,Boolean isupdateinventory) throws InterruptedException {
			System.out.println("async Store Orders called.");
				Optional<User> userdata = userRepository.findById(userid);
				List<Etsy> lstetsy = etsyRepository.findByUserid(userdata.get().getId());
				Optional<AdditionalSettings> objSettings= settingService.getAdditionalSettings(userdata.get().getId());
				for(int e = 0; e < lstetsy.size(); e++)	{	
					Etsy objEtsy = lstetsy.get(e);
					if(objEtsy != null)
					{
						List<Shops> lstShops = shopRepository.findByUserid(objEtsy.getEtsyuserid());
						for (int s = 0; s < lstShops.size(); s++) {
							
							int process_count = 25;
							int totalrecord = 25;
							int i=0;	
							do {
								int offset = process_count * i;
								
								Orders orders = orderServices.GetOrders(lstShops.get(s).getShop_id(), objEtsy, offset);							
								totalrecord = orders.getCount();
								orderServices.AddUpdateOrders(orders.getResults(), objSettings, objEtsy.getUserid());							
								i++;
							}while (totalrecord > process_count*i); 
						}
					}
				}
				System.out.println("Orders Updated.");
//				if(isupdateinventory)
//				{
//					asyncInventoryMethod();
//				}
				asyncInventoryMethod();
		}
	
	   @Async
	   @Override
	   public void asyncInventoryMethod() throws InterruptedException {
		  System.out.println("async Inventory Method called");
		  try
		  {
			  List<User> users = userRepository.findByRole("shopowner");
				for(int i=0; i < users.size(); i++)
			   	{
					List<Shops> shops = shopRepository.findByloginuserid(users.get(i).getId());
					
					for(int s=0;s<shops.size();s++)
					{
						String shopid = shops.get(s).getShop_id();
						List<ListingResult> listing = listingRepository.findByShopid(shops.get(s).getShop_id());
						
						if(listing.size() > 0)
						{
							for(int l=0; l < listing.size(); l++)
							{
								getInvetory(listing.get(l));
							}
							
						}
					}
			   	}
				System.out.println("Inventory updated.");
		  }
		  catch(Exception ex)
		  {
			  System.out.println("asyncInventoryMethod : " + ex.getMessage());
		  }
	   }
	   
	   @Async
	   @Override
	   public void getInvetory(ListingResult listing) throws InterruptedException {
		   try
		   {
			   	ListingInventory listInventory = shoplistingService.getListingInventory(listing.getListingid());
				listInventory.setListingid(listing.getListingid());							
				shoplistingService.saveInventory(listInventory);
				listing.setSku_number(listInventory.getProducts().get(0).getSku());
				
				if(listingRepository.existsByListingidAndShopid(listing.getListingid(), listing.getShopid()))
				{
					listingRepository.deleteListingByListingidAndShopid(listing.getListingid(), listing.getShopid());
				}
				listingRepository.save(listing);
		   }
		   catch(Exception ex)
		   {
			   System.out.println("setSkuNumber : "+ex.getMessage());
		   }
	   }
}
