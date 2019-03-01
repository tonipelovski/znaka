package com.znaka.Tokens;

import java.util.regex.Pattern;

public class TokenStringMatch extends TokenMatch {
    public TokenStringMatch() {
        super("string_literal");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^\".*?\""), s);
    }


}
