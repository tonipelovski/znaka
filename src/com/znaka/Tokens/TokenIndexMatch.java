package com.znaka.Tokens;

public class TokenIndexMatch extends TokenMatch {

    public TokenIndexMatch() {
        super("index");
    }

    @Override
    boolean check(String s) {
        return s.matches("\\[\\d*\\]"); // matches [], [1], [12]....
    }
}
