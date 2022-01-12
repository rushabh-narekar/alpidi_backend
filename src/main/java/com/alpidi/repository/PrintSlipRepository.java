package com.alpidi.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.SettingPrintSlip;

public interface PrintSlipRepository extends MongoRepository<SettingPrintSlip, String>{
	Long deleteSettingPrintSlipByLoginuserid(String loginuserid);
	Boolean existsByLoginuserid(String loginuserid);
	
	Optional<SettingPrintSlip> findByLoginuserid(String loginuserid);
}
