package io.github.zul.springsmartspecification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.github.zul.springsmartspecification.domain.MongoExampleChild;

public interface MongoExampleChildRepository extends MongoRepository<MongoExampleChild, String> {

}
