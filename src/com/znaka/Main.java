package com.znaka;

import com.znaka.Exceptions.LexerException;
import com.znaka.Tokens.Token;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, LexerException {
	// write your code here
        URL url = Main.class.getResource("test.txt");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        String lexerOutput = "";

        while(lexer.readLine()){
            lexerOutput = lexerOutput.concat(lexer.printTokens());
        }
        System.out.println("lexer: ");
        System.out.println(lexerOutput);
    }
}
