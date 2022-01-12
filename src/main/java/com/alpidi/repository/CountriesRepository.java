package com.alpidi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.Countries;

public interface CountriesRepository extends MongoRepository<Countries, String>{
	Optional<Countries> findByCountryid(String countryid);
}
