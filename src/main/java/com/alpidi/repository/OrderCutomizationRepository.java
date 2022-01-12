package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.CustomizedOrders;
import com.alpidi.model.OrderCustomized;

public interface OrderCutomizationRepository extends MongoRepository<CustomizedOrders, String>{
	Long deleteCustomizedOrdersByOrderid(String orderid);
	Boolean existsByOrderid(String orderid);
	Optional<CustomizedOrders> findByOrderid(String orderid);
	
	Optional<CustomizedOrders> findByOrderidAndIsAssigned(String orderid,Boolean isAssigned);
	
	List<CustomizedOrders> findByIsAssignedAndTransactionPrinthouseidAndTransactionIsApprovedByPrinthouse(Boolean isapprove,String printhouseid,Boolean isapprovebyprinthouse);
	
	List<CustomizedOrders> findByIsAssignedAndTransactionPrinthouseid(Boolean isassigned,String printhouseid);
	Optional<CustomizedOrders> findByOrderidAndTransactionListingidContaining(String orderid,String listingid);
}
