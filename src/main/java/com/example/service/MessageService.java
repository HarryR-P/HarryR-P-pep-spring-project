package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message postMessage(Message message){
        if(message.getMessageText() == null || message.getMessageText().length() > 255 || message.getMessageText().length() == 0){
            return null;
        }
        if(message.getPostedBy() == null){
            return null;
        }
        Optional<Account> accountOptional = accountRepository.findById(message.getPostedBy());
        if(accountOptional.isEmpty()){
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id){
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isEmpty()){
            return null;
        }
        return messageOptional.get();
    }

    public int deleteMessageById(int id){
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isEmpty()){
            return 0;
        }
        messageRepository.deleteById(id);
        return 1;
    }

    public int patchMessage(int id, String messageText){
        if(messageText == null || messageText.length() > 255 || messageText.length() == 0){
            return 0;
        }
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isEmpty()){
            return 0;
        }
        Message message = messageOptional.get();
        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getAllMessagesByUser(int userId){
        return messageRepository.findByPostedBy(userId);
    }
}
