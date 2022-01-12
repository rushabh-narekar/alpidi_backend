package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	  Optional<User> findByEmail(String email);
	  
	  List<User> findByRole(String role);
	  
	  //List<User> findByRoleNotAndIsdeleted(String role,Boolean isdeleted);
	  
	  List<User> findByRoleAndIsdeleted(String role,Boolean isdeleted);
	  
	  Optional<User> findByIdAndIsdeleted(String userid,Boolean isdelete);
	  
	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
	Page<User> findByRoleNotAndIsdeletedAndEmailContaining(String role, boolean isdeleted,String query, Pageable paging);
	}