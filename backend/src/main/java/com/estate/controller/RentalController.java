package com.estate.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import com.estate.controller.request.RentalRequest;
import com.estate.controller.response.GenericResponse;
import com.estate.controller.response.RentalResponse;
import com.estate.controller.response.RentalsResponse;
import com.estate.service.RentalService;
import com.estate.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@Operation(summary = "Get all rentals")
	@GetMapping("/rentals")
	public ResponseEntity<RentalsResponse> getAllRentals() {
		List<Rental> rentals = rentalService.getAllRentals();
		List<RentalResponse> rentalResponseList = rentals.stream().map(this::convertToResponse).toList();
		RentalsResponse rentalsResponse = new RentalsResponse(rentalResponseList);

		return ResponseEntity.ok(rentalsResponse);
	}

	@Operation(summary = "Create new rental")
	@PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> createRental(
		@ModelAttribute RentalRequest rentalRequest,
		Principal principal
	) {
		return rentalService.createRental(rentalRequest, principal);
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
	public ResponseEntity<GenericResponse> updateRental(
			@PathVariable Integer id,
			@ModelAttribute RentalRequest rentalRequest
	) {
		return rentalService.updateRental(id, rentalRequest);
	}

	@Operation(summary = "Retrieve rental picture by its name")
	@GetMapping(value = "/rentals/picture/{pictureName}")
	public @ResponseBody byte[] getPicture(@PathVariable String pictureName) throws IOException {
		return storageService.retrieve(pictureName);
	}

	private RentalResponse convertToResponse(Rental rental) {
		return modelMapper.map(rental, RentalResponse.class);
	}
	
}
