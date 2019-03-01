package com.znaka;

//TODO send only whole lines without spaces, remove all spaces and then tokenizeLine
import com.znaka.Tokens.Token;
import com.znaka.Tokens.TokenExceptions.TokenMatchException;
import com.znaka.Tokens.TokenMatcher;

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

                try{
                    tokens = tm.tokenizeLine(line);
                }catch (TokenMatchException te){
                    System.out.println(te.getMessage());
                    return false;
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
