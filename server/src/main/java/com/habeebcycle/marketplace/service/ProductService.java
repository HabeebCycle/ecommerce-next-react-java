package com.habeebcycle.marketplace.service;

import com.habeebcycle.marketplace.model.entity.product.Product;
import com.habeebcycle.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Optional<Product> getProductBySlug(String slug){
        return productRepository.findBySlug(slug);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Boolean slugExists(String slug){
        return productRepository.existsBySlug(slug);
    }

    public List<Product> getProductByPrice(BigDecimal price){
        return productRepository.findByPrice(price);
    }

    public List<Product> getProductInId(List<Long> ids){
        return productRepository.findByIdIn(ids);
    }

    public List<Product> getProductByCategory(Long catId){
        return productRepository.findByCategoryId(catId);
    }

    public List<Product> getProductByOwner(Long owner){
        return productRepository.findByOwner(owner);
    }

    public List<Product> getPriceRangeProduct(BigDecimal minPrice, BigDecimal maxPrice){
        return productRepository.findByMinPriceAndMaxPrice(minPrice, maxPrice);
    }

    public BigDecimal getMaxProductPrice(){
        return productRepository.findMaxPrice();
    }

    public BigDecimal getMinProductPrice(){
        return productRepository.findMinPrice();
    }

    public Long getNumberOfProducts(){
        return productRepository.findNumberOfProducts();
    }

    public Long getNumberOfProductsInCategory(Long catId){
        return productRepository.findNumberOfProductInCategory(catId);
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void deleteProduct(Product product){
        productRepository.delete(product);
    }

    public void deleteProducts(List<Long> ids){
        productRepository.deleteAll(productRepository.findAllById(ids));
    }

    public List<Product> getOnSales(){
        List<Product> allProducts = getAllProduct();
        return allProducts
                .stream()
                .filter(product -> product.getProductDetails().getOnSales())
                .collect(Collectors.toList());
    }

    public List<Product> getChildProducts(Long catId){
        return productRepository.findByCategoryIn(productRepository.findAllChildrenCategory(catId));
    }

}
