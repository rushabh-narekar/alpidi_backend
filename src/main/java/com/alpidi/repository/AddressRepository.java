package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.ShipAddress;

public interface AddressRepository extends MongoRepository<ShipAddress, String>{
	List<ShipAddress> findByLoginuseridAndIsdelete(String loginuserid,Boolean isdelete);
	List<ShipAddress> findByIsdelete(Boolean isdelete);
	
	Optional<ShipAddress> findByIsdefaultaddress(Boolean isdefaultaddress);
}
