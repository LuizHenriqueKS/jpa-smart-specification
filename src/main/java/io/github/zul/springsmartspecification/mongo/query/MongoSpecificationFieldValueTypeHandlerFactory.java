package io.github.zul.springsmartspecification.mongo.query;

import io.github.zul.springsmartspecification.mongo.valuetypehandler.DefaultMongoSpecificationFieldValueTypeHandler;

public class MongoSpecificationFieldValueTypeHandlerFactory {

    private final static MongoSpecificationFieldValueTypeHandler DEFAULT = new DefaultMongoSpecificationFieldValueTypeHandler();

    public static MongoSpecificationFieldValueTypeHandler get(Class<?> type) {
        return DEFAULT;
    }

}
