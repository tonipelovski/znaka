package ParserTests;

import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.Lexer;
import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;
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
    public void genaralBasicsTest() throws IOException, LexerException, ParserException {
        URL url = ParserTests.class.getResource("testParserSecond");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
        String parserOutput = parser.toString();
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
                "     [integer:10]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:char:bit_conn3ct]\n" +
                "     [integer:1337]]\n" +
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
    public void testTypesASTS() throws IOException, LexerException, ParserException {
        URL url = ParserTests.class.getResource("testTypesAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
        String parserOutput = parser.toString();
        String expected = "[var:int:a][var:char:c][var:bool:b][var:string:alabala]";
        Assertions.assertEquals(expected, parserOutput);
    }

    @Test
    public void testAssignsATS() throws IOException, LexerException, ParserException {
        URL url = ParserTests.class.getResource("testAssignsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
        String parserOutput = parser.toString();
        String expected = "\n  [operator\n" +
                "    =\n" +
                "     [var:int:a]\n" +
                "     [integer:10]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var:int:b]\n" +
                "     [integer:15]]\n" +
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
                "     [operator:*:[var::a]:[integer:10]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::c]\n" +
                "     [ala:[var::a]]]";
        Assertions.assertEquals(expected, parserOutput);
    }

    @Test
    public void testOperationsAST() throws IOException, LexerException, ParserException {
        URL url = ParserTests.class.getResource("testOperationsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
        String parserOutput = parser.toString();
        String expected = "\n  [operator\n" +
                "    =\n" +
                "     [var:int:a]\n" +
                "     [operator:*:[integer:10]:[integer:15]]][var:int:b]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::b]\n" +
                "     [operator:*:[var::a]:[integer:100]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [operator:*:[var::one]:[var::two]]\n" +
                "     [operator:*:[var::three]:[operator:*:[var::four]:[var::five]]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::five]\n" +
                "     [operator:*:[ala:[var::a]]:[integer:10]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [operator:+:[operator:*:[var::a]:[var::b]]:[var::c]]\n" +
                "     [operator:*:[operator:+:[var::b]:[var::d]]:[var::c]]]";
        Assertions.assertEquals(expected, parserOutput);
    }


    @Test
    public void testConditionsAST() throws IOException, LexerException, ParserException {
        URL url = ParserTests.class.getResource("testConditionsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
        String parserOutput = parser.toString();
        String expected = "\n[if:[operator:>:[var::ala]:[var::bala]]:\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::ala]\n" +
                "     [var::bala]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::c]\n" +
                "     [var::a]]:]\n" +
                "[while:[operator:==:[var::ala]:[var::bala]]:[operator:+=:[var::ala]:[integer:1]][operator:+=:[var::c]:[var::a]]]\n" +
                "[if:[operator:<:[var::ala]:[var::bala]]:\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::ala]\n" +
                "     [operator:+:[var::ala]:[integer:1]]]:]\n" +
                "[while:[operator:==:[var::ala]:[var::bala]]:[operator:+=:[var::ala]:[integer:1]][operator:+=:[var::c]:[var::a]]]";
        Assertions.assertEquals(expected, parserOutput);
    }

    @Test
    public void testFunctionsAST() throws IOException, LexerException, ParserException {
        URL url = ParserTests.class.getResource("testFunctionsAST");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        Parser parser = new Parser(lexer);
        while(parser.parseLine()){

        }
        String parserOutput = parser.toString();
        String expected = "[ala:int:[var:int:a][var:int:b]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::a]\n" +
                "     [var::b]][return:[var::a]]]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::a]\n" +
                "     [operator:*:[ala:[var::c][var::b]]:[integer:10]]][bala:int:[var:int:a][var:int:b]\n" +
                "  [operator\n" +
                "    =\n" +
                "     [var::a]\n" +
                "     [var::b]]]";
        Assertions.assertEquals(expected, parserOutput);
    }


}
