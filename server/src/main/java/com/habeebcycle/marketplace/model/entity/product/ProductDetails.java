package com.habeebcycle.marketplace.model.entity.product;

import org.hibernate.annotations.Formula;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

public class ProductDetails {

    @Size(max = 65535) private String shortDescription;
    @Digits(integer = 10, fraction = 2) private BigDecimal regularPrice;
    @Digits(integer = 10, fraction = 2) private BigDecimal salesPrice;
    private Instant salesStart;
    private Instant salesEnd;
    private String sku;
    private String type;
    private String status;
    private String weight;
    private String dimension;
    private String notes;
    private Integer stock;
    private Boolean featured;
    @Formula("salesEnd - salesStart > 0") private Boolean onSales;
    private String images;

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

    public Instant getSalesStart() {
        return salesStart;
    }

    public void setSalesStart(Instant salesStart) {
        this.salesStart = salesStart;
    }

    public Instant getSalesEnd() {
        return salesEnd;
    }

    public void setSalesEnd(Instant salesEnd) {
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

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getOnSales() {
        return onSales;
    }

    public void setOnSales(Boolean onSales) {
        this.onSales = onSales;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
