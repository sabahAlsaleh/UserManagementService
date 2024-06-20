package se.kth.UserManagementService.service;


import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.kth.UserManagementService.model.User;
import se.kth.UserManagementService.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private  UserRepository authRepository;

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
    /*
    @Autowired
    private KeycloakSecurityManager keycloakSecurityManager;
    @Value("${realm}")
    private String realm;

    public List<UserRepresentation> getAllUser() {
        Keycloak keycloak = keycloakSecurityManager.getKeycloakInstance();
        return keycloak.realm(realm)
                .users()
                .list();
    }



     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public User createUser(User user) {
        user.setParentsNamesIfUnder18(user.getFatherName(), user.getMotherName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setInstitution(userDetails.getInstitution());
            user.setPosition(userDetails.getPosition());
            user.setRank(userDetails.getRank());
            user.setAddress(userDetails.getAddress());
            user.setBirthdate(userDetails.getBirthdate());
            user.setParentsNamesIfUnder18(userDetails.getFatherName(), userDetails.getMotherName());
            return userRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }




    public User authenticate(String email, String rawPassword) {
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found: " + email));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }



        return user;
    }


    public void logout() {
        SecurityContextHolder.clearContext();
    }


    public User getUserByUsername(String name) {
        return authRepository.getUserByUsername(name);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}