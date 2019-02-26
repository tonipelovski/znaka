package com.znaka.Tokens;

import java.util.Arrays;
import java.util.List;

public class TokenResWordMatch extends TokenMatch {
    final List<String> reserved = Arrays.asList("if", "else", "while", "for", "elseif");
    public TokenResWordMatch() {
        super("reserved_word");
    }

    boolean check(String s){
        return reserved.contains(s);
    }
}
