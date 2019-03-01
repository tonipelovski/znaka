package com.znaka.Tokens;

import java.util.regex.Pattern;

public class TokenSymbolMatch extends TokenMatch{

    public TokenSymbolMatch() {
        super("symbol");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        return nextTokenEndIndex(Pattern.compile("^[a-zA-Z_][a-zA-Z_0-9]*"), s);
    }

}
