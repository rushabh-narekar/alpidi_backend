package com.alpidi.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.AutomationRules;

public interface AutomationRulesRepository extends MongoRepository<AutomationRules, String>{
	Long deleteAutomationRulesByLoginuserid(String loginuserid);
	Boolean existsByLoginuserid(String loginuserid);
	
	List<AutomationRules> findByLoginuseridAndIsdelete(String loginuserid,Boolean isdelete);
}
