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
        while(parser.parseLIne()){

        }
        String parserOutput = parser.printASTS();
        String expected = "\n  [assign\n" +
                "    =\n" +
                "     [charType:c]\n" +
                "     [char:c]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [intType:a]\n" +
                "     [number:10.0]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [booleanType:b]\n" +
                "     [boolean:true]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [string:s]\n" +
                "     [string_literal:\"string\"]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [var:a_var]\n" +
                "     [operator:*:[var:b_var]:[var:c_var]]]\n" +
                "  [assign\n" +
                "    =\n" +
                "     [operator:*:[var:one]:[operator:*:[var:two]:[var:three]]]\n" +
                "     [operator:*:[var:four]:[var:five]]]";
        Assertions.assertEquals(expected, parserOutput);

    }
}
