package EvaluatorTests;

import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FunctionsTest extends EvaluatorTest {
    @Test
    public void FunctionCreation() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("func foo(string a) -> string{\n" +
                "  ret a + \" + B\"\n" +
                "}");
    }
}
