package com.estate.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import com.estate.request.RentalRequest;
import com.estate.response.RentalResponse;
import com.estate.service.RentalService;
import com.estate.service.StorageService;
import com.estate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estate.handler.ResponseHandler;
import com.estate.model.Rental;

@RestController
@RequestMapping("/api/")
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StorageService storageService;

	@Autowired
	private UserService userService;

	@Operation(summary = "Get all rentals")
	@GetMapping("/rentals")
	public ResponseEntity<Object> getAllUsers() {
		List<Rental> rentals = rentalService.getAllRentals();
		List<RentalResponse> rentalDTOS = rentals.stream().map(this::convertToResponse).toList();

		return ResponseHandler.generateResponse("rentals", HttpStatus.OK, rentalDTOS);
	}

	@Operation(summary = "Create new rental")
	@PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createRental(
		@ModelAttribute RentalRequest rentalRequest,
		Principal principal
	) {
		ResponseEntity<Object> response;

		try {
			rentalService.createRental(rentalRequest, principal);
			response = ResponseHandler.generateResponse("message", HttpStatus.CREATED, "Rental created !");
		} catch (Exception e) {
			response = ResponseHandler.generateResponse("message", HttpStatus.BAD_REQUEST, "Failed to create rental");
		}

		return response;
	}

	@Operation(summary = "Get rental by id")
	@GetMapping("/rentals/{id}")
	public ResponseEntity<RentalResponse> getRentalById(@PathVariable Integer id) {
		Rental rental = rentalService.getRental(id);
		RentalResponse rentalResponse = convertToResponse(rental);

		return ResponseEntity.ok(rentalResponse);
	}

	@Operation(summary = "Edit one rental")
	@PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateRental(
			@PathVariable Integer id,
			@ModelAttribute RentalRequest rentalRequest
	) {
		ResponseEntity<Object> response;

		try {
			rentalService.updateRental(id, rentalRequest);
			response = ResponseHandler.generateResponse("message", HttpStatus.OK, "Rental updated !");
		} catch (Exception e) {
			response = ResponseHandler.generateResponse("message", HttpStatus.BAD_REQUEST, "Failed to update rental");
		}

		return response;
	}

	@GetMapping(value = "/rentals/picture/{pictureName}")
	public @ResponseBody byte[] getPicture(@PathVariable String pictureName) throws IOException {
		return storageService.retrieve(pictureName);
	}

	private RentalResponse convertToResponse(Rental rental) {
		return modelMapper.map(rental, RentalResponse.class);
	}
	
}
