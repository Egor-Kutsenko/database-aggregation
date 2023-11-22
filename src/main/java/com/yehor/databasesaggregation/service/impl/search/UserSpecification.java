package com.yehor.databasesaggregation.service.impl.search;

import com.yehor.databasesaggregation.model.entity.UserEntity;
import com.yehor.databasesaggregation.model.request.RequestUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserSpecification {

    private final LocalSessionFactoryBean sessionFactoryBean;

    public List<UserEntity> searchUser(RequestUser requestUser) {
        final Session currentSession = Objects.requireNonNull(sessionFactoryBean.getObject()).openSession();
        final CriteriaBuilder cb = currentSession.getCriteriaBuilder();
        final CriteriaQuery<UserEntity> cbQuery = cb.createQuery(UserEntity.class);
        final Root<UserEntity> root = cbQuery.from(UserEntity.class);
        final Specification<UserEntity> specification = requestToSpecification(requestUser);
        cbQuery.where(specification.toPredicate(root, cbQuery, cb));
        final Query<UserEntity> query = currentSession.createQuery(cbQuery);
        return query.getResultList();
    }

    private Specification<UserEntity> requestToSpecification(RequestUser requestUser) {
        return new SpecificationBuilder<UserEntity>()
                .addIf(() -> requestUser.getId() != 0, () -> findById(requestUser.getId()))
                .addIf(() -> requestUser.getName() != null, () -> findByName(requestUser.getName()))
                .addIf(() -> requestUser.getSurname() != null, () -> findBySurname(requestUser.getSurname()))
                .addIf(() -> requestUser.getUsername() != null, () -> findByUsername(requestUser.getUsername()));
    }

    private static Specification<UserEntity> findById(long id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    private static Specification<UserEntity> findByName(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }

    private static Specification<UserEntity> findBySurname(String name) {
        return (root, query, cb) -> cb.equal(root.get("surname"), name);
    }

    private static Specification<UserEntity> findByUsername(String username) {
        return (root, query, cb) -> cb.equal(root.get("username"), username);
    }
}
