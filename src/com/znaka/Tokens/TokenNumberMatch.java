package com.znaka.Tokens;

import java.util.regex.Pattern;

public class TokenNumberMatch extends TokenMatch {
    public TokenNumberMatch() {
        super("number");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^-?\\d+(\\.\\d+)?"), s);
    }
}
