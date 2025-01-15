package com.estate.controller;

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

	@PostMapping("/messages")
	public Message createMessage(@RequestBody Message message) {
		return messageRepository.save(message);
	}
	
}
