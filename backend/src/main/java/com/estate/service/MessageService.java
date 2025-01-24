package com.estate.service;

import com.estate.model.Message;
import com.estate.model.Rental;
import com.estate.model.User;
import com.estate.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            String textMessage,
            String userEmail,
            Integer rentalId
    ) {
        Rental rental = rentalService.getRental(rentalId);
        User user = userService.findByEmail(userEmail);
        Message message = new Message(rental, user, textMessage, new Date(), new Date());

        messageRepository.save(message);
    }
}
