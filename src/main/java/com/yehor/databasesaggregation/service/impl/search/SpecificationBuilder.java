package com.yehor.databasesaggregation.service.impl.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class SpecificationBuilder<T> implements Specification<T> {

    private final List<Specification<T>> specifications = new LinkedList<>();

    @Override
    public Predicate toPredicate(@NonNull Root root, @NonNull CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                specifications
                        .stream()
                        .map(s -> criteriaBuilder.and(s.toPredicate(root, query, criteriaBuilder)))
                        .toList()
                        .toArray(new Predicate[specifications.size()])
        );
    }

    @Override
    public Specification<T> and(Specification<T> other) {
        specifications.add(other);
        return this;
    }

    @Override
    public Specification<T> or(Specification other) {
        return Specification.super.or(other);
    }

    public SpecificationBuilder<T> addIf(Supplier<Boolean> condition, Supplier<Specification<T>> specificationFunction) {
        if (condition.get()) {
            specifications.add(specificationFunction.get());
        }
        return this;
    }
}
