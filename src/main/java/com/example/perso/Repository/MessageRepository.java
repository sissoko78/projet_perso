package com.example.perso.Repository;

import com.example.perso.Model.Message;
import com.example.perso.Model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository <Message, Long>{
     // Récupérer tous les messages entre deux utilisateurs, dans les deux sens
     List<Message> findBySenderAndReceiverOrReceiverAndSender(
        User sender1, User receiver1, User sender2, User receiver2
    );

    // Récupérer tous les messages où l'utilisateur est soit l'expéditeur soit le destinataire
    List<Message> findBySenderOrReceiver(User sender, User receiver);

}