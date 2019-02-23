package com.znaka;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTests {
    @Test
    public void TestLexerPrint() throws IOException {
        File file = new File("/home/theking/IdeaProjects/ZnakaInterpreter/src/com/znaka/test.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        while(lexer.readLine()){

        }
        String lexerOutput = lexer.printTokens();
        String actual = "[symbol : int][symbol : a][operator : =][number : 10][; : ][symbol : char][symbol : alabala][operator : =][\"kurwa\" : ][; : ][symbol : while][( : ][symbol : a][operator : >][number : 8][) : ][{ : ][symbol : a][operator : --][; : ][} : ]";
        Assertions.assertEquals(actual, lexerOutput);
    }
}
