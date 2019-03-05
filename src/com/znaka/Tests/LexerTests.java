package com.znaka.Tests;

import com.znaka.Exceptions.InvalidSyntax;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.TokenMatchException;
import com.znaka.Lexer;
import com.znaka.Tokens.Token;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
//TODO INdex into punctuation


public class LexerTests {
    private Lexer lexer;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        URL url = LexerTests.class.getResource("test.txt");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        //BufferedReader reader = new BufferedReader(new StringReader("fun(10, 15)"));
        ArrayList<Token> tokens = new ArrayList<>();
        lexer = new Lexer(tokens, reader);
    }

    private <T extends Throwable> void LexerErrorHelper(String s1, Class<T> exception) {
        lexer.resetInput(new BufferedReader(new StringReader(s1)));
        Assertions.assertThrows(exception, () -> {
            lexer.readLine();
            lexer.readLine();
        });
    }

    private void ErrorMessageHelper(String expected, String actual) {
        lexer.resetInput(new BufferedReader(new StringReader(actual)));
        try {
            lexer.readLine();
            lexer.readLine();
        } catch (Throwable t) {
            assertEquals(expected, t.getMessage());
        }
    }

    @Test
    @DisplayName("Test if the lexer can pick the right tokens from a file")
    public void TestLexerPrint() throws IOException, LexerException {

        String lexerOutput = "";
        while (lexer.readLine()) {
            lexerOutput = lexerOutput.concat(lexer.printTokens());
        }
        String expected =
                "[symbol : noType][operator : =][number : 15]" +
                        "[type : int][symbol : a][operator : =][number : 10][punc : ;]" +
                        "[type : bool][symbol : s][operator : =][boolean : False][punc : ;]" +
                        "[symbol : bit_conn3ct][operator : =][number : 1337][punc : ;]" +
                        "[type : char][symbol : c][operator : =][character : 'f'][punc : ;]" +
                        "[type : bool][symbol : a][operator : =][boolean : True][punc : ;]" +
                        "[type : string][symbol : alabala][operator : =][string_literal : \"kurwa\"][punc : ;]" +
                        "[keyword : while][punc : (][symbol : a][operator : >][number : 8][punc : )][punc : {]" +
                        "[symbol : a][operator : --][punc : ;]" +
                        "[punc : }]" +
                        "[symbol : ls][punc : [][number : 6][punc : ]][punc : ;]" +
                        "[symbol : a][operator : <][number : 2][punc : ;]";
        Assertions.assertEquals(expected, lexerOutput);

    }

    @Test
    public void TestLexerErrors() {
        LexerErrorHelper("fun(", InvalidSyntax.class);
        LexerErrorHelper("?", TokenMatchException.class);
        LexerErrorHelper("\\", TokenMatchException.class);
    }


    @Test
    public void TestLexerErrorMessages() {
        ErrorMessageHelper("Line(1): fun(", "fun(");
        ErrorMessageHelper("Line(1): fun{", "fun{");
        ErrorMessageHelper("Line(1): {", "{");
        ErrorMessageHelper("Couldn't process line(1): ?", "?");
        ErrorMessageHelper("Couldn't process line(1): 's", "'s");
    }

    @Test
    public void TestBrackets() throws IOException { // Does not find unclosed openes for wrong - 'a(' 'b['...
        Assertions.assertTrue(lexer.valid_brackets("()"));
        Assertions.assertTrue(lexer.valid_brackets("hello()"));
        Assertions.assertTrue(lexer.valid_brackets("bye(asd)"));
        Assertions.assertTrue(lexer.valid_brackets("rr{ala(a[])}"));
        Assertions.assertTrue(lexer.valid_brackets("fasd"));

        Assertions.assertFalse(lexer.valid_brackets(")"));
        Assertions.assertFalse(lexer.valid_brackets("a)("));
        Assertions.assertFalse(lexer.valid_brackets("f{]"));
//        Assertions.assertFalse(lexer.valid_brackets("f{"));


    }

}

