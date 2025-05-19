package com.example.perso.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.perso.Model.Message;
import com.example.perso.Model.User;

import com.example.perso.Service.MessageService;


import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/Message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/SendMessage/{receiverId}")
    public ResponseEntity<Message> SendMessage(
            @PathVariable Long receiverId, // ← Utilise bien @PathVariable
            @RequestParam String content,
            HttpServletRequest request) {
        Message message = messageService.sendMessage(receiverId, content, request);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversation/{otherUserId}")
    public ResponseEntity<List<Message>> getConversation(
            @PathVariable Long otherUserId,
            HttpServletRequest request) {
        List<Message> conversation = messageService.getConversationWithUser(otherUserId, request);
        return ResponseEntity.ok(conversation);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<User>> getContacts(HttpServletRequest request) {
        List<User> contacts = messageService.getMessagedUsers(request);
        return ResponseEntity.ok(contacts);
    }
}

