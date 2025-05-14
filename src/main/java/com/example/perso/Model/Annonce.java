package com.example.perso.Model;

import com.example.perso.Enum.TypeAnnonce;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private User user;
}