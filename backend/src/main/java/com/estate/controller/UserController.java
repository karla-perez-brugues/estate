package com.estate.controller;

import com.estate.controller.response.UserResponse;
import com.estate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.model.User;

@RestController
@RequestMapping("/api/")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Get user by id")
	@GetMapping("/user/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
		User user = userService.findById(id);
		UserResponse userResponse = modelMapper.map(user, UserResponse.class);

		return ResponseEntity.ok(userResponse);
	}
	
}
