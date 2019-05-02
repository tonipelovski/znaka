package com.znaka.Tests.ParserTests;

import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.Lexer;
import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ParserExceptionsTests {

    @org.junit.Test(expected = ParserException.class)
    public void noRightASTException() throws IOException, ParserException, LexerException {
        URL url = ParserTests.class.getResource("testNoRightASTException");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
    }

    @org.junit.Test(expected = ParserException.class)
    public void noOperatorASTException() throws IOException, ParserException, LexerException {
        URL url = ParserTests.class.getResource("testNoOperatorASTException");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    @Test
    public void testExceptionMessage() throws Exception, LexerException, ParserException {
        expectedEx.expect(ParserException.class);
        expectedEx.expectMessage("Expected right: a = b * ;\n" +
                "                       ^");
        URL url = ParserTests.class.getResource("testNoRightASTException");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
    }

}
