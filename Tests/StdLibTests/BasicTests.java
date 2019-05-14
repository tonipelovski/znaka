package StdLibTests;

import EvaluatorTests.EvaluatorTest;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.StdLib.Library;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BasicTests extends EvaluatorTest {
    @Test
    public void BasicTest() throws LexerException, ParserException, EvaluatorException, IOException {
        Library.addFunctions(evaluator.getFunctions());
        ExecuteString("print()");
    }
}
