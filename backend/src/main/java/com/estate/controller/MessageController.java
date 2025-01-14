package com.estate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.model.Message;
import com.estate.repository.MessageRepository;

@RestController
@RequestMapping("/api/")
public class MessageController {

	@Autowired
	private MessageRepository messageRepository;
	
	@GetMapping("/messages")
	public List<Message> getAllUsers() {
		return messageRepository.findAll();
	}
	
}
