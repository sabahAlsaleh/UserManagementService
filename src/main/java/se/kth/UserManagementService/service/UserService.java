package se.kth.UserManagementService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.kth.UserManagementService.model.User;
import se.kth.UserManagementService.repo.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  UserRepository authRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setParentsNamesIfUnder18(user.getFatherName(), user.getMotherName());
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



    public User authenticate(String username, String password) {
        try{
            Authentication authenticationResponse = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(username, password)
                    );
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
            return getUserByUsername(username);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new NoSuchElementException("User not found: " + username);
        }
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