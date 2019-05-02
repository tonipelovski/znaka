package com.znaka.Tokens.TokenMatches;

import java.util.regex.Pattern;

public class TokenNumberMatch extends TokenMatch { // deprecated since creation of integer and float
    public TokenNumberMatch() {
        super("number");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^-?\\d+(\\.\\d+)?"), s);
    }
}
