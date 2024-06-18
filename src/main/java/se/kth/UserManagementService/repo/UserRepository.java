package se.kth.UserManagementService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.UserManagementService.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUsername(String username);

    Optional<Object> findByEmail(String email);
}
