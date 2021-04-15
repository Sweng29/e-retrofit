package com.retrofit.app.specifications;

import com.retrofit.app.filters.ProductFilterAttributes;
import com.retrofit.app.model.Product;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    private ProductSpecification(){}

    public static Specification<Product> filter(ProductFilterAttributes filterAttributes) {

        return (root, cq, cb) -> {

            final Collection<Predicate> predicates = new ArrayList<>();
            applyIdFilter(root, predicates, cb, filterAttributes.getProductId());
            applyKeywordSearch(root, cb, predicates, filterAttributes.getKeyword());
            applyIsActiveFilter(root, predicates, cb,filterAttributes.isActive());
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void applyIsActiveFilter(
            Root<Product> root,
            Collection<Predicate> predicates,
            CriteriaBuilder cb,
            boolean active) {
        predicates.add(cb.equal(root.get("isActive"),active));
    }

    private static void applyKeywordSearch(
            Root<Product> root,
            CriteriaBuilder cb,
            Collection<Predicate> predicates,
            String keyword) {
        if (keyword !=null && !keyword.trim().equals(""))
        {
            predicates.add(cb.or(
                    cb.like(root.get("productName"),"%" + keyword +"%"),
                    cb.like(root.get("productDescription"),"%" + keyword +"%")
            ));
        }
    }

    private static void applyIdFilter(
            Root<Product> root,
            Collection<Predicate> predicates,
            CriteriaBuilder cb,
            Long productId) {
        if (productId != null && productId > 0)
        {
            predicates.add(cb.equal(root.get("id"),productId));
        }
    }
}
