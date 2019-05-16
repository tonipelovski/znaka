package LexerTests;

import com.znaka.Exceptions.InvalidSyntax;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.TokenMatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
//TODO INdex into punctuation
//TODO Fix error messages

public class LexerTests extends  LexerTest{

    @Test
    @DisplayName("Test if the lexer can pick the right tokens from a file")
    public void TestLexerPrint() throws IOException, LexerException {
        setNewFile("LexerResources/test.txt");
        String lexerOutput = "";
        while (lexer.readLine()) {
            lexerOutput = lexerOutput.concat(lexer.tokensToString());
        }
        String expected =
                "[symbol : noType][operator : =][integer : 15]\n" +
                        "[type : int][symbol : a][operator : =][integer : 10][punc : ;]\n" +
                        "[symbol : b][operator : =][operator : -][float : 20.5][punc : ;]\n" +
                        "[type : float][symbol : abc][operator : =][operator : -][float : 20.0]\n" +
                        "[type : bool][symbol : s][operator : =][boolean : False][punc : ;]\n" +
                        "[symbol : bit_conn3ct][operator : =][integer : 1337][punc : ;]\n" +
                        "[type : char][symbol : c][operator : =][character : 'f'][punc : ;]\n" +
                        "[type : bool][symbol : a][operator : =][boolean : True][punc : ;]\n" +
                        "[type : string][symbol : alabala][operator : =][string_literal : \"kurwa\"][punc : ;]\n" +
                        "[keyword : while][punc : (][symbol : a][operator : >][integer : 8][punc : )][punc : {]\n" +
                        "[symbol : a][operator : --][punc : ;]\n" +
                        "[punc : }]\n" +
                        "[symbol : ls][punc : [][integer : 6][punc : ]][punc : ;]\n" +
                        "[symbol : a][operator : <][integer : 2][punc : ;]\n";
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
        ErrorMessageHelper("\nLine(1): fun(\n" +
                                   "            ^",
                "fun(");
        ErrorMessageHelper("\nLine(1): {\n" +
                                   "         ^",
                "{");
        ErrorMessageHelper("\nLine(1): fun{\n" +
                                   "            ^",
                "fun{");
        ErrorMessageHelper("Couldn't process line(1): ?", "?");
        ErrorMessageHelper("Couldn't process line(1): 's", "'s");
        ErrorMessageHelper("Couldn't process line(1): 'hello'", "'hello'");
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

    @Test
    public void TestGetLine() throws IOException, LexerException {
        LineTestHelper("token1");
        LineTestHelper("token8");
        LineTestHelper("int a = 20");
        LineTestHelper("weird()");

    }

}
