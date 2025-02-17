package com.estate.service;

import com.estate.controller.response.GenericResponse;
import com.estate.model.Message;
import com.estate.model.Rental;
import com.estate.model.User;
import com.estate.repository.MessageRepository;
import com.estate.controller.request.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<GenericResponse> create(
        MessageRequest messageRequest,
        Principal principal
    ) {
        GenericResponse messageResponse = new GenericResponse();
        HttpStatus httpStatus;

        try {
            Rental rental = rentalService.getRental(messageRequest.getRentalId());
            User user = userService.findByEmail(principal.getName());
            Message message = new Message(rental, user, messageRequest.getMessage(), new Date(), new Date());

            messageRepository.save(message);

            messageResponse.setMessage("Message sent successfully !");
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            messageResponse.setMessage("Failed to send message");
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity
            .status(httpStatus)
            .body(messageResponse);
    }
}
