package com.znaka.Tokens;

import com.znaka.Exceptions.TokenMatchException;
import com.znaka.Tokens.TokenMatches.*;

import java.util.ArrayList;
import java.util.List;

public class TokenMatcher {
    public List<TokenMatch> ls;
    private String inp_string;

    public TokenMatcher() {
        //System.out.println("Hello from token matcher");
        this.ls = new ArrayList<>();
        ls.add(new TokenPunctuationMatch());
        //ls.add(new TokenIndexMatch());
        ls.add(new TokenTypeMatch());
        ls.add(new TokenAccessTypeMatch());
        ls.add(new TokenResWordMatch());
        ls.add(new TokenBoolMatch());
        ls.add(new TokenSymbolMatch());
//        ls.add(new TokenNumberMatch());
        ls.add(new TokenArrayMatch());
        ls.add(new TokenOperatorMatch());
        ls.add(new TokenFloatMatch());
        ls.add(new TokenIntegerMatch());
        ls.add(new TokenCharMatch());
        ls.add(new TokenStringMatch());
    }

    private Token yoinkToken() {
        int ind;
        Token token;
        for(TokenMatch tm : ls){
            ind = tm.nextTokenEndIndex(inp_string);
            if(ind > 0){
                token = new Token(tm.getKey(), inp_string.substring(0, ind));
                inp_string = inp_string.substring(ind);
                inp_string = inp_string.trim();
                return token;
            }
        }
        return new Token("","");
    }

    public ArrayList<Token> tokenizeLine(String s) throws TokenMatchException {
        Token token;
        ArrayList<Token> tokens = new ArrayList<Token>();
        inp_string = s.trim();
        while(inp_string.length() > 0){
            token = yoinkToken();
            if(token.empty()){
                throw new TokenMatchException(s);
            }
            else{
                tokens.add(token);
            }

            if(inp_string.trim().isEmpty()){
                return tokens;
            }


        }
        return tokens;
     }




}



