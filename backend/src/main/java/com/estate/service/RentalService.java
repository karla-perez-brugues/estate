package com.estate.service;

import com.estate.request.RentalRequest;
import com.estate.exception.ResourceNotFoundException;
import com.estate.model.Rental;
import com.estate.model.User;
import com.estate.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public void createRental(RentalRequest rentalRequest, Principal principal) throws IOException {
        String pictureName = storageService.store(rentalRequest.getPicture());

        User owner = userService.findByEmail(principal.getName());

        Rental rental = new Rental();
        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setPicture(getPictureLocation(pictureName));
        rental.setDescription(rentalRequest.getDescription());
        rental.setOwner(owner);
        rental.setCreatedAt(new Date());
        rental.setUpdatedAt(new Date());

        rentalRepository.save(rental);
    }

    public Rental getRental(Integer id) {
        return rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }

    public void updateRental(
            Integer id,
            RentalRequest rentalRequest
        ) {
        Rental rental = getRental(id);

        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setUpdatedAt(new Date());

        rentalRepository.save(rental);
    }

    public String getPictureLocation(String pictureName) {
        return "http://localhost:3001/api/rentals/picture/" + pictureName;
    }
}
