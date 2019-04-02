package com.znaka;

import com.znaka.Exceptions.LexerException;
import com.znaka.Tokens.Token;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, LexerException {
	// write your code here
        URL url = Main.class.getResource("testFunctionsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        String lexerOutput = "";
        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){
        }
        System.out.println(parser.printASTS());
    }
}
