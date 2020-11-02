package com.habeebcycle.marketplace.payload;

public class ResourceAvailability {

    private Boolean available;

    public ResourceAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
