package se.kth.UserManagementService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.UserManagementService.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
