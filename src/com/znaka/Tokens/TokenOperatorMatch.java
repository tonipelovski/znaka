package com.znaka.Tokens;

import java.util.Arrays;
import java.util.List;

public class TokenOperatorMatch extends TokenMatch{
    final List<String> operators = Arrays.asList("<=", ">=", "+", "=", "-", "*", "/", "^", "%", "<", ">", "--", "++");
    public TokenOperatorMatch(){
        super("operator");
    }

    boolean check(String s){
        return operators.contains(s);
    }

    /*public Token match(String s){
        if(operators.contains(s)){
            return new Token("operator", s);
        }
        return new Token("", "");
    }*/
}
