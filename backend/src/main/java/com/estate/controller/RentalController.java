package com.estate.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import com.estate.dto.RentalDTO;
import com.estate.model.User;
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

	@Autowired
	private UserService userService;

	@Operation(summary = "Get all rentals")
	@GetMapping("/rentals")
	public ResponseEntity<Object> getAllUsers() {
		List<Rental> rentals = rentalService.getAllRentals();
		List<RentalDTO> rentalDTOS = rentals.stream().map(this::convertToDto).toList();

		return ResponseHandler.generateResponse("rentals", HttpStatus.OK, rentalDTOS);
	}

	@Operation(summary = "Create new rental")
	@PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createRental(
			@RequestParam("name") String name,
			@RequestParam("surface") Float surface,
			@RequestParam("price") Float price,
			@RequestParam("picture") MultipartFile file,
			@RequestParam("description") String description,
			Principal principal
	) throws IOException {
		String pictureName = storageService.store(file);

		User owner = userService.findByEmail(principal.getName());

		Rental rental = new Rental();
		rental.setName(name);
		rental.setSurface(surface);
		rental.setPrice(price);
		rental.setPicture(rentalService.getPictureLocation(pictureName));
		rental.setDescription(description);
		rental.setOwner(owner);
		rental.setCreatedAt(new Date());
		rental.setUpdatedAt(new Date());

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
	@PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateRental(
			@PathVariable Integer id,
			@RequestParam("name") String name,
			@RequestParam("surface") Float surface,
			@RequestParam("price") Float price,
			@RequestParam("description") String description
	) {
		ResponseEntity<Object> response;

		try {
			rentalService.updateRental(id, name, surface, price, description);
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

	private RentalDTO convertToDto(Rental rental) {
		return modelMapper.map(rental, RentalDTO.class);
	}
	
}
