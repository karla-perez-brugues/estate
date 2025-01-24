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
        rentalRepository.save(rental);
    }

    public Rental getRental(Integer id) {
        return rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }

    public void updateRental(
            Integer id,
            String name,
            Float surface,
            Float price,
            String description
        ) {
        Rental rental = getRental(id);

        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setUpdatedAt(new Date());

        rentalRepository.save(rental);
    }

    public String getPictureLocation(String pictureName) {
        return "http://localhost:3001/api/rentals/picture/" + pictureName;
    }
}
