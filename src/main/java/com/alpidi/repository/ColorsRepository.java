package com.alpidi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.Colors;

public interface ColorsRepository extends MongoRepository<Colors, String>{
	Long deleteColorsByColor(String Color);
	Boolean existsByColor(String Color);
	
	Optional<Colors> findOneByColor(String Color);
}
