package com.alpidi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.Etsy;

public interface EtsyRepository extends MongoRepository<Etsy, String> {	
	
	Optional<Etsy> findByEtsyuserid(String etsyuserid);
	
	List<Etsy> findByUserid(String userid);
	
	Boolean existsByEtsyuserid(String etsyuserid);
}
