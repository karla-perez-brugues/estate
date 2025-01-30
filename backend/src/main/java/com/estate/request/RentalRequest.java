package com.estate.request;

import org.springframework.web.multipart.MultipartFile;

public class RentalRequest {

    private String name;
    private Float surface;
    private Float price;
    private String description;
    private MultipartFile picture;

    RentalRequest() {}

    RentalRequest(String name, Float surface, Float price, String description) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
    }

    RentalRequest(String name, Float surface, Float price, String description, MultipartFile picture) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSurface() {
        return surface;
    }

    public void setSurface(Float surface) {
        this.surface = surface;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
