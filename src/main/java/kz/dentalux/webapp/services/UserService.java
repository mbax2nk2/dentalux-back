package kz.dentalux.webapp.services;

import java.security.Principal;
import java.util.List;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findAppUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public List<AppUser> findAll(Principal principal) {
        return null;
    }
}
