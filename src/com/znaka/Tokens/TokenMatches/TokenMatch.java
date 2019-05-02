package com.znaka.Tokens.TokenMatches;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TokenMatch {
    public String getKey() {
        return key;
    }

    private String key;


    public TokenMatch(String key) {
        this.key = key;
    }


    protected int nextTokenEndIndex(Pattern p1, String s){
        Matcher m = p1.matcher(s);
        if(!m.find()){
            return 0;
        }
        return m.end();
    }

    protected int nextTokenFromStringArray(String[] keywords, String s){
        /*for(int i=0; i < keywords.length; i++){
            keywords[i] = keywords[i].replaceAll("(.)", "\\\\$1");
        }*/
        String s1 = String.join("|", keywords);
        return nextTokenEndIndex(Pattern.compile("^(" +s1+")\\b"), s);
    }

    public abstract int nextTokenEndIndex(String s);

}