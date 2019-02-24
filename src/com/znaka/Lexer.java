package com.znaka;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    ArrayList<Token> tokens;
    BufferedReader br;

    public Lexer(ArrayList<Token> tokens, BufferedReader br) {
        this.tokens = tokens;
        this.br = br;
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
            final List<String> operators = Arrays.asList("+", "=", "-", "*", "/", "<", ">", "--", "++");
            
            /* splited[i].equals("=") || splited[i].equals("+") || splited[i].equals("-") || splited[i].equals("*")
                 || splited[i].equals("/") || splited[i].equals("<") || splited[i].equals(">") || splited[i].equals("--") ||
               splited[i].equals("++") */

            for(int i = 0; i < splited.length; i++) {

                Token token = new Token("", "");
               if(splited[i].matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")){
                    token.setType("symbol");
                    token.setValue(splited[i]);
                }else if(operators.contains(splited[i])){
                    token.setType("operator");
                    token.setValue(splited[i]);
                }
                else if(splited[i].matches("-?\\d+(\\.\\d+)?")) {
                   token.setType("number");
                   token.setValue(splited[i]);
               }else{
                    token.setType(splited[i]);
               }
                // TO DO  more if else with regex
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
