package com.znaka;


import com.znaka.Tokens.TokenMatcher;
import com.znaka.Tokens.TokenTypeMatch;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//TODO put operators and types as constant properties of Lexer
//TODO Change the line replacement to avoid repetition
//TODO Change the if, elses for matching with classes to use shared behaiviour and enable easier unittesting
public class Lexer {
    ArrayList<Token> tokens;
    BufferedReader br;
    TokenMatcher tm;

    public Lexer(ArrayList<Token> tokens, BufferedReader br) {
        this.tokens = tokens;
        this.br = br;
        this.tm = new TokenMatcher();
//        tm.ls.add(new TokenTypeMatch());
        //add shit
    }


    public boolean readLine() throws IOException {
            String line = br.readLine();
            if(line == null){
                return false;
            }
            line = line.replace("(", " ( ");
            line = line.replace(")", " ) ");
            line = line.replace("}", " } ");
            line = line.replace("{", " { ");
            line = line.replace("--", " -- ");
            line = line.replace("++", " ++ ");

            line = line.replace(";", " ; ");
            String[] splited = line.split(" ");
            final List<String> operators = Arrays.asList("<=", ">=", "+", "=", "-", "*", "/", "<", ">", "--", "++");
            final List<String> types = Arrays.asList("char", "int", "bool", "string", "float");
            final List<String> reserved = Arrays.asList("if", "else", "while", "for", "elseif");

            /* splited[i].equals("=") || splited[i].equals("+") || splited[i].equals("-") || splited[i].equals("*")
                 || splited[i].equals("/") || splited[i].equals("<") || splited[i].equals(">") || splited[i].equals("--") ||
               splited[i].equals("++") */

            Token token;
            for(int i = 0; i < splited.length; i++) {

                //Token token = new Token("", "");

                token = tm.tokenize(splited[i]);


                /*if(types.contains(splited[i])){
                    token.setType("type"); // signifies type of data
                    token.setValue(splited[i]);
                }
                else if(reserved.contains(splited[i])){
                    token.setType("reserved_word"); //used to perform special operation
                    token.setValue(splited[i]);
                }
               else if(splited[i].matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")){
                    token.setType("symbol"); // used to create a new variable in memmory
                    token.setValue(splited[i]);
                }
               else if(operators.contains(splited[i])){
                    token.setType("operator"); // boolean or arithmetic operator
                    token.setValue(splited[i]);
                }
                else if(splited[i].matches("-?\\d+(\\.\\d+)?")) {
                   token.setType("number"); // matches numbers
                   token.setValue(splited[i]);
                }
                else if(splited[i].matches("\".*?\"")){
                    token.setType("string_literal"); // matches strings
                    token.setValue(splited[i]);
                }
                else if(splited[i].matches("'.'")){
                    token.setType("character"); // matches a single character
                    token.setValue(splited[i]);
                }
                else{
                    token.setType(splited[i]);
                }
                // TO DO  more if else with regex
                */
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
