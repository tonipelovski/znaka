package com.znaka.Tokens;

import java.util.regex.Pattern;

public class TokenIndexMatch extends TokenMatch { // Deprecated

    public TokenIndexMatch() {
        super("index");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^\\[\\d*\\]"), s);
    }

    /*boolean check(String s) {
        return s.matches("\\[\\d*\\]"); // matches [], [1], [12]....
    }*/
}
