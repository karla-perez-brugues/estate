package com.estate.service;

import com.estate.model.Message;
import com.estate.model.Rental;
import com.estate.model.User;
import com.estate.repository.MessageRepository;
import com.estate.controller.request.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RentalService rentalService;

    public void create(
        MessageRequest messageRequest,
        Principal principal
    ) {
        Rental rental = rentalService.getRental(messageRequest.getRentalId());
        User user = userService.findByEmail(principal.getName());
        Message message = new Message(rental, user, messageRequest.getMessage(), new Date(), new Date());

        messageRepository.save(message);
    }
}
