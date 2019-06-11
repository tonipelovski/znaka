package com.znaka.Tokens.TokenMatches;

public class TokenTypeMatch extends TokenMatch {
    private static final String[] types = {"char", "int", "bool", "string", "float", "double", "void", "any", "arr"};

    public TokenTypeMatch() {
        super("type");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenFromStringArray(types, s);
    }
}
