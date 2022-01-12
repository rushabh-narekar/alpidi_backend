package com.alpidi.security.sevices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alpidi.model.ListingInventory;
import com.alpidi.model.ListingResult;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;

@Service
public class AsyncServiceImpl implements AsyncService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	private ShoplistingService shoplistingService;
	
	   @Async
	   @Override
	   public void asyncMethod() throws InterruptedException {
		  System.out.println("asyncMethod called");
	      //Thread.sleep(10000);
	      //System.out.println("Calling other service..");
	      //System.out.println("Thread: " + Thread.currentThread().getName());
	      
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
							String listingid = listing.get(l).getListingid();
							
							ListingInventory listInventory = shoplistingService.getListingInventory(listing.get(l).getListingid());
							listInventory.setListingid(listingid);
							
							shoplistingService.saveInventory(listInventory);
							
							System.out.println("Sku no before " + listInventory.getProducts().get(0).getSku());
							listing.get(l).setSku_number(listInventory.getProducts().get(0).getSku());
							System.out.println("Sku no after : " + listing.get(l).getSku_number());
							if(listingRepository.existsByListingidAndShopid(listingid, shopid)) 
							{
								listingRepository.deleteListingByListingidAndShopid(listingid, shopid);
							}
							System.out.println("Img 75 :  "  + listing.get(l).getimg75());
							listingRepository.save(listing.get(l));
						}
						
					}
				}
		   	}
			System.out.println("Inventory updated.");
	      
	   }
	}
