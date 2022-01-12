package com.alpidi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.Shops;

public interface ShopRepository extends MongoRepository<Shops, String> {
	
	List<Shops> findByUserid(String userid);
	Optional<Shops> findByuserid(String userid);
	List<Shops> findByUseridAndLoginuserid(String etsyuserid,String loginuserid);	
	List<Shops> findByloginuserid(String userid);
	
	Boolean existsByuserid(String userid);
	Optional<Shops> findByshopid(String shopid);
	
	Long deleteShopsByShopid(String shopid);
}
