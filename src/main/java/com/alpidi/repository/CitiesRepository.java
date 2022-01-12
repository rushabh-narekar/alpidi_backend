package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.Cities;

public interface CitiesRepository  extends MongoRepository<Cities, String>{
	List<Cities> findBystateid(String stateid);
	Optional<Cities> findBycityid(String countryid);
}
