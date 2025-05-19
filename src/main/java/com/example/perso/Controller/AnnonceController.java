package com.example.perso.Controller;

import com.example.perso.Model.Annonce;
import com.example.perso.Service.AnnonceService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/Annonce")
public class AnnonceController {
    private AnnonceService annonceService;


    @PostMapping("/post_annonce")
    public ResponseEntity<Annonce> addAnnonce(@RequestBody Annonce annonce, HttpServletRequest request) {
    Annonce createdAnnonce = annonceService.addAnnonce(annonce, request);
    return ResponseEntity.ok(createdAnnonce);
}


    @GetMapping("/getAll")
    public ResponseEntity<List<Annonce>> getAllAnnonces(HttpServletRequest request) {
        List<Annonce> annonces = annonceService.getAllAnnonces(request);
        return ResponseEntity.ok(annonces);
    }

    @GetMapping("GetparUser")
    public ResponseEntity <List<Annonce>>GetAnnonparUser(HttpServletRequest request) {
        List<Annonce> annonces= annonceService.getAnnoncesByConnectedUser(request);
        return ResponseEntity.ok(annonces);
    }

    
    

    @DeleteMapping("/delete/{id}")
    public String deleteAnnonce( @PathVariable long id) {
        annonceService.deleteAnnonce(id);
        return "Annonce supprimer avec succes";
    };
}
