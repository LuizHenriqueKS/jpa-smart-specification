package io.github.zul.springsmartspecification.jpa.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import io.github.zul.springsmartspecification.specification.SmartSpecification;

public class JpaSpecificationArgsFactory {

    public static JpaSpecificationArgs get(JpaPathRouter router, SmartSpecification<?> specification,
            CriteriaQuery<Long> cq, CriteriaBuilder cb) {
        return null;
    }

}
