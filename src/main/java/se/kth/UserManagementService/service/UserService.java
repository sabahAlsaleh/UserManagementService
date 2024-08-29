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
import se.kth.UserManagementService.model.Role;
import se.kth.UserManagementService.model.User;
import se.kth.UserManagementService.repo.RoleRepository;
import se.kth.UserManagementService.repo.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private  UserRepository authRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

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

            user.getRoles().clear();
            Set<Role> newRoles = userDetails.getRoles();
            for (Role role : newRoles) {

                Role existingRole = roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RuntimeException("Role not found with id: " + role.getId()));
                user.getRoles().add(existingRole);
            }
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