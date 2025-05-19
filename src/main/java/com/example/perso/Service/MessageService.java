package com.example.perso.Service;

import com.example.perso.Model.Message;
import com.example.perso.Model.User;
import com.example.perso.Repository.MessageRepository;
import com.example.perso.Repository.UserRepository;
import com.example.perso.Security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    // 🔹 Envoyer un message
    public Message sendMessage(Long receiverId, String content, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant ou invalide");
        }

        String jwt = authHeader.substring(7);
        String senderEmail = jwtService.extractUsername(jwt);

        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Expéditeur non trouvé"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(content);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);

        return messageRepository.save(message);
    }

    // 🔹 Conversation entre l’utilisateur connecté et un autre utilisateur
    public List<Message> getConversationWithUser(Long otherUserId, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant ou invalide");
        }

        String jwt = authHeader.substring(7);
        String senderEmail = jwtService.extractUsername(jwt);

        User connectedUser = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));

        User otherUser = userRepository.findById(otherUserId)
                .orElseThrow(() -> new RuntimeException("Autre utilisateur non trouvé"));

        return messageRepository.findBySenderAndReceiverOrReceiverAndSender(
                connectedUser, otherUser, connectedUser, otherUser);
    }

    // 🔹 Liste des utilisateurs avec lesquels j’ai échangé
    public List<User> getMessagedUsers(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant ou invalide");
        }

        String jwt = authHeader.substring(7);
        String senderEmail = jwtService.extractUsername(jwt);

        User me = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));

        List<Message> messages = messageRepository.findBySenderOrReceiver(me, me);

        Set<User> users = new HashSet<>();
        for (Message msg : messages) {
            if (!msg.getSender().getId().equals(me.getId())) {
                users.add(msg.getSender());
            }
            if (!msg.getReceiver().getId().equals(me.getId())) {
                users.add(msg.getReceiver());
            }
        }

        return new ArrayList<>(users);
    }
}
