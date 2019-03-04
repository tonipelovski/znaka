package com.znaka.Tokens;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TokenOperatorMatch extends TokenMatch{
    final List<String> operators = Arrays.asList("<=", ">=", "==", "!=", "--", "++", "+=", "-=", "/=", "*=",
            "+", "=", "-", "*", "/", "^", "%", "<", ">", ".");
    public TokenOperatorMatch(){
        super("operator");
    }

    @Override
    public int nextTokenEndIndex(String s) {
        for(String operator : operators){
            if(s.indexOf(operator) == 0){
                return operator.length();
            }
        }
        return 0;
    }
}
