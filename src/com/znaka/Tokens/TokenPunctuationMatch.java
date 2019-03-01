package com.znaka.Tokens;

public class TokenPunctuationMatch extends TokenMatch {

    public TokenPunctuationMatch() {
        super("punc");
    }

    @Override
    boolean check(String s) {
        return ",;(){}[]".contains(s);
    }
}
