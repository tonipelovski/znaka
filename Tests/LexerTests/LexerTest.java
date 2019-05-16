package LexerTests;

import com.znaka.Exceptions.LexerException;
import com.znaka.Lexer;
import com.znaka.Tokens.TokenMatches.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class LexerTest {
    protected static Lexer lexer;

    @BeforeAll
    public static void setUp() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        //BufferedReader reader = new BufferedReader(new StringReader("fun(10, 15)"));
        ArrayList<Token> tokens = new ArrayList<>();
        lexer = new Lexer(tokens, reader);
    }

    protected void setNewFile(String filename) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("TestResources/" + filename),
                StandardCharsets.US_ASCII);
        lexer.resetInput(reader);
    }

    protected  <T extends Throwable> void LexerErrorHelper(String s1, Class<T> exception) {
        lexer.resetInput(new BufferedReader(new StringReader(s1)));
        Assertions.assertThrows(exception, () -> {
            lexer.readLine();
            lexer.readLine();
        });
    }
    protected void LineTestHelper(String line) throws IOException, LexerException {
        lexer.resetInput(new BufferedReader(new StringReader(line)));
        lexer.readLine();
        Assertions.assertEquals(line, lexer.getLast_line());
    }

    protected void ErrorMessageHelper(String expected, String actual) {
        lexer.resetInput(new BufferedReader(new StringReader(actual)));
        try {
            lexer.readLine();
            lexer.readLine();
        } catch (Throwable t) {
            assertEquals(expected, t.getMessage());
        }
    }

}
