package com.ua.sutty.crudmongodb.repository;

import com.ua.sutty.crudmongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserById(String id);
    void deleteUserById(String  id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsById(String  id);
}
