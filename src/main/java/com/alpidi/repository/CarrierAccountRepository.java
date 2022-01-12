package com.alpidi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.CarrierAccountDetails;

public interface CarrierAccountRepository extends MongoRepository<CarrierAccountDetails, String>{
	Long deleteCarrierAccountDetailsByObjectid(String objectid);
	Boolean existsByObjectid(String objectid);
}
