package kz.dentalux.webapp.exceptions;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = -761503632186596342L;

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}

