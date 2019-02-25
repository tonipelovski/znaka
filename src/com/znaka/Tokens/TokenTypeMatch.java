package com.znaka.Tokens;

import com.znaka.Token;

import java.util.Arrays;
import java.util.List;

public class TokenTypeMatch implements TokenMatch {
    final List<String> types = Arrays.asList("char", "int", "bool", "string", "float");

    public TokenTypeMatch() {

    }

    public Token match(String s){
        if (types.contains(s)){
           return new Token("type", s);
        }
        return new Token("", "");
    }
}
