package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.Activities;

public interface ActivitiesRepository extends MongoRepository<Activities, String>{
	Long deleteByloginuseridAndOrderidAndInventoryid(String loginuserid,String orderid,String inventoryid);
	Boolean existsByloginuseridAndOrderidAndInventoryid(String loginuserid,String orderid,String inventoryid);
	
	Optional<Activities> findByLoginuseridAndOrderid(String loginuserid,String orderid);
	Optional<Activities> findByLoginuseridAndOrderidAndOrderStatus(String loginuserid,String orderid,int OrderStatus);
	
	Optional<Activities> findByLoginuseridAndOrderidAndOrderStatusOrderByUpdatedDateDesc(String loginuserid,String orderid,int OrderStatus);
	
	List<Activities> findByLoginuseridAndInventoryidAndOrderStatus(String loginuserid, String getid, int status);
	List<Activities> findByLoginuseridOrderByUpdatedDateDesc(String loginuserid);
	Optional<Activities> findByLoginuseridAndInventoryid(String getloginuserid, String getid);
	List<Activities> findByloginuseridAndInventoryid(String getloginuserid, String getid);
}
