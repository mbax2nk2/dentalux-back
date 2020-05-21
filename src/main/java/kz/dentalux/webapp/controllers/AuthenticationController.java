package kz.dentalux.webapp.controllers;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;
import kz.dentalux.webapp.config.JwtTokenProvider;
import kz.dentalux.webapp.dto.AuthenticationRequest;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.repositories.UserRepository;
import kz.dentalux.webapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    public AuthenticationController(
        AuthenticationManager authenticationManager,
        JwtTokenProvider jwtTokenProvider,
        UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody AuthenticationRequest data) {

        try {
            String username = data.getUsername();
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, data.getPassword()));

            String token = jwtTokenProvider.createToken(username, authenticate.getAuthorities());

            Map<String, Object> model = new HashMap<>();
            model.put("token", token);
            if (authenticate.getDetails() instanceof AppUser) {
                AppUser user = (AppUser) authenticate.getDetails();
                model.put("name", String.format("%s %s",
                    StringUtils.isEmpty(user.getLastName()) ? "" : user.getLastName(),
                    StringUtils.isEmpty(user.getFirstName()) ? "" : user.getFirstName()));
            } else {
                model.put("name", authenticate.getName());
            }

            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}