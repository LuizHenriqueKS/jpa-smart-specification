package io.github.zul.springsmartspecification.repository;

import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoSmartSpecificationRepositoryImpl<D>
        extends DefaultMongoSmartSpecificationRepository<D> implements MongoSmartSpecificationRepository<D> {

    public MongoSmartSpecificationRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

}
