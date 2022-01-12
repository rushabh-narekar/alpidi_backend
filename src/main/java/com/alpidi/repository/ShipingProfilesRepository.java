package com.alpidi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.ShipingProfileDetails;
import com.alpidi.model.Shops;

public interface ShipingProfilesRepository extends MongoRepository<ShipingProfileDetails, String> {
	Boolean existsByshippingprofileid(String shippingprofileid);
	Optional<ShipingProfileDetails> findByshippingprofileid(String shippingprofileid);
	
	Optional<ShipingProfileDetails> findByUserid(String userid);
	Long deleteShipingProfilesByshippingprofileid(String shippingprofileid);
}
