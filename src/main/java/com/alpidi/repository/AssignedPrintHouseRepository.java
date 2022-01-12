package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.AssignOrdersPrintHouse;
import com.alpidi.model.OrderDetails;
import com.alpidi.model.ShipingProfileDetails;

public interface AssignedPrintHouseRepository extends MongoRepository<AssignOrdersPrintHouse, String> {
	Long deleteAssignedOrdersPrintHouseByOrderid(String orderid);
	Boolean existsByOrderid(String orderid);
	Optional<AssignOrdersPrintHouse> findByOrderid(String orderid);
	
	List<AssignOrdersPrintHouse> findByPrintHouseUserId(String printHouseUserId);
}
