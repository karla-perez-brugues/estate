package com.estate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.exception.ResourceNotFoundException;
import com.estate.model.User;
import com.estate.repository.UserRepository;

@RestController
@RequestMapping("/api/")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id) {
		User user = userRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

		return ResponseEntity.ok(user);
	}
	
}
