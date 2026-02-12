package com.javanauta.usuario.infrastructure.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnauthorizeException extends AuthenticationException {
    public UnauthorizeException(String message) {
        super(message);
    }

    public UnauthorizeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
