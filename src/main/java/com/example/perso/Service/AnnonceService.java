package com.example.perso.Service;

import com.example.perso.Model.Annonce;
import com.example.perso.Repository.AnnonceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnnonceService {
    private AnnonceRepository annonceRepository;

    public Annonce addAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    };
   public List<Annonce> getAllAnnonces() {
       return annonceRepository.findAll();
   }

   public String DeleteAnnonce(Long id) {
       annonceRepository.deleteById(id);
       return "L'annonce a été supprimer ";
   };


}
