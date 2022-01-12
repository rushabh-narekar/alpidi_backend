package com.alpidi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.ListingInventory;

public interface ListingInventoryRepository extends MongoRepository<ListingInventory, String> {
	Long deleteListingInventoryByListingid(String listingid);
	Boolean existsByListingid(String listingid);
	
	Optional<ListingInventory> findOneByListingid(String listingid);
}
