package com.znaka.Tokens;

public class TokenStringMatch extends TokenMatch {
    public TokenStringMatch() {
        super("string_literal");
    }

    boolean check(String s){
        return s.matches("\".*?\"");
    }
}
