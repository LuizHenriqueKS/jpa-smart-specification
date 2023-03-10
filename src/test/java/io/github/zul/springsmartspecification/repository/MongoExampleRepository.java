package io.github.zul.springsmartspecification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.github.zul.springsmartspecification.domain.MongoExample;

public interface MongoExampleRepository
        extends MongoRepository<MongoExample, String>, MongoSmartSpecificationRepository<MongoExample> {

}
