package com.estate.controller.response;

import com.estate.model.Rental;

import java.util.List;

public class RentalsResponse {
    private List<RentalResponse> rentals;

    public RentalsResponse() {}

    public RentalsResponse(List<RentalResponse> rentals) {
        this.rentals = rentals;
    }

    public List<RentalResponse> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalResponse> rentals) {
        this.rentals = rentals;
    }
}
