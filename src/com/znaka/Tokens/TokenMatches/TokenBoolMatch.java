package com.znaka.Tokens.TokenMatches;

import java.util.regex.Pattern;

public class TokenBoolMatch extends TokenMatch {

    public TokenBoolMatch() {
        super("boolean");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^(True|False)\\b"), s);
    }
}
