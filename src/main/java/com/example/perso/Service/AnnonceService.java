package com.example.perso.Service;

import com.example.perso.Model.Annonce;
import com.example.perso.Model.User;
import com.example.perso.Repository.AnnonceRepository;
import com.example.perso.Repository.UserRepository;
import com.example.perso.Security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    // ✅ 1. Ajouter une annonce avec utilisateur connecté
    public Annonce addAnnonce(Annonce annonce, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token JWT manquant ou invalide");
        }

        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        annonce.setUser(user);
        return annonceRepository.save(annonce);
    }

   

    // ✅ 3. Récupérer une annonce par son ID
    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    // ✅ 4. Supprimer une annonce
    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }

    // ✅ 5. Mettre à jour une annonce
    public Annonce updateAnnonce(Long id, Annonce updatedAnnonce) {
        Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);

        if (optionalAnnonce.isPresent()) {
            Annonce existingAnnonce = optionalAnnonce.get();
            existingAnnonce.setDatArrive(updatedAnnonce.getDatArrive());
            existingAnnonce.setDatDepart(updatedAnnonce.getDatDepart());
            existingAnnonce.setKilodisponible(updatedAnnonce.getKilodisponible());
            existingAnnonce.setPrix_kilo(updatedAnnonce.getPrix_kilo());
            // Ne pas modifier le user
            return annonceRepository.save(existingAnnonce);
        } else {
            throw new RuntimeException("Annonce non trouvée avec l'ID : " + id);
        }
    }


     // ✅ 2. Récupérer toutes les annonces
     public List<Annonce> getAllAnnonces(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new RuntimeException("Token JWT manquant ou invalide ");
        }
        return annonceRepository.findAll();
    }
    
    // ✅ 6. Récupérer les annonces de l'utilisateur connecté
    public List<Annonce> getAnnoncesByConnectedUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token JWT manquant ou invalide");
        }

        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return annonceRepository.findByUser(user);
    }
}
