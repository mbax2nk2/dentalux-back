package kz.dentalux.webapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import kz.dentalux.webapp.models.AppUser;
import kz.dentalux.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Autowired
    JwtProperties jwtProperties;

    private UserService userService;

    private String secretKey;

    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    public String createToken(Authentication auth) {

        Claims claims = Jwts.claims().setSubject(auth.getName());
        claims.put("roles",
            auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
                Collectors.toList()));
        if (auth.getPrincipal() instanceof AppUser) {
            AppUser user = (AppUser) auth.getPrincipal();
            claims.put("company", user.getCompany().getId());
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, secretKey)//
            .compact();
    }

//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser(token));
//        return new UsernamePasswordAuthenticationToken(userDetails, "",
//            userDetails.getAuthorities());
//    }

    public UserDetails getUser(String token) {
        String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
            .getSubject();
        return userService.loadUserByUsername(username);
    }

    public String resolveToken(HttpServletRequest req) {
        if (req.getCookies() != null) {
            Cookie token = Arrays.stream(req.getCookies())
                .filter(cookie -> "token".equals(cookie.getName())).findFirst().orElse(null);
            if (token != null) {
                return token.getValue();
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
//            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
        return false;
    }

}
