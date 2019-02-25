package com.znaka.Tokens;

import com.znaka.Token;

import java.util.Arrays;
import java.util.List;
//TODO fix the compile error when adding matching to the list
public class TokenMatcher {
    public List<TokenMatch> ls;
    public TokenMatcher(){
        //System.out.println("Hello from token macher");
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



