package com.alpidi.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.ListingProperties;

public interface ListingPropertiesRepository extends MongoRepository<ListingProperties, String> {
	Optional<ListingProperties> findBylistingid(String listingid);	
	Boolean existsByListingidAndShopid(String listingid,String shopid);
}
