package com.estate.controller;

import com.estate.controller.request.MessageRequest;
import com.estate.controller.response.GenericResponse;
import com.estate.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Operation(summary = "Create new message")
	@PostMapping("/messages")
	public HttpEntity<GenericResponse> createMessage(
		@RequestBody MessageRequest messageRequest,
		Principal principal
	) {
		return messageService.create(messageRequest, principal);
	}
	
}
