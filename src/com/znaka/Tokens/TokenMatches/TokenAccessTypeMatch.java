package com.znaka.Tokens.TokenMatches;

public class TokenAccessTypeMatch extends TokenMatch {
    private String[] keywords = {"let", "var"};
    public TokenAccessTypeMatch() {
        super("access");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenFromStringArray(keywords, s);
    }
}
