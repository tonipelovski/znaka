package com.znaka.Tokens;

import com.znaka.Token;

public class TokenSymbolMatch extends TokenMatch{

    public TokenSymbolMatch() {
        super("symbol");
    }

    boolean check(String s){
       return s.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$");
    }
    /*public Token match(String s){
        if(s.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")){
            return new Token("symbol", s);
        }
        return new Token("","");
    }*/
}
