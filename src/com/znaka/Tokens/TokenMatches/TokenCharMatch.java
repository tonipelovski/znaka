package com.znaka.Tokens.TokenMatches;

import java.util.regex.Pattern;

public class TokenCharMatch extends TokenMatch {
    public TokenCharMatch() {
        super("character");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^'.'"), s);
    }

}
