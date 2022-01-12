package com.alpidi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.Sizes;

public interface SizesRepository extends MongoRepository<Sizes, String>{
	Long deleteSizesBySize(String Size);
	Boolean existsBySize(String Size);
	
	Optional<Sizes> findBySize(String Size);
}
