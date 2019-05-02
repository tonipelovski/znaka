package com.znaka.Tokens.TokenMatches;

import java.util.regex.Pattern;

public class TokenFloatMatch extends TokenMatch {
    public TokenFloatMatch() {
        super("float");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^\\d+(\\.\\d+)"), s);
    }
}
