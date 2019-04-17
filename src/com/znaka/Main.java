package com.znaka;

import com.znaka.Exceptions.LexerException;
import com.znaka.Tokens.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, LexerException {
	// write your code here
        URL url = Main.class.getResource("testCode.txt");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);
        String lexerOutput = "";
        while (lexer.readLine()) {
            lexerOutput = lexerOutput.concat(lexer.tokensToString());
        }
        System.out.println(lexerOutput);
        lexer.resetInput(new BufferedReader(new FileReader(file)));
        System.out.println(lexer.tokensToString());
        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){
        }

        System.out.println(parser.printASTS());
    }
}
