package com.estate.service;

import com.estate.controller.request.RentalRequest;
import com.estate.controller.response.GenericResponse;
import com.estate.exception.ResourceNotFoundException;
import com.estate.model.Rental;
import com.estate.model.User;
import com.estate.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<GenericResponse> createRental(RentalRequest rentalRequest, Principal principal) {
        GenericResponse messageResponse = new GenericResponse();
        HttpStatus httpStatus;

        try {
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

            messageResponse.setMessage("Rental created !");
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            messageResponse.setMessage("Failed to create rental");
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(messageResponse);

    }

    public Rental getRental(Integer id) {
        return rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }

    public ResponseEntity<GenericResponse> updateRental(
            Integer id,
            RentalRequest rentalRequest
    ) {
        GenericResponse messageResponse = new GenericResponse();
        HttpStatus httpStatus;

        try {
            Rental rental = getRental(id);

            rental.setName(rentalRequest.getName());
            rental.setSurface(rentalRequest.getSurface());
            rental.setPrice(rentalRequest.getPrice());
            rental.setDescription(rentalRequest.getDescription());
            rental.setUpdatedAt(new Date());

            rentalRepository.save(rental);

            messageResponse.setMessage("Rental updated !");
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            messageResponse.setMessage("Failed to update rental");
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(messageResponse);
    }

    public String getPictureLocation(String pictureName) {
        return "http://localhost:3001/api/rentals/picture/" + pictureName;
    }
}
