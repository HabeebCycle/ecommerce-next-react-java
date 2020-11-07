package com.habeebcycle.marketplace.payload.category;

import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.model.entity.util.ChildCategory;
import com.habeebcycle.marketplace.payload.user.UserSummary;

import java.time.Instant;
import java.util.List;

public class ProductCategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private List<ChildCategory> children;
    private Image image;
    private Instant createdAt;
    private Instant updatedAt;
    private UserSummary createdBy;
    private UserSummary updatedBy;

    public ProductCategoryResponse() {
    }

    public ProductCategoryResponse(Long id, String name, String slug, String description, List<ChildCategory> children, Image image) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.children = children;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ChildCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ChildCategory> children) {
        this.children = children;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
