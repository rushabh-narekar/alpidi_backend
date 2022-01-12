package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.States;

public interface StatesRepository extends MongoRepository<States, String>{
	List<States> findByCountryid(String countryid);
	Optional<States> findByStateid(String stateid);
}
