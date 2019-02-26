package com.znaka.Tokens;

import com.znaka.Token;

public class TokenNumberMatch extends TokenMatch {
    public TokenNumberMatch() {
        super("number");
    }

    boolean check(String s){
        return s.matches("-?\\d+(\\.\\d+)?");
    }
    /*public Token match(String s){
        if(s.matches("-?\\d+(\\.\\d+)?")){
            return new Token("number", s);
        }
        return new Token("","");
    }*/
}
