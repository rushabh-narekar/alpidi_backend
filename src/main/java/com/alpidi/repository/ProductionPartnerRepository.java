package com.alpidi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.ProductionPartnerDetails;

public interface ProductionPartnerRepository extends MongoRepository<ProductionPartnerDetails, String> {
	Boolean existsByProductionpartnerid(String productionpartnerid);
	long deleteProductionPartnerByProductionpartnerid(String productionpartnerid);
}
