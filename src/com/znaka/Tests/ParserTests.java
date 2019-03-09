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
    public void genaralBasicsTest() throws IOException {
        URL url = Main.class.getResource("testParserSecond");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "[intType:c][intType:a][intType:b]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [intType:d]\n" +
                "     [operator:*:[var:c]:[var:b]]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [operator:*:[intType:ala]:[intType:bala]]\n" +
                "     [intType:balaala]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [intType:a]\n" +
                "     [number:10.0]]\n" +
                "[call:int:[operator:*:[var:a]:[var:c]][var:b]]\n" +
                "\n" +
                "  [assign\n" +
                "    =\n" +
                "     [intType:a]\n" +
                "     [number:10.0]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [charType:bit_conn3ct]\n" +
                "     [number:1337.0]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [charType:c]\n" +
                "     [char:f]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [booleanType:a]\n" +
                "     [boolean:true]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [string:alabala]\n" +
                "     [string_literal:\"kurwa\"]]";
        Assertions.assertEquals(expected, parserOutput);

    }
}
