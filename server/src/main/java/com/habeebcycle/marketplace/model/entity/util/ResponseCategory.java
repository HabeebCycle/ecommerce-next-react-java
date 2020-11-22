package com.habeebcycle.marketplace.model.entity.util;

public class ResponseCategory {
    private Long id;
    private String name;
    private String slug;
    private String link;

    public ResponseCategory(Long id, String name, String slug, String link) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.link = link;
    }

    public ResponseCategory() {
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
