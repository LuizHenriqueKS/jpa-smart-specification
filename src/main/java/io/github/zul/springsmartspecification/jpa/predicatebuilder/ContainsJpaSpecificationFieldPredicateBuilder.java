package io.github.zul.springsmartspecification.jpa.predicatebuilder;

import java.util.Set;

import io.github.zul.springsmartspecification.jpa.query.JpaSpecificationArgs;
import io.github.zul.springsmartspecification.jpa.query.JpaSpecificationFieldPredicateBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class ContainsJpaSpecificationFieldPredicateBuilder implements JpaSpecificationFieldPredicateBuilder {

    @Override
    public Set<String> getPatterns() {
        return Set.of("Contains", "Containing", "IsContaining");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Predicate build(JpaSpecificationArgs args) {
        if (args.getExpressions().size() != 2) {
            throw new IllegalArgumentException("Invalid number of expressions: " + args.getExpressions().size());
        }
        Expression<String> path = (Expression<String>) args.getExpressions().get(0);
        Object value = args.getExpressions().get(1);
        if (value instanceof String) {
            value = "%" + value + "%";
            return args.getCriteriaBuilder().like(path, (String) value);
        } else {
            Expression<String> exp = (Expression<String>) value;
            exp = args.getCriteriaBuilder().concat("%", exp);
            exp = args.getCriteriaBuilder().concat(exp, "%");
            return args.getCriteriaBuilder().like(path, exp);
        }
    }

}
