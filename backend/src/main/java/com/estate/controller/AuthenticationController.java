package com.estate.controller;

import com.estate.controller.response.UserResponse;
import com.estate.model.User;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.controller.request.AuthenticationRequest;
import com.estate.controller.response.AuthenticationResponse;
import com.estate.controller.request.RegisterRequest;
import com.estate.service.AuthenticationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Create new user")
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authenticationService.register(request));
	}

	@Operation(summary = "Log user in")
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authenticationService.login(request));
	}

	@Operation(summary = "Get current authenticated user details")
	@GetMapping("/me") 
	public ResponseEntity<UserResponse> me(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		UserResponse userResponse = modelMapper.map(user, UserResponse.class);

		return ResponseEntity.ok(userResponse);
	}

}