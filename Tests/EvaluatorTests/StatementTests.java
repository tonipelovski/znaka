package EvaluatorTests;

import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class StatementTests extends EvaluatorTest {
    @Test
    public void IfConditionTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 10");
        ExecuteString("int b = 5");

        ExecuteString("if(b < a){ b = a }");
        ExecuteString("int c = b");

        Assertions.assertEquals(10, evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void IfElseConditionTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 10");
        ExecuteString("int b = 5");

        ExecuteString("if(b > a){ b = a }else{ b = 100}");
        ExecuteString("int c = b");

        Assertions.assertEquals(100, evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void LoopTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 10");
        ExecuteString("int b = 5");

        ExecuteString("while(b < a){ b = b + 1}");
        ExecuteString("int c = b");

        Assertions.assertEquals(10, evaluator.getLastReturnedValue().getVal());
    }
}
