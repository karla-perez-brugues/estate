package com.estate.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.estate.dto.RentalDTO;
import com.estate.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	private RentalService rentalService;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Get all rentals")
	@GetMapping("/rentals")
	public ResponseEntity<Object> getAllUsers() {
		List<Rental> rentals = rentalService.getAllRentals();
		List<RentalDTO> rentalDTOS = rentals.stream().map(this::convertToDto).toList();

		return ResponseHandler.generateResponse("rentals", HttpStatus.OK, rentalDTOS);
	}

	@Operation(summary = "Create new rental")
	@PostMapping("/rentals")
	public ResponseEntity<Object> createRental(@RequestBody Rental rental) {
		// todo: manage pictures

		ResponseEntity<Object> response;

		try {
			rentalService.createRental(rental);
			response = ResponseHandler.generateResponse("message", HttpStatus.CREATED, "Rental created !");
		} catch (Exception e) {
			response = ResponseHandler.generateResponse("message", HttpStatus.BAD_REQUEST, "Failed to create rental");
		}

		return response;
	}

	@Operation(summary = "Get rental by id")
	@GetMapping("/rentals/{id}")
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
		Rental rental = rentalService.getRental(id);
		RentalDTO rentalDTO = convertToDto(rental);

		return ResponseEntity.ok(rentalDTO);
	}

	@Operation(summary = "Edit one rental")
	@PutMapping(value = "/rentals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) // FIXME: HttpMediaTypeNotSupportedException: Content-Type 'multipart/form-data;boundary=----WebKitFormBoundaryS8d20PWFMVftQKJ2' is not supported
	public ResponseEntity<Object> updateRental(@PathVariable Integer id, @RequestBody Rental rentalData) {
		ResponseEntity<Object> response;

		try {
			rentalService.updateRental(id, rentalData);
			response = ResponseHandler.generateResponse("message", HttpStatus.OK, "Rental updated !");
		} catch (Exception e) {
			response = ResponseHandler.generateResponse("message", HttpStatus.BAD_REQUEST, "Failed to update rental");
		}

		return response;
	}

	private RentalDTO convertToDto(Rental rental) {
		return modelMapper.map(rental, RentalDTO.class);
	}
	
}
