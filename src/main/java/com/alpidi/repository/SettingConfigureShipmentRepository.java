package com.alpidi.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.DefaultConfiureShipment;

public interface SettingConfigureShipmentRepository extends MongoRepository<DefaultConfiureShipment, String>{
	Long deleteDefaultConfiureShipmentShipmentByLoginuserid(String userid);
	Boolean existsByLoginuserid(String userid);
	
	Optional<DefaultConfiureShipment> findByLoginuserid(String userid);
}
