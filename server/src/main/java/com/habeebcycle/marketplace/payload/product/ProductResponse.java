package com.habeebcycle.marketplace.payload.product;

import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.model.entity.user.User;
import com.habeebcycle.marketplace.payload.user.UserSummary;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.Instant;

@ResponseBody
public class ProductResponse {

    private Long id;
    private String title;
    private String slug;
    private BigDecimal price;
    private String description;
    private ProductCategory category;
    private Image thumbnail;
    private UserSummary owner;
    private ProductDetailResponse productDetails;
    private Instant createdAt;
    private Instant updatedAt;
    private UserSummary createdBy;
    private UserSummary updatedBy;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String title, String slug, BigDecimal price, String description,
                           ProductCategory category, Image thumbnail, UserSummary owner, ProductDetailResponse productDetails) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.price = price;
        this.description = description;
        this.category = category;
        this.thumbnail = thumbnail;
        this.owner = owner;
        this.productDetails = productDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public UserSummary getOwner() {
        return owner;
    }

    public void setOwner(UserSummary owner) {
        this.owner = owner;
    }

    public ProductDetailResponse getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetailResponse productDetails) {
        this.productDetails = productDetails;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public UserSummary getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserSummary updatedBy) {
        this.updatedBy = updatedBy;
    }
}
