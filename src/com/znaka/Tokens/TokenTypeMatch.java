package com.znaka.Tokens;

import com.znaka.Token;

import java.util.Arrays;
import java.util.List;

public class TokenTypeMatch extends TokenMatch {
    final List<String> types = Arrays.asList("char", "int", "bool", "string", "float");

    public TokenTypeMatch() {
        super("type");
    }

    boolean check(String s){
        return types.contains(s);
    }

    /*public Token match(String s){
        if (types.contains(s)){
           return new Token("type", s);
        }
        return new Token("", "");
    }*/
}
