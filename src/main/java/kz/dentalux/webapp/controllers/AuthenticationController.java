package kz.dentalux.webapp.controllers;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;
import kz.dentalux.webapp.config.JwtTokenProvider;
import kz.dentalux.webapp.dto.AuthenticationRequest;
import kz.dentalux.webapp.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository users;

    public AuthenticationController(
        AuthenticationManager authenticationManager,
        JwtTokenProvider jwtTokenProvider,
        UserRepository users) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.users = users;
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody AuthenticationRequest data) {

        try {
            String username = data.getUsername();
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, data.getPassword()));

            String token = jwtTokenProvider.createToken(username, authenticate.getAuthorities());

            Map<String, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}