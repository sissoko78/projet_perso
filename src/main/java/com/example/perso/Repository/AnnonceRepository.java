package com.example.perso.Repository;

import com.example.perso.Model.Annonce;
import com.example.perso.Model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByUser(User user);
}
