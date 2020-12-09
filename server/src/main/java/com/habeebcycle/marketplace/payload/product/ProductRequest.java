package com.habeebcycle.marketplace.payload.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductRequest {

    @NotBlank @Size(min = 3, max = 255) private String title;
    @NotBlank private String slug;
    private BigDecimal price;
    private String description;
    private Long category;
    private Long owner;
    private String url;
    private Boolean publish;

    private String shortDescription;
    private BigDecimal regularPrice;
    private BigDecimal salesPrice;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private String sku;
    private String type;
    private String status;
    private String weight;
    private String dimension;
    private String notes;
    private Integer stock;

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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public LocalDateTime getSalesStart() {
        return salesStart;
    }

    public void setSalesStart(LocalDateTime salesStart) {
        this.salesStart = salesStart;
    }

    public LocalDateTime getSalesEnd() {
        return salesEnd;
    }

    public void setSalesEnd(LocalDateTime salesEnd) {
        this.salesEnd = salesEnd;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    @Override
    public String toString() {
        return "{" +
                "\"title\":\"" + title + "\"" +
                ",\"slug\":\"" + slug + "\"" +
                ",\"price\":" + price +
                ",\"description\":\"" + description + "\"" +
                ",\"category\":" + category +
                ",\"owner\":" + owner +
                ",\"url\":\"" + url + "\"" +
                ",\"publish\":" + publish +
                ",\"shortDescription\":\"" + shortDescription + "\"" +
                ",\"regularPrice\":" + regularPrice +
                ",\"salesPrice\":" + salesPrice +
                ",\"salesStart\":" + (salesStart != null ? "\"" + salesStart  + "\"" : null) +
                ",\"salesEnd\":" + (salesEnd != null ? "\"" + salesEnd + "\"" : null) +
                ",\"sku\":\"" + sku + "\"" +
                ",\"type\":\"" + type + "\"" +
                ",\"status\":\"" + status + "\"" +
                ",\"weight\":\"" + weight + "\"" +
                ",\"dimension\":\"" + dimension + "\"" +
                ",\"notes\":\"" + notes + "\"" +
                ",\"stock\":" + stock +
                "}";
    }
}
