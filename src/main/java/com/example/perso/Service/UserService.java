package com.example.perso.Service;

import com.example.perso.Model.User;
import com.example.perso.Repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{

    private UserRepository userRepository;


    

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(p->{
                    p.setNom(user.getNom());
                    p.setPrenom(user.getPrenom());
                    p.setEmail(user.getEmail());
                    p.setPassword(user.getPassword());
                    return userRepository.save(p);
                }).orElseThrow(()->new RuntimeException("Utilisateur non trouvable"));
    }
     // Chargement d’un utilisateur à partir de l’email (utilisé par Spring Security)
    
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // On retourne un objet UserDetails utilisé par Spring Security
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            new ArrayList<>() // liste des rôles vides (à améliorer si rôles)
        );
    }
}
