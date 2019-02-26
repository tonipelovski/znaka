package com.znaka.Tokens;

public class TokenCharMatch extends TokenMatch {
    public TokenCharMatch() {
        super("character");
    }

    boolean check(String s){
        return s.matches("'.'");
    }
}
