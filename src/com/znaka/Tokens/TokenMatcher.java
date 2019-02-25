package com.znaka.Tokens;

import com.znaka.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class TokenMatcher {
    public List<TokenMatch> ls;

    public TokenMatcher(){
        //System.out.println("Hello from token macher");
        this.ls = new ArrayList<>();
        ls.add(new TokenTypeMatch());

    }

    public Token tokenize(String s) {
        Token token;
        for(TokenMatch tm : ls){
            token = tm.match(s);
            if(!token.empty()){
                return token;
            }
        }
        return new Token("", "");
     }


}



