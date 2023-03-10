package io.github.zul.springsmartspecification.jpa.query;

import java.util.List;

import io.github.zul.springsmartspecification.jpa.valuetypehandler.DefaultJpaSpecificationFieldValueTypeHandler;
import io.github.zul.springsmartspecification.jpa.valuetypehandler.StringJpaSpecificationFieldValueTypeHandler;

public class JpaSpecificationFieldValueTypeHandlerFactory {

    private static final List<JpaSpecificationFieldValueTypeHandler> handlers = List.of(
            new StringJpaSpecificationFieldValueTypeHandler());

    private static final JpaSpecificationFieldValueTypeHandler defaultHandler = new DefaultJpaSpecificationFieldValueTypeHandler();

    public static JpaSpecificationFieldValueTypeHandler get(Class<?> type) {
        for (JpaSpecificationFieldValueTypeHandler typeHandler : handlers) {
            if (typeHandler.supportsType(type)) {
                return typeHandler;
            }
        }
        return defaultHandler;
    }

}
