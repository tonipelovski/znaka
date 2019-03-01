package com.znaka.Tokens;

import java.util.regex.Pattern;

public class TokenPunctuationMatch extends TokenMatch {
    private static String punc =  ",;(){}";
    public TokenPunctuationMatch() {
        super("punc");
    }


    @Override
    public int nextTokenEndIndex(String s) {
       for(char c : punc.toCharArray()){
           if(s.toCharArray()[0] == c){
               return 1;
           }
       }
       return 0;
    }
}
