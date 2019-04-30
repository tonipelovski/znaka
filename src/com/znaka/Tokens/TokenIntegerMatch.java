package com.znaka.Tokens;

import java.util.regex.Pattern;

public class TokenIntegerMatch extends TokenMatch {
    public TokenIntegerMatch() {
        super("integer");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^\\d+"), s);
    }
}
