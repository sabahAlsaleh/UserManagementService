package se.kth.UserManagementService.controller;

import jakarta.ws.rs.NotFoundException;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.kth.UserManagementService.model.AuthenticateRequest;
import se.kth.UserManagementService.model.User;
import se.kth.UserManagementService.service.UserService;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getAllUsers")
    @PreAuthorize("hasRole('DataOwner')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("home")
    public String home() {
        return "  home";
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('DataOwner')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DataOwner')")
    public User createUser(@RequestBody User user) {

        return userService.createUser(user);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('DataOwner')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('DataOwner')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("auth/login")
    public ResponseEntity<?> authenticate(@RequestBody(required = false) AuthenticateRequest authenticateRequest,
                                          Authentication authentication) {
        try {
            User user;
            if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
                user = userService.getUserByUsername(authentication.getName());
            } else if (authenticateRequest != null) {
                user = userService.authenticate(authenticateRequest.email(), authenticateRequest.password());
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect user ID or password");
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect user ID or password");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        userService.logout();
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
    }

}