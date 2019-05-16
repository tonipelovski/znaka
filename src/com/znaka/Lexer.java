package com.znaka;

//TODO Improve error bracket message: Line(1): f{
//                                              ^

import com.znaka.Exceptions.InvalidSyntax;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.TokenMatchException;
import com.znaka.Tokens.TokenMatcher;
import com.znaka.Tokens.TokenMatches.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


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
    private Stack<Character> st = new Stack<>();
    private HashMap<Character, Character> mp;
    private int lineNum = 1;
    private Bracket last_bracket;
    private String last_line;

    public Lexer(ArrayList<Token> tokens, BufferedReader br) {
        this.tokens = tokens;
        this.br = br;
        this.tm = new TokenMatcher();
        this.mp = new HashMap<>();
        mp.put('(', ')');
        mp.put('[', ']');
        mp.put('{', '}');
    }

    public Lexer(BufferedReader br) {
        this.tokens = new ArrayList<>();
        this.br = br;
        this.tm = new TokenMatcher();
        this.mp = new HashMap<>();
        mp.put('(', ')');
        mp.put('[', ']');
        mp.put('{', '}');
    }

    public int getLineNum() {
        return lineNum;
    }

    public boolean valid_brackets(String input) {
        char opening_bracket;
        int i=0;
        for (char c : input.toCharArray()) {
            last_bracket = new Bracket(c, LineErrorPrint(input, i));
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
            i++;
        }
        return true;
    }

    private String LineErrorPrint(String input, int bracket_error_i) {
//        return String.format("Line(%d): %s", lineNum, input);
        String s = String.format("Line(%d): ", lineNum);
        StringBuilder outputBuffer = new StringBuilder(s.length());
        for (int i = 0; i < s.length()+bracket_error_i; i++){
            outputBuffer.append(" ");
        }
        return s + input + "\n" + outputBuffer.toString() + "^";
    }


    public String getLast_line() {
        return last_line;
    }

    public boolean readLine() throws IOException, LexerException {

        String line;
        while((line = br.readLine()) != null && line.isEmpty()){}
        last_line = line;
        tokens.clear();
        if (line == null) {
            if(st.size() > 0){
                throw new InvalidSyntax(last_bracket.getLine());

            }
            br.close();
            return false;
        }

        if(!valid_brackets(line)){
            throw new InvalidSyntax(last_bracket.getLine());
        }
        try {
            tokens = tm.tokenizeLine(line);
        }catch (TokenMatchException tme){
            throw new TokenMatchException(String.format("Couldn't process line(%d): ", lineNum)  + tme.getMessage());
        }
        lineNum++;
        return true;
    }

    public void resetInput(BufferedReader bufferedReader){
        st.clear();
        lineNum = 1;
        br = bufferedReader;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public String tokensToString() {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            token.append(tokens.get(i).toString());
        }
        return token.toString() +'\n';
    }
}
