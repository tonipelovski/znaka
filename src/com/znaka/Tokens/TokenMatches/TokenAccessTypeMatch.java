package com.znaka.Tokens.TokenMatches;

public class TokenAccessTypeMatch extends TokenMatch {
    private String[] keywords = {"non-var", "jiw_ednorog111"};
    public TokenAccessTypeMatch() {
        super("access");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenFromStringArray(keywords, s);
    }
}
