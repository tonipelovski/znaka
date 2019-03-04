package com.znaka.Tokens.TokenExceptions;

public class TokenMatchException extends Throwable{
    public TokenMatchException(String message) {
        super(message);
    }

    public TokenMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
