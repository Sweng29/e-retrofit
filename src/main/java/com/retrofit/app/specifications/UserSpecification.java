package com.retrofit.app.specifications;

import com.retrofit.app.constants.ProfileStatus;
import com.retrofit.app.constants.RoleConstant;
import com.retrofit.app.filters.UserFilterAttributes;
import com.retrofit.app.model.User;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    private UserSpecification(){}

    public static Specification<User> findAll(UserFilterAttributes filterAttributes) {

        return (root, cq, cb) -> {

                    final Collection<Predicate> predicates = new ArrayList<>();
                    applyIdFilter(root, predicates, cb, filterAttributes.getId());
                    applyKeywordSearch(root, cb, predicates, filterAttributes.getKeyword());
                    applyProfileStatusFilter(root, predicates, cb,filterAttributes.getProfileStatus());
                    applyRoleConstantFilter(root, predicates, cb,filterAttributes.getRoleConstant());
                    applyIsActiveFilter(root, predicates, cb,filterAttributes.isActive());
                    return cb.and(predicates.toArray(new Predicate[0]));
                };
    }

    private static void applyIsActiveFilter(
            Root<User> root,
            Collection<Predicate> predicates,
            CriteriaBuilder cb,
            boolean active) {
        predicates.add(cb.equal(root.get("isActive"),active));
    }

    private static void applyRoleConstantFilter(
            Root<User> root,
            Collection<Predicate> predicates,
            CriteriaBuilder cb,
            RoleConstant roleConstant) {
        if (roleConstant !=null)
        {
            predicates.add(cb.equal(
                    root
                            .join("roles", JoinType.LEFT)
                            .get("roleConstant"),
                    roleConstant));
        }
    }

    private static void applyProfileStatusFilter(
            Root<User> root,
            Collection<Predicate> predicates,
            CriteriaBuilder cb,
            ProfileStatus profileStatus) {
        if (profileStatus!=null)
        {
            predicates.add(cb.equal(root.get("profileStatus"),profileStatus));
        }
    }

    private static void applyKeywordSearch(
            Root<User> root,
            CriteriaBuilder cb,
            Collection<Predicate> predicates,
            String keyword) {
        if (keyword !=null && !keyword.trim().equals(""))
        {
            predicates.add(cb.or(
                    cb.like(root.get("username"),"%" + keyword +"%"),
                    cb.like(root.get("email"),"%" + keyword +"%"),
                    cb.like(root.get("mobileNumber"),"%" + keyword +"%")
            ));
        }
    }

    private static void applyIdFilter(
            Root<User> root,
            Collection<Predicate> predicates,
            CriteriaBuilder cb,
            Integer id) {
        if (id != null && id > 0)
        {
            predicates.add(cb.equal(root.get("id"),id));
        }
    }
}
