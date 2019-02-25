package com.znaka;

import com.znaka.Tokens.TokenMatcher;
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
        URL url = getClass().getResource("test.txt");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        while(lexer.readLine()){

        }
        String lexerOutput = lexer.printTokens();
        String actual =
                "[type : int][symbol : a][operator : =][number : 10][; : ]" +
                "[symbol : bit_conn3ct][operator : =][number : 1337][; : ]" +
                "[type : char][symbol : c][operator : =][character : 'f'][; : ]" +
                "[type : string][symbol : alabala][operator : =][string_literal : \"kurwa\"][; : ]" +
                "[reserved_word : while][( : ][symbol : a][operator : >][number : 8][) : ][{ : ]" +
                "[symbol : a][operator : --][; : ]" +
                "[} : ]";
        Assertions.assertEquals(actual, lexerOutput);
//        String a = "ASD()";
//        System.out.println(a.replaceAll("([(])", " $1"));
    }

}
