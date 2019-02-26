package com.znaka;


import com.znaka.Tokens.TokenMatcher;
import com.znaka.Tokens.TokenTypeMatch;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Lexer {
    private ArrayList<Token> tokens;
    private BufferedReader br;
    private TokenMatcher tm;

    public Lexer(ArrayList<Token> tokens, BufferedReader br) {
        this.tokens = tokens;
        this.br = br;
        this.tm = new TokenMatcher();
    }


    public boolean readLine() throws IOException {
            String line = br.readLine();
            tokens.clear();
            if(line == null){
                return false;
            }
            final List<String> addSpace = Arrays.asList("(", ")", "{", "}", ";", "--", "++", "[", "]");
            for(int i = 0; i < addSpace.size(); i++) {
                if (line.contains(addSpace.get(i))){
                    line = line.replace(addSpace.get(i), " " + addSpace.get(i) + " ");
                }
            }
            String[] splited = line.split(" ");
            Token token;
            for(int i = 0; i < splited.length; i++) {
                token = tm.tokenize(splited[i]);
                if(!token.getType().equals("")) {
                    tokens.add(token);
                }

            }
            return true;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public String printTokens(){
        String token = "";
        for(int i = 0; i < tokens.size(); i++){
            token += tokens.get(i).printer();
        }
        return token;
    }
}
