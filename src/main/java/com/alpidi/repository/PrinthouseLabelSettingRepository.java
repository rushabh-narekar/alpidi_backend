package com.alpidi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.PrinthouseLabelSettings;

public interface PrinthouseLabelSettingRepository extends MongoRepository<PrinthouseLabelSettings, String> {
	Long deletePrinthouseLabelSettingsByPrinthouseuserid(String Printhouseid);
	Boolean existsByPrinthouseuserid(String Printhouseid);
	
	Optional<PrinthouseLabelSettings> findByPrinthouseuserid(String Printhouseid);
}
