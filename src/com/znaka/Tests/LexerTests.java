package com.znaka.Tests;

import com.znaka.Lexer;
import com.znaka.Tokens.Token;
import com.znaka.Tokens.TokenBoolMatch;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTests {
    Lexer lexer;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        URL url = LexerTests.class.getResource("../test.txt");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        lexer = new Lexer(tokens, reader);
    }

    @Test
    @DisplayName("Test if the lexer can pick the right tokens from a file")
    public void TestLexerPrint() throws IOException {

        String lexerOutput = "";
        /*try{
            lexer.readLine();
        }catch (IOException ie){
            Assertions.assertEquals("Invalid brackets!", ie.getMessage());
        }*/
        while(lexer.readLine()){
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
                "[symbol : ls][index : [6]][punc : ;]" +
                "[symbol : a][operator : <][number : 2][punc : ;]";
        Assertions.assertEquals(expected, lexerOutput);

    }

    @Test
    public void TestBrackets() throws IOException{
        Assertions.assertEquals(true, lexer.valid_brackets("()"));
        Assertions.assertEquals(true, lexer.valid_brackets("hello()"));
        Assertions.assertEquals(true, lexer.valid_brackets("bye(asd)"));
        Assertions.assertEquals(true, lexer.valid_brackets("rr{ala()}"));
        Assertions.assertEquals(false, lexer.valid_brackets("("));
        Assertions.assertEquals(false, lexer.valid_brackets("a)("));
        Assertions.assertEquals(false, lexer.valid_brackets("f{"));
        Assertions.assertEquals(true, lexer.valid_brackets("fasd"));

    }

}

