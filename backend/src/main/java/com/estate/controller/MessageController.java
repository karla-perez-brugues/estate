package com.estate.controller;

import com.estate.dto.MessageDTO;
import com.estate.handler.ResponseHandler;
import com.estate.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Operation(summary = "Create new message")
	@PostMapping("/messages")
	public HttpEntity<Object> createMessage(
			@RequestBody MessageDTO messageDTO,
			Principal principal
	) {
		ResponseEntity<Object> response;

		try {
			messageService.create(messageDTO.getMessage(), principal.getName(), messageDTO.getRentalId());
			response = ResponseHandler.generateResponse("message", HttpStatus.CREATED, "Message sent successfully !");
		} catch (Exception e) {
			response = ResponseHandler.generateResponse("message", HttpStatus.BAD_REQUEST, "Failed to send message");
		}

		return response;
	}
	
}
