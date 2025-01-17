package com.estate.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.model.Message;
import com.estate.repository.MessageRepository;

@RestController
@RequestMapping("/api/")
public class MessageController {

	@Autowired
	private MessageRepository messageRepository;

	@Operation(summary = "Create new message")
	@PostMapping("/messages")
	public Message createMessage(@RequestBody Message message) {
		// TODO: return response message and status code, do not return entity
		return messageRepository.save(message);
	}
	
}
