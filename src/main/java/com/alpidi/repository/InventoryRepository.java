package com.alpidi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.alpidi.model.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String>{
	Long deleteByLoginuseridAndSizeAndColor(String loginuserid,String size,String color);
	Boolean existsByLoginuseridAndSizeAndColor(String loginuserid,String size,String color);
	
	//@Query("select i from Inventory i where i.loginuserid = ?1 and i.size = ?2 and i.color = ?3")
	List<Inventory> findByLoginuseridAndSizeAndColor(String loginuserid,String size,String color);
	
	Optional<Inventory> findByloginuseridAndSizeAndColor(String loginuserid,String size,String color);
	
	List<Inventory> findByLoginuserid(String loginuserid);
}
