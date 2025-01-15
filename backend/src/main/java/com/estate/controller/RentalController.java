package com.estate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.exception.ResourceNotFoundException;
import com.estate.handler.ResponseHandler;
import com.estate.model.Rental;
import com.estate.repository.RentalRepository;

@RestController
@RequestMapping("/api/")
public class RentalController {

	@Autowired
	private RentalRepository rentalRepository;
	
	@GetMapping("/rentals")
	public ResponseEntity<Object> getAllUsers() {
		List<Rental> rentals = rentalRepository.findAll();

		return ResponseHandler.generateResponse("rentals", HttpStatus.OK, rentals);
	}
	
	@PostMapping("/rentals")
	public Rental createRental(@RequestBody Rental rental) {
		return rentalRepository.save(rental);
	}

	@GetMapping("/rentals/{id}")
	public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
		Rental rental = rentalRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Rental does not exist"));
		
		return ResponseEntity.ok(rental);
	}

	@PutMapping("/rentals/{id}")
	public ResponseEntity<Rental> updateRental(@PathVariable Integer id, @RequestBody Rental rentalData) {
		Rental rental = rentalRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Rental does not exist"));

		rental.setName(rentalData.getName());
		rental.setSurface(rentalData.getSurface());
		rental.setPrice(rentalData.getPrice());
		rental.setPicture(rentalData.getPicture());
		rental.setDescription(rentalData.getDescription());

		Rental updatedRental = rentalRepository.save(rental);

		return ResponseEntity.ok(updatedRental);
	}
	
}
