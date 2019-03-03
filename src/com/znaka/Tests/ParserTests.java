package com.znaka.Tests;

import com.znaka.Lexer;
import com.znaka.Main;
import com.znaka.Parser;
import com.znaka.Tokens.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ParserTests {
    @Test
    public void testOutput() throws IOException {
        URL url = Main.class.getResource("testParser");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(lexer.readLine()){
            parser.parseLIne();
        }
        String parserOutput = parser.printASTS();
        String expected = "[var:variable]" +
                "[assign:=:null:null]" +
                "[var:a_var]" +
                "[var:ala]" +
                "[operator:*:null:null]" +
                "[var:bala]" +
                "[assign:=:null:null]" +
                "[var:alabala]";
        Assertions.assertEquals(expected, parserOutput);

    }
}
