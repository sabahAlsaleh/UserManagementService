package se.kth.UserManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.kth.UserManagementService.model.Role;
import se.kth.UserManagementService.service.RoleService;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("getAllRoles")
    @PreAuthorize("hasRole('DataOwner')")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("getRoleById/{id}")
    @PreAuthorize("hasRole('DataOwner')")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('DataOwner')")
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('DataOwner')")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        return roleService.updateRole(id, roleDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('DataOwner')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
