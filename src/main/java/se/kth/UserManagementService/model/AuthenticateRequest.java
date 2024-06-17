package se.kth.UserManagementService.model;

public record AuthenticateRequest(String email, String password) {
}
