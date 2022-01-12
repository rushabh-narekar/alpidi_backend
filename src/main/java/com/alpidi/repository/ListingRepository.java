package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.alpidi.model.ListingResult;
import com.alpidi.model.ShopImage;


public interface ListingRepository extends MongoRepository<ListingResult, String> {
	
	//pagination related methods
	Page<ListingResult> findByShopsectionidAndStateAndTitleContainingOrderByPriorityDescLastmodifiedtimestampDesc(String shop_section_id,String state,String title,  Pageable pageable);
	Page<ListingResult> findByStateAndTitleContainingOrderByPriorityDesc(String state,String title,  Pageable pageable);	
	Page<ListingResult> findByTitleContainingOrderByPriorityDesc(String title, Pageable pageable);
	//End of pagination methods
	
	Long deleteListingByListingidAndShopid(String listingid, String shopid);
	Long deleteByListingidIn(List<String> listingids);
	
	Boolean existsByListingidAndShopid(String listingid,String shopid);		
	Optional<ListingResult> findByListingidAndShopid(String listingid,String shopid);
	Optional<ListingResult> findByListingid(String listingid);
	Optional<ListingResult> findOneByListingid(String listingid);
	List<ListingResult> findByShopsectionidAndState(String shop_section_id,String state);
	
	@Query(value="{}", fields="{listingid : 1, _id : 0}")
	List<Object> findlistingidAndExcludeIdByShopid(String shopid);
	
	List<ListingResult> findByShopidAndUserid(String shopid,String userid);
	
	List<ListingResult> findByShopid(String shopid);
	List<ListingResult> findByShopidAndState(String shopid,String state);
	//long countBystate(String state);
	
	//void updateItemSkuNumber(String listingid,String skunumber);
}	
