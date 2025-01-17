package com.estate.service;

import com.estate.exception.ResourceNotFoundException;
import com.estate.model.Rental;
import com.estate.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public void createRental(Rental rental) {
        // todo: manage pictures
        rentalRepository.save(rental);
    }

    public Rental getRental(Integer id) {
        return rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }

    public void updateRental(Integer id, Rental data) {
        Rental rental = getRental(id);

        if (data.getName() != null) {
            rental.setName(data.getName());
        }
        if (data.getSurface() != null) {
            rental.setSurface(data.getSurface());
        }
        if (data.getPrice() != null) {
            rental.setPrice(data.getPrice());
        }
        if (data.getDescription() != null) {
            rental.setDescription(data.getDescription());
        }

        rental.setUpdatedAt(new Date());

        rentalRepository.save(rental);
    }
}
