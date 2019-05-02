package com.znaka.Tokens.TokenMatches;

public class TokenResWordMatch extends TokenMatch {
    private static final String[] keywords =  {"if", "else", "while", "for", "elseif"};

    public TokenResWordMatch() {
        super("keyword");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenFromStringArray(keywords, s);
    }

}
