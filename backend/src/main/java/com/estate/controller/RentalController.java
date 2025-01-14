package com.estate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.model.Rental;
import com.estate.repository.RentalRepository;

@RestController
@RequestMapping("/api/")
public class RentalController {

	@Autowired
	private RentalRepository rentalRepository;
	
	@GetMapping("/rentals")
	public List<Rental> getAllUsers() {
		return rentalRepository.findAll();
	}
	
}
