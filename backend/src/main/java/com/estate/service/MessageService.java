package com.estate.service;

import com.estate.model.Message;
import com.estate.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void create(Message message) {
        messageRepository.save(message);
    }
}
