package com.example.perso.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // ID unique pour chaque message
    private Long id;

    // L'utilisateur qui envoie le message (relation ManyToOne)
    @ManyToOne
    @JsonIgnoreProperties
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // L'utilisateur qui reçoit le message (relation ManyToOne)
    @ManyToOne
    @JsonIgnoreProperties
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    // Contenu du message
    private String message;

    // Date et heure d’envoi du message
    private LocalDateTime timestamp = LocalDateTime.now();

    // État du message (lu ou non lu)
    private boolean isRead = false;
    
}
