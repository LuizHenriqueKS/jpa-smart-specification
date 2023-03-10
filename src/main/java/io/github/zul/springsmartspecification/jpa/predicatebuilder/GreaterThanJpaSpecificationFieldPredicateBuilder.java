package io.github.zul.springsmartspecification.jpa.predicatebuilder;

import java.util.Set;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import io.github.zul.springsmartspecification.jpa.query.JpaSpecificationArgs;
import io.github.zul.springsmartspecification.jpa.query.JpaSpecificationFieldPredicateBuilder;
import io.github.zul.springsmartspecification.reflection.ReflectionUtils;

public class GreaterThanJpaSpecificationFieldPredicateBuilder implements JpaSpecificationFieldPredicateBuilder {

    @Override
    public Set<String> getPatterns() {
        return Set.of("GreaterThan", "Gt", "After");
    }

    @Override
    public Predicate build(JpaSpecificationArgs args) {
        if (args.getExpressions().size() != 2) {
            throw new IllegalArgumentException("Invalid number of expressions: " + args.getExpressions().size());
        }
        Expression<?> path = (Expression<?>) args.getExpressions().get(0);
        Object value = args.getExpressions().get(1);
        return ReflectionUtils.invokeFirstMethod(this, "buildMagic", args, path, value);
    }

    public <T extends Comparable<T>> Predicate buildMagic(JpaSpecificationArgs args, Expression<T> path, T value) {
        return args.getCriteriaBuilder().greaterThan(path, value);
    }

}
