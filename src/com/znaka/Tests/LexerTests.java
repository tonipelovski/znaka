package com.znaka.Tests;

import com.znaka.Lexer;
import com.znaka.Tokens.Token;
import com.znaka.Tokens.TokenBoolMatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTests {
    @Test
    @DisplayName("Test if the lexer can pick the right tokens from a file")
    public void TestLexerPrint() throws IOException {
        URL url = getClass().getResource("../test.txt");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);
        String lexerOutput = "";

        while(lexer.readLine()){
            lexerOutput = lexerOutput.concat(lexer.printTokens());
        }
        String expected =
                "[type : int][symbol : a][operator : =][number : 10][punc : ;]" +
                "[symbol : bit_conn3ct][operator : =][number : 1337][punc : ;]" +
                "[type : char][symbol : c][operator : =][character : 'f'][punc : ;]" +
                "[type : bool][symbol : a][operator : =][boolean : True][punc : ;]" +
                "[type : string][symbol : alabala][operator : =][string_literal : \"kurwa\"][punc : ;]" +
                "[keyword : while][punc : (][symbol : a][operator : >][number : 8][punc : )][punc : {]" +
                "[symbol : a][operator : --][punc : ;]" +
                "[punc : }]" +
                "[symbol : ls][index : [6]][punc : ;]";
        Assertions.assertEquals(expected, lexerOutput);
//        String a = "ASD()";
//        System.out.println(a.replaceAll("([(])", " $1"));
    }

}

