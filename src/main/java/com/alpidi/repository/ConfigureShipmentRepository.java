package com.alpidi.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.ConfigureShipment;

public interface ConfigureShipmentRepository extends MongoRepository<ConfigureShipment, String>{
	Long deleteConfigureShipmentByLoginuseridAndOrderid(String loginuserid,String orderid);
	Boolean existsByLoginuseridAndOrderid(String loginuserid,String orderid);
	
	Optional<ConfigureShipment> findByOrderid(String orderid);
}
