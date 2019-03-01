package com.znaka.Tokens;

public class TokenBoolMatch extends TokenMatch {

    public TokenBoolMatch() {
        super("boolean");
    }

    @Override
    boolean check(String s) {
        return s.equals("True") || s.equals("False");
    }
}
