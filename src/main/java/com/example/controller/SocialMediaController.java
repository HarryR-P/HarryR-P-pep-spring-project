package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account){
        if(accountService.usernameExists(account)){
            return ResponseEntity.status(409).build();
        }
        Account returnAccount = accountService.registerUser(account);
        if(returnAccount == null){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok().body(returnAccount);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account){
        Account returnAccount = accountService.loginUser(account);
        if(returnAccount == null){
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().body(returnAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message){
        Message returnMessage = messageService.postMessage(message);
        if(returnMessage == null){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok().body(returnMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok().body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Message returnMessage = messageService.getMessageById(messageId);
        return ResponseEntity.ok().body(returnMessage);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        int rowsUpdated = messageService.deleteMessageById(messageId);
        if(rowsUpdated == 0){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().body(rowsUpdated);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable int messageId, @RequestBody Message message){
        int rowsUpdated = messageService.patchMessage(messageId, message.getMessageText());
        if(rowsUpdated == 0){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok().body(rowsUpdated);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable int accountId){
        return ResponseEntity.ok().body(messageService.getAllMessagesByUser(accountId));
    }
}
