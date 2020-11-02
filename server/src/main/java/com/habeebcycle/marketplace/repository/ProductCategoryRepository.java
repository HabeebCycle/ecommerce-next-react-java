package com.habeebcycle.marketplace.repository;

import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> findBySlug(String slug);

    Boolean existsBySlug(String slug);

    List<ProductCategory> findByParent(Long parent);
}
