package com.alpidi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.ShopImage;

public interface ListingimagesRepository extends MongoRepository<ShopImage, String> {

	//Boolean existByListing_idAndListing_image_id(String listing_id,String listing_image_id);
}
