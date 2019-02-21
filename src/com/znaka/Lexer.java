package com.znaka;


import java.io.*;
import java.util.ArrayList;

public class Lexer {
    ArrayList<Token> tokens;
    BufferedReader br;

    public Lexer(ArrayList<Token> tokens, BufferedReader br) {
        this.tokens = tokens;
        this.br = br;
    }

    public void readLine(String filename) throws IOException {
            String line = br.readLine();
            String[] splited = line.split(" ");
            for(int i = 0; i < splited.length; i++) {
                Token token = new Token("", "");
                if (splited[i].equals("int")) {
                    token.setType("type");
                    token.setValue("int");
                } else if (splited[i].equals("char")) {
                    token.setType("type");
                    token.setValue("char");
                }
                // TO DO  more if else with regex
            }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void printTokens(){
        for(int i = 0; i < tokens.size(); i++){
            tokens.get(i).printer();
        }
    }
}
