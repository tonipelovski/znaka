package com.znaka.Tokens.TokenMatches;

import java.util.regex.Pattern;

public class TokenArrayMatch extends TokenMatch {
    public TokenArrayMatch() {
        super("array");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("\\[(?:([\\w\\d\"']+,)*([\\w\\d\"']+,?))?\\]"), s);
    }
}
