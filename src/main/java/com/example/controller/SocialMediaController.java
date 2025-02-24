package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService)
    {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        Account optionalAccount = accountService.findByUsername(account.getUsername());
        if(optionalAccount != null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        else if(account.getUsername().isEmpty() || account.getPassword().length() < 4)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Account createdAccount;
        createdAccount = accountService.register(account);
        return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account optionalAccount;
        optionalAccount = accountService.login(account);
        if(optionalAccount == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(optionalAccount);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Account optionalAccount;
        optionalAccount = accountService.findById(message.getPostedBy());
        if(optionalAccount != null && !message.getMessageText().isEmpty() && message.getMessageText().length() <= 255)
        {
            Message createdMessage;
            createdMessage = messageService.createMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> retrieveMessages(){
        List<Message> messageList;
        messageList = messageService.retrieveMessages();

        return ResponseEntity.status(HttpStatus.OK).body(messageList);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> retrieveMessage(@PathVariable Integer messageId){
        Message optionalMessage;
        optionalMessage = messageService.retrieveMessage(messageId);
        if(optionalMessage != null)
        {
            return ResponseEntity.status(200).body(optionalMessage);
        }
        else
        {
            return ResponseEntity.status(200).body(null);
        }
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<String> patchMessage(@PathVariable Integer messageId, @RequestBody String messageText){
        if(messageService.retrieveMessage(messageId) != null && !messageText.isEmpty() && messageText.length() <= 255)
        {
            messageService.updateMessage(messageId, messageText);
            return ResponseEntity.status(200).body("1");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    

}
