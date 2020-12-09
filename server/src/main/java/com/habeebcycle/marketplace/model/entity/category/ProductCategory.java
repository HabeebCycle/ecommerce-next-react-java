package com.habeebcycle.marketplace.model.entity.category;

import com.habeebcycle.marketplace.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_categories", uniqueConstraints = {@UniqueConstraint(columnNames = "slug")})
public class ProductCategory extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min = 3, max = 255) private String name;
    @NotBlank private String slug;
    private String description;
    private Long parent;
    private Long image;
    private Boolean status;

    public ProductCategory() {
    }

    public ProductCategory(@NotBlank @Size(min = 3, max = 255) String name, @NotBlank String slug, @Size(max = 65535)String description) {
        this.name = name;
        this.slug = slug;
        this.description = description;
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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
