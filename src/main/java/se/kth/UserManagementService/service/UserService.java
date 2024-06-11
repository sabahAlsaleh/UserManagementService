package se.kth.UserManagementService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.UserManagementService.model.User;
import se.kth.UserManagementService.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
            user.setName(userDetails.getName());
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
}