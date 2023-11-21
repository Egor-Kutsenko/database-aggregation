package com.yehor.databasesaggregation.service.impl.search;

import com.yehor.databasesaggregation.model.entity.User;
import com.yehor.databasesaggregation.model.request.RequestUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSpecification {

    private final EntityManager entityManager;

    public List<User> searchUser(RequestUser requestUser) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cbQuery = cb.createQuery(User.class);
        final Root<User> root = cbQuery.from(User.class);
        Specification<User> specification = requestToSpecification(requestUser);
        cbQuery.where(specification.toPredicate(root, cbQuery, cb));
        final TypedQuery<User> query = entityManager.createQuery(cbQuery);
        return query.getResultList();
    }

    private Specification<User> requestToSpecification(RequestUser requestUser) {
        return new SpecificationBuilder<User>()
                .addIf(() -> requestUser.getId() != 0, () -> findById(requestUser.getId()))
                .addIf(() -> requestUser.getName() != null, () -> findByName(requestUser.getName()))
                .addIf(() -> requestUser.getSurname() != null, () -> findBySurname(requestUser.getSurname()))
                .addIf(() -> requestUser.getUsername() != null, () -> findByUsername(requestUser.getUsername()));
    }

    private static Specification<User> findById(long id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    private static Specification<User> findByName(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }

    private static Specification<User> findBySurname(String name) {
        return (root, query, cb) -> cb.equal(root.get("surname"), name);
    }

    private static Specification<User> findByUsername(String username) {
        return (root, query, cb) -> cb.equal(root.get("username"), username);
    }
}
