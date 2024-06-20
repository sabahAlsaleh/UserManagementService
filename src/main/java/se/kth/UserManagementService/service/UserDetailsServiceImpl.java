package se.kth.UserManagementService.service;

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.kth.UserManagementService.config.UserDetailsImpl;
import se.kth.UserManagementService.model.User;
import se.kth.UserManagementService.repo.UserRepository;


 */
import java.util.NoSuchElementException;
/*
@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new UserDetailsImpl(user);
    }

}


 */