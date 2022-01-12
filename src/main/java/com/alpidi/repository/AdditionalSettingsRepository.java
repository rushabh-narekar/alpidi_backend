package com.alpidi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.AdditionalSettings;

public interface AdditionalSettingsRepository extends MongoRepository<AdditionalSettings, String> {
	Long deleteAdditionalSettingsByUserid(String userid);
	Boolean existsByUserid(String userid);
	
	Optional<AdditionalSettings> findByuserid(String userid);
}
