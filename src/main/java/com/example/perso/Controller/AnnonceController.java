package com.example.perso.Controller;

import com.example.perso.Model.Annonce;
import com.example.perso.Repository.AnnonceRepository;
import com.example.perso.Service.AnnonceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Annonce")
public class AnnonceController {
    private AnnonceService annonceService;


    @PostMapping("/post_annonce")
    public Annonce addAnnonce(@RequestBody Annonce annonce) {
        return annonceService.addAnnonce(annonce);
    }

    @GetMapping("/getAll")
    public List<Annonce> getAllAnnonces() {
        return annonceService.getAllAnnonces();
    };


    @DeleteMapping("/delete/{id}")
    public String deleteAnnonce( @PathVariable long id) {
        annonceService.DeleteAnnonce(id);
        return "Annonce supprimer avec succes";
    };
}
