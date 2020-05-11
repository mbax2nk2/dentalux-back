package kz.dentalux.webapp.services;

import java.util.Optional;
import kz.dentalux.webapp.models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public abstract class AbstractService {

    protected UserService userService;

    public AbstractService(UserService userService) {
        this.userService = userService;
    }

    public Optional<Long> getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String name = authentication.getName();
            UserDetails userDetails = userService.loadUserByUsername(name);
            if (userDetails instanceof AppUser) {
                return Optional.of(((AppUser) userDetails).getId());
            }
        }
        return Optional.empty();
    }

}
