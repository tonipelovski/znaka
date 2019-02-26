package com.znaka.Tokens;

import com.znaka.Token;

public abstract class TokenMatch {
    String key;

    public TokenMatch(String key) {
        this.key = key;
    }

    public Token match(String s){
        if(check(s)){
            return new Token(key, s);
        }
        else{
            return new Token("","");
        }
    }
    abstract boolean check(String s);
}