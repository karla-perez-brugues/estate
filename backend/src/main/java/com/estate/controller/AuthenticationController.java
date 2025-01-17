package com.estate.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.authentication.AuthenticationRequest;
import com.estate.authentication.AuthenticationResponse;
import com.estate.authentication.RegisterRequest;
import com.estate.service.AuthenticationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

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
	public ResponseEntity<UserDetails> me(Authentication authentication) { 
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		// TODO: create DTO, do not send entity
		return ResponseEntity.ok(userDetails);
	}

}