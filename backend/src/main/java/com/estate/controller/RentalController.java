package com.estate.controller;

import java.io.IOException;
import java.util.List;

import com.estate.dto.RentalDTO;
import com.estate.service.RentalService;
import com.estate.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estate.handler.ResponseHandler;
import com.estate.model.Rental;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StorageService storageService;

	@Operation(summary = "Get all rentals")
	@GetMapping("/rentals")
	public ResponseEntity<Object> getAllUsers() {
		List<Rental> rentals = rentalService.getAllRentals();
		List<RentalDTO> rentalDTOS = rentals.stream().map(this::convertToDto).toList();

		return ResponseHandler.generateResponse("rentals", HttpStatus.OK, rentalDTOS);
	}

	@Operation(summary = "Create new rental")
	@PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // FIXME: HttpMediaTypeNotSupportedException: Content-Type 'multipart/form-data;boundary=----WebKitFormBoundaryS8d20PWFMVftQKJ2' is not supported
	public ResponseEntity<Object> createRental(@RequestBody Rental rental, @RequestParam("picture") MultipartFile file) throws IOException {
		storageService.store(file);
		rental.setPicture(file.getOriginalFilename());

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
	public ResponseEntity<Object> updateRental(@PathVariable Integer id, @RequestBody Rental rental) {
		ResponseEntity<Object> response;

		try {
			rentalService.updateRental(id, rental);
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
