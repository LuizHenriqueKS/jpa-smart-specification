package io.github.zul.springsmartspecification.jpa.query;

import java.util.Collections;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;

import io.github.zul.springsmartspecification.specification.SmartSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JpaSpecificationArgs {

    private SmartSpecification<?> specification;
    private List<Object> expressions;

    private JpaPathRouter router;
    private CriteriaQuery<?> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;

    private JpaSpecificationField field;

    public JpaSpecificationArgs createNewArgs(List<Object> expressions) {
        return JpaSpecificationArgs.builder()
                .specification(specification)
                .expressions(Collections.unmodifiableList(expressions))
                .router(router)
                .criteriaQuery(criteriaQuery)
                .criteriaBuilder(criteriaBuilder)
                .field(field)
                .build();
    }

    public JpaSpecificationArgs createNewArgs(JpaSpecificationField field) {
        return JpaSpecificationArgs.builder()
                .specification(specification)
                .expressions(expressions)
                .router(router)
                .criteriaQuery(criteriaQuery)
                .criteriaBuilder(criteriaBuilder)
                .field(field)
                .build();
    }

    public Object getValue() {
        return field.getValue(specification);
    }

    public Path<?> getPath() {
        return router.getPath(field.getFieldMeta().getPath());
    }

}
