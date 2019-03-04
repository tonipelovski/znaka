package com.znaka;

//TODO Make bracket validation

import com.znaka.Tokens.Token;
import com.znaka.Tokens.TokenExceptions.TokenMatchException;
import com.znaka.Tokens.TokenMatcher;

import java.io.*;
import java.util.*;

class LineCause extends Throwable{
    public LineCause(String message) {
        super(message);
    }
}

public class Lexer {
    private ArrayList<Token> tokens;
    private BufferedReader br;
    private TokenMatcher tm;
    private Stack<Character> st = new Stack<>();
    HashMap<Character, Character> mp;

    public Lexer(ArrayList<Token> tokens, BufferedReader br) {
        this.tokens = tokens;
        this.br = br;
        this.tm = new TokenMatcher();
        this.mp = new HashMap<>();
        mp.put('(', ')');
        mp.put('[', ']');
        mp.put('{', '}');
    }

    public boolean valid_brackets(String input) {
        char opening_bracket;
        for (char c : input.toCharArray()) {
            if (mp.containsKey(c)) {
                st.push(c);
            } else if (mp.containsValue(c)) {
                if (st.size() == 0) {
                    return false;
                }
                opening_bracket = st.pop();
                if (mp.get(opening_bracket) != c) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean readLine() throws IOException {
        String line = br.readLine();
        tokens.clear();
        if (line == null) {
            if(st.size() > 0){
                throw new IOException("Invalid brackets!", new LineCause("Not closed end bracket"));
            }
            return false;
        }

        if(!valid_brackets(line)){
            throw new IOException("Invalid brackets!", new LineCause(line));
        }
        try {
            tokens = tm.tokenizeLine(line);
        } catch (TokenMatchException te) {
            throw new IOException(te.getMessage());
        }


        return true;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public String printTokens() {
        String token = "";
        for (int i = 0; i < tokens.size(); i++) {
            token += tokens.get(i).printer();
        }
        return token;
    }
}
