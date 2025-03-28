package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message)
    {
        return messageRepository.save(message);
    }

    public List<Message> retrieveMessages()
    {
        return messageRepository.findAll();
    }

    public Message retrieveMessage(Integer messageId)
    {
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent())
        {
            return message.get();
        }
        else
        {
            return null;
        }
    }

    public void deleteMessage(Integer messageId)
    {
        messageRepository.deleteById(messageId);
    }

    public void updateMessage(Integer messageId, String messageText)
    {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent())
        {
            Message message = optionalMessage.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
        }
    }

    public List<Message> findByPostedBy(Integer accountID)
    {
        List<Message> messages = messageRepository.findByPostedBy(accountID);
        return messages;
    }

}
