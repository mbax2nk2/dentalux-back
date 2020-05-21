package kz.dentalux.webapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kz.dentalux.webapp.dto.AuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
        HttpServletResponse res) throws AuthenticationException {
        try {
            AuthenticationRequest creds = new ObjectMapper()
                .readValue(req.getInputStream(), AuthenticationRequest.class);

            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain,
        Authentication auth) throws IOException, ServletException {
        String token = jwtTokenProvider.createToken(auth.getName(), auth.getAuthorities());
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
//        FilterChain filterChain) throws ServletException, IOException {
////        String token = jwtTokenProvider.resolveToken(req);
////        if (token != null && jwtTokenProvider.validateToken(token)) {
////            Authentication auth = jwtTokenProvider.getAuthentication(token);
////
////            if (auth != null) {
////                SecurityContextHolder.getContext().setAuthentication(auth);
////            }
////        }
////        filterChain.doFilter(req, res);
//    }

}
