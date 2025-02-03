package com.estate.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRequest {
    private String message;
    private Integer userId;
    private Integer rentalId;

    public MessageRequest() {}

    public MessageRequest(String message, Integer userId, Integer rentalId) {
        this.message = message;
        this.userId = userId;
        this.rentalId = rentalId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("rental_id")
    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }
}
