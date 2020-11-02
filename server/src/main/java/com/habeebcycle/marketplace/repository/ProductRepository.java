package com.habeebcycle.marketplace.repository;

import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);

    Boolean existsBySlug(String slug);

    List<Product> findByPrice(BigDecimal price);

    List<Product> findByIdIn(List<Long> productIds);

    List<Product> findByCategoryId(Long id);

    List<Product> findByOwner(Long owner);

    List<Product> findByCategoryIn(List<ProductCategory> categories);

    @Query("SELECT p FROM Product p WHERE p.price >= :minPrice AND p.price <= :maxPrice")
    List<Product> findByMinPriceAndMaxPrice(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT MAX(p.price) FROM Product p")
    BigDecimal findMaxPrice();

    @Query("SELECT MIN(p.price) FROM Product p")
    BigDecimal findMinPrice();

    @Query("SELECT COUNT(*) FROM Product")
    Long findNumberOfProducts();

    @Query("SELECT COUNT(*) FROM Product p WHERE p.category = :catId")
    Long findNumberOfProductInCategory(@Param("catId") Long catId);

    @Query("SELECT c FROM ProductCategory c where c.parent = :catId")
    List<ProductCategory> findAllChildrenCategory(@Param("catId") Long catId);

}
