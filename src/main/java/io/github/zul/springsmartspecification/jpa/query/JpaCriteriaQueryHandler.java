package io.github.zul.springsmartspecification.jpa.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import io.github.zul.springsmartspecification.projection.ProjectionFieldMeta;
import io.github.zul.springsmartspecification.projection.ProjectionHandler;
import io.github.zul.springsmartspecification.projection.ProjectionHandlerFactory;
import io.github.zul.springsmartspecification.specification.SmartSpecification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaCriteriaQueryHandler<D> {

    private final EntityManager em;
    private final SmartSpecification<D> specification;

    public long count() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<D> root = cq.from(specification.getDomainClass());
        JpaPathRouter router = new JpaPathRouter(root);
        cq.select(cb.count(root));
        cq.where(buildPredicate(router, cq, cb));
        return em.createQuery(cq).getSingleResult();
    }

    public boolean exists() {
        return count() > 0;
    }

    public Optional<D> findOne() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<D> cq = cb.createQuery(specification.getDomainClass());
            Root<D> root = cq.from(specification.getDomainClass());
            JpaPathRouter router = new JpaPathRouter(root);
            cq.where(buildPredicate(router, cq, cb));
            D result = em.createQuery(cq).getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    private Expression<Boolean> buildPredicate(JpaPathRouter router, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return JpaPredicateBuilder.build(router, specification, cq, cb);
    }

    public List<D> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<D> cq = cb.createQuery(specification.getDomainClass());
        Root<D> root = cq.from(specification.getDomainClass());
        JpaPathRouter router = new JpaPathRouter(root);
        cq.where(buildPredicate(router, cq, cb));
        return em.createQuery(cq).getResultList();
    }

    public Page<D> findAll(Pageable pageable) {
        Long count = count();
        if (count == 0) {
            return Page.empty(pageable);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<D> cq = cb.createQuery(specification.getDomainClass());
        Root<D> root = cq.from(specification.getDomainClass());
        JpaPathRouter router = new JpaPathRouter(root);
        cq.where(buildPredicate(router, cq, cb));
        handleSorts(router, cb, cq, pageable);
        TypedQuery<D> query = em.createQuery(cq);
        handlePageable(query, pageable);
        List<D> content = query.getResultList();
        return new PageImpl<>(content, pageable, count);
    }

    private void handleSorts(JpaPathRouter router, CriteriaBuilder cb, CriteriaQuery<?> cq, Pageable pageable) {
        if (pageable.getSort() != null) {
            List<Order> orderList = new ArrayList<>();
            pageable.getSort().forEach(sort -> {
                List<String> path = List.of(sort.getProperty().split("\\."));
                Expression<?> selection = router.getPath(path);
                orderList.add(sort.isAscending() ? cb.asc(selection) : cb.desc(selection));
            });
            Collections.reverse(orderList);
            cq.orderBy(orderList);
        }
    }

    private void handlePageable(TypedQuery<?> query, Pageable pageable) {
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
    }

    public <P> Optional<P> findOneProjectedBy(Class<P> projectionClass) {
        try {
            ProjectionHandler<D, P> handler = ProjectionHandlerFactory.get(specification.getDomainClass(),
                    projectionClass);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<D> root = cq.from(specification.getDomainClass());
            JpaPathRouter router = new JpaPathRouter(root);
            cq.where(buildPredicate(router, cq, cb));
            cq.multiselect(getMultiSelect(handler, router));
            Object[] row = em.createQuery(cq).getSingleResult();
            P projection = handler.create(row);
            return Optional.ofNullable(projection);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    private <P> List<Selection<?>> getMultiSelect(ProjectionHandler<D, P> handler, JpaPathRouter router) {
        List<Selection<?>> selections = new ArrayList<>();
        for (ProjectionFieldMeta fieldMeta : handler.getFields()) {
            selections.add(router.getPath(fieldMeta.getPath()));
        }
        return selections;
    }

    public <P> List<P> findAllProjectedBy(Class<P> projectionClass) {
        ProjectionHandler<D, P> handler = ProjectionHandlerFactory.get(specification.getDomainClass(),
                projectionClass);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<D> root = cq.from(specification.getDomainClass());
        JpaPathRouter router = new JpaPathRouter(root);
        cq.where(buildPredicate(router, cq, cb));
        cq.multiselect(getMultiSelect(handler, router));
        List<Object[]> rowList = em.createQuery(cq).getResultList();
        List<P> projectionList = handler.create(rowList);
        return projectionList;
    }

    public <P> Page<P> findAllProjectedBy(Class<P> projectionClass, Pageable pageable) {
        long count = count();
        if (count == 0) {
            return Page.empty(pageable);
        }
        ProjectionHandler<D, P> handler = ProjectionHandlerFactory.get(specification.getDomainClass(),
                projectionClass);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<D> root = cq.from(specification.getDomainClass());
        JpaPathRouter router = new JpaPathRouter(root);
        cq.where(buildPredicate(router, cq, cb));
        handleSorts(router, cb, cq, pageable);
        cq.multiselect(getMultiSelect(handler, router));
        TypedQuery<Object[]> query = em.createQuery(cq);
        handlePageable(query, pageable);
        List<Object[]> rowList = query.getResultList();
        List<P> content = handler.create(rowList);
        return new PageImpl<>(content, pageable, count);
    }

}
