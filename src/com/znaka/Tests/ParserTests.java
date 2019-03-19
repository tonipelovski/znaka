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
                "     [number:10.0]][func:int:[operator:*:[var:a]:[var:c]][var:b]]" +
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

    @Test
    public void testTypesASTS() throws IOException{
        URL url = Main.class.getResource("testTypesAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "[intType:a][charType:c][booleanType:b][string:alabala]";
        Assertions.assertEquals(expected, parserOutput);
    }

    @Test
    public void testAssignsATS() throws IOException{
        URL url = Main.class.getResource("testAssignsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "\n  [assign\n" +
                "    =\n" +
                "     [intType:a]\n" +
                "     [number:10.0]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [intType:b]\n" +
                "     [number:15.0]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [charType:c]\n" +
                "     [char:c]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [booleanType:b]\n" +
                "     [boolean:true]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [string:alabala]\n" +
                "     [string_literal:\"alaaaa\"]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [var:a]\n" +
                "     [operator:*:[var:a]:[number:10.0]]][func:char:[intType:a]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [var:c]\n" +
                "     [func::[var:a]]]";
        Assertions.assertEquals(expected, parserOutput);
    }

    @Test
    public void testOperationsATS() throws IOException{
        URL url = Main.class.getResource("testOperationsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "\n  [assign\n" +
                "    =\n" +
                "     [intType:a]\n" +
                "     [operator:*:[number:10.0]:[number:15.0]]][intType:b]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [var:b]\n" +
                "     [operator:*:[var:a]:[number:100.0]]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [operator:*:[var:one]:[var:two]]\n" +
                "     [operator:*:[var:three]:[operator:*:[var:four]:[var:five]]]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [var:five]\n" +
                "     [operator:*:[func::[var:a]]:[number:10.0]]]";
        Assertions.assertEquals(expected, parserOutput);
    }


    @Test
    public void testConditionsATS() throws IOException{
        URL url = Main.class.getResource("testConditionsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "[intType:a][intType:b]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [var:a]\n" +
                "     [operator:*:[var:a]:[operator:+:[var:c]:[operator:+:[var:b]:[var:d]]]]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [operator:+:[var:a]:[var:b]]\n" +
                "     [var:c]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [var:c]\n" +
                "     [func::[var:d][var:c]]]\n" +
                "[if:[operator:>:[var:ala]:[var:bala]]::]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [var:ala]\n" +
                "     [var:bala]]";
        Assertions.assertEquals(expected, parserOutput);
    }

}
