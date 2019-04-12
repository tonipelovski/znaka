package com.znaka.Tests.ParserTests;

import com.znaka.Exceptions.LexerException;
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
    public void genaralBasicsTest() throws IOException, LexerException {
        URL url = ParserTests.class.getResource("testParserSecond");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "[var:int:c][var:int:a][var:int:b]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:int:d]\n" +
                "     [operator:*:[var::c]:[var::b]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [operator:*:[var:int:ala]:[var:int:bala]]\n" +
                "     [var:int:balaala]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:int:a]\n" +
                "     [number:10.0]][func:int:[operator:*:[var::a]:[var::c]][var::b]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:int:a]\n" +
                "     [number:10.0]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:char:bit_conn3ct]\n" +
                "     [number:1337.0]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:char:c]\n" +
                "     [char:f]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:bool:a]\n" +
                "     [boolean:true]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:string:alabala]\n" +
                "     [string_literal:\"kurwa\"]]";
        Assertions.assertEquals(expected, parserOutput);

    }

    @Test
    public void testTypesASTS() throws IOException, LexerException {
        URL url = ParserTests.class.getResource("testTypesAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "[var:int:a][var:char:c][var:bool:b][var:string:alabala]";
        Assertions.assertEquals(expected, parserOutput);
    }

    @Test
    public void testAssignsATS() throws IOException, LexerException {
        URL url = ParserTests.class.getResource("testAssignsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "\n  [operator\n" +
                "    =\n" +
                "     [var:int:a]\n" +
                "     [number:10.0]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:int:b]\n" +
                "     [number:15.0]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:char:c]\n" +
                "     [char:c]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:bool:b]\n" +
                "     [boolean:true]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:string:alabala]\n" +
                "     [string_literal:\"alaaaa\"]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::a]\n" +
                "     [operator:*:[var::a]:[number:10.0]]][func:char:[var:int:a]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::c]\n" +
                "     [func::[var::a]]]";
        Assertions.assertEquals(expected, parserOutput);
    }

    @Test
    public void testOperationsAST() throws IOException, LexerException {
        URL url = ParserTests.class.getResource("testOperationsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "\n  [operator\n" +
                "    =\n" +
                "     [var:int:a]\n" +
                "     [operator:*:[number:10.0]:[number:15.0]]][var:int:b]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::b]\n" +
                "     [operator:*:[var::a]:[number:100.0]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [operator:*:[var::one]:[var::two]]\n" +
                "     [operator:*:[var::three]:[operator:*:[var::four]:[var::five]]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::five]\n" +
                "     [operator:*:[func::[var::a]]:[number:10.0]]]";
        Assertions.assertEquals(expected, parserOutput);
    }


    @Test
    public void testConditionsAST() throws IOException, LexerException {
        URL url = ParserTests.class.getResource("testConditionsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "\n[if:[operator:>:[var::ala]:[var::bala]]:\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::ala]\n" +
                "     [var::bala]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::c]\n" +
                "     [var::a]]]\n" +
                "[while:[operator:==:[var::ala]:[var::bala]]:[operator:+=:[var::ala]:[number:1.0]][operator:+=:[var::c]:[var::a]]]\n" +
                "[if:[operator:<:[var::ala]:[var::bala]]:\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::ala]\n" +
                "     [operator:+:[var::ala]:[number:1.0]]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::a]\n" +
                "     [operator:*:[number:4.0]:[number:10.0]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [operator:*:[var::a]:[var::b]]\n" +
                "     [operator:*:[var::c]:[var::a]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::a]\n" +
                "     [operator:*:[operator:+:[var::a]:[operator:+:[var::b]:[var::c]]]:[var::c]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::c]\n" +
                "     [func::[var::c][var::d]]]";
        Assertions.assertEquals(expected, parserOutput);
    }

    @Test
    public void testFunctionsAST() throws IOException, LexerException {
        URL url = ParserTests.class.getResource("testFunctionsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "[func::[var:int:a][var:int:b]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::a]\n" +
                "     [var::b]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::a]\n" +
                "     [operator:*:[func::[var::c][var::b]]:[number:10.0]]]";
        Assertions.assertEquals(expected, parserOutput);
    }


}
