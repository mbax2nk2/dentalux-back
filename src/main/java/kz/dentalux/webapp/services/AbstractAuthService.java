package kz.dentalux.webapp.services;

import java.util.Optional;
import kz.dentalux.webapp.models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public abstract class AbstractAuthService {
    protected Optional<AppUser> loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AppUser) {
            return Optional.ofNullable((AppUser) authentication.getPrincipal());
        }

        log.warn("User not found");
        return Optional.empty();
    }
}
