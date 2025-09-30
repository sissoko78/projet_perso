package com.example.perso.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOffre;
    private Date datDepart;
    private Date datArrive;
    private String paysdepart;
    private String paysarrive;
    private String kilodisponible;
    private String prix_kilo;
    private String moyentransport;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"annonces", "sentMessages", "receivedMessages", "password"})
    private User user;
}