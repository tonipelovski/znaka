package com.znaka;

//TODO Improve error bracket message: Line(1): f{
//                                              ^

import com.znaka.Exceptions.InvalidSyntax;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.TokenMatchException;
import com.znaka.Tokens.Token;
import com.znaka.Tokens.TokenMatcher;

import java.io.*;
import java.util.*;


class Bracket{
    private char val;
    private String line;

    public Bracket(char val, String line) {
        this.val = val;
        this.line = line;
    }

    public char getVal() {
        return val;
    }

    public String getLine() {
        return line;
    }
}

public class Lexer {
    private ArrayList<Token> tokens;
    private BufferedReader br;
    private TokenMatcher tm;
    private Stack<Bracket> st = new Stack<>();
    private HashMap<Character, Character> mp;
    private int lineNum = 0;

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
        Bracket opening_bracket;
        for (char c : input.toCharArray()) {
            if (mp.containsKey(c)) {
                st.push(new Bracket(c, LineErrorPrint(input)));
            } else if (mp.containsValue(c)) {
                if (st.size() == 0) {
                    return false;
                }
                opening_bracket = st.pop();
                if (mp.get(opening_bracket.getVal()) != c) {
                    return false;
                }
            }
        }
        return true;
    }

    private String LineErrorPrint(String input) {
        return String.format("Line(%d): %s", lineNum, input);
    }


    public boolean readLine() throws IOException, LexerException {
        String line = br.readLine();
        tokens.clear();
        if (line == null) {
            if(st.size() > 0){
                throw new InvalidSyntax(st.lastElement().getLine());
            }
            return false;
        }
        lineNum++;
        if(!valid_brackets(line)){
            throw new InvalidSyntax(LineErrorPrint(line));
        }
        try {
            tokens = tm.tokenizeLine(line);
        }catch (TokenMatchException tme){
            throw new TokenMatchException(String.format("Couldn't process line(%d): ", lineNum)  + tme.getMessage());
        }
        return true;
    }

    public void resetInput(BufferedReader bufferedReader){
        st.clear();
        lineNum = 0;
        br = bufferedReader;
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
            token += tokens.get(i).toString();
        }
        return token;
    }
}
