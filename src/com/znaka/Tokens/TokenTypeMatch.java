package com.znaka.Tokens;

import java.util.Arrays;
import java.util.List;

public class TokenTypeMatch extends TokenMatch {
    private static final String[] types = {"char", "int", "bool", "string", "float", "double"};

    public TokenTypeMatch() {
        super("type");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenFromStringArray(types, s);
    }
}
