package se.kth.UserManagementService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.UserManagementService.model.Role;
import se.kth.UserManagementService.repo.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> updateRole(Long id, Role roleDetails) {
        return roleRepository.findById(id).map(role -> {
            role.setName(roleDetails.getName());
            return roleRepository.save(role);
        });
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}