package com.znaka;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        File file = new File("/home/theking/IdeaProjects/ZnakaInterpreter/src/com/znaka/test.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        while(lexer.readLine()){

        }
        System.out.println("lexer: ");

        String tokens_output = lexer.printTokens();
        System.out.println(tokens_output);
    }
}
