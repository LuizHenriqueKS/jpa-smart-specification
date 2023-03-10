package io.github.zul.springsmartspecification.mongo.query;

import java.util.List;

public interface MongoSpecificationFieldValueTypeHandler {

    boolean supportsType(Class<?> type);

    boolean isValidValue(Object value);

    List<Object> getValueExpressions(MongoSpecificationArgs args);

}
