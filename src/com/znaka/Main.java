package com.znaka;

import com.znaka.Tokens.Token;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        URL url = Main.class.getResource("testParser");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        System.out.println("parser ");

        Parser parser = new Parser(lexer);
        while(lexer.readLine()){
            parser.parseLIne();
        }
        parser.printASTS();

    }
}
