package com.lightningcd.api.repository;


import com.lightningcd.api.model.Authentication;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthenticationRepository extends MongoRepository<Authentication, ObjectId> {

    Authentication findByUsername(String username);

}
