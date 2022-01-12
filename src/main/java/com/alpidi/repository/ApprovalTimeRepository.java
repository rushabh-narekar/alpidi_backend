package com.alpidi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.ApprovalTime;

public interface ApprovalTimeRepository extends MongoRepository<ApprovalTime, String> {

}
