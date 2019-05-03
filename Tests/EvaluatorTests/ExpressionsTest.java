package EvaluatorTests;

import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ExpressionsTest extends EvaluatorTest{

    @Test
    public void TwoOperators() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("20 + 5");
        Assertions.assertEquals(25, evaluator.getLastReturnedValue().getVal());
        ExecuteString("10 * 5");
        Assertions.assertEquals(50, evaluator.getLastReturnedValue().getVal());
        ExecuteString("20 / 5");
        Assertions.assertEquals(4, evaluator.getLastReturnedValue().getVal());
        ExecuteString("20 - 5");
        Assertions.assertEquals(15, evaluator.getLastReturnedValue().getVal());
        ExecuteString("20.0 - 5.0");
        Assertions.assertEquals(Float.parseFloat("15.0"), evaluator.getLastReturnedValue().getVal());
        ExecuteString("2.5 - 0.5");
        Assertions.assertEquals(Float.parseFloat("2.0"), evaluator.getLastReturnedValue().getVal());
        ExecuteString("20 - 5.0");
        Assertions.assertEquals(Float.parseFloat("15.0"), evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void MoreThanTwoOperators() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("20 + 5 + 3");
        Assertions.assertEquals(28, evaluator.getLastReturnedValue().getVal());
        ExecuteString("20 + 5 + 2.5");
        Assertions.assertEquals(Float.parseFloat("27.5"), evaluator.getLastReturnedValue().getVal());
        ExecuteString("20 + 5 + 3.7");
        Assertions.assertEquals(Float.parseFloat("28.7"), evaluator.getLastReturnedValue().getVal());
        ExecuteString("20 + 5 + 3.7 + 10");
        Assertions.assertEquals(Float.parseFloat("38.7"), evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void OperatorsWithVariables() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("a = 20");
        ExecuteString("-a + -5 + 3");
        Assertions.assertEquals(-22, evaluator.getLastReturnedValue().getVal());
        ExecuteString("5 + a + a");
        Assertions.assertEquals(45, evaluator.getLastReturnedValue().getVal());
        ExecuteString("b = 20");
        ExecuteString("a + b");
        Assertions.assertEquals(40, evaluator.getLastReturnedValue().getVal());
        ExecuteString("a - b");
        Assertions.assertEquals(0, evaluator.getLastReturnedValue().getVal());
        ExecuteString("int c = -20");
        ExecuteString("a + c");
        Assertions.assertEquals(0, evaluator.getLastReturnedValue().getVal());
        ExecuteString("double a = 25.67");
        ExecuteString("a + 10");
        Assertions.assertEquals(Double.parseDouble("35.67"), evaluator.getLastReturnedValue().getVal());
        ExecuteString("float a = 25.67");
        ExecuteString("a + 10 + 5 + -1");
        Assertions.assertEquals(Float.parseFloat("39.67"), evaluator.getLastReturnedValue().getVal());
        ExecuteString("int a = 10");
        ExecuteString("-a");
        Assertions.assertEquals(-10, evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void CompareOperators() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 20");
        ExecuteString("int b = 30");
        ExecuteString("a > b");
        Assertions.assertEquals(false, evaluator.getLastReturnedValue().getVal());
        ExecuteString("a < b");
        Assertions.assertEquals(true, evaluator.getLastReturnedValue().getVal());
        ExecuteString("(a + b) > (a - b)");
        Assertions.assertEquals(true, evaluator.getLastReturnedValue().getVal());
        ExecuteString("(a - b) > (a + b)");
        Assertions.assertEquals(false, evaluator.getLastReturnedValue().getVal());
    }
    @Test
    public void BinaryOperators() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 20");
        ExecuteString("bool b = False");
        ExecuteString("bool c = True");
        ExecuteString("a++");
        Assertions.assertEquals(21, evaluator.getLastReturnedValue().getVal());
        ExecuteString("a--");
        Assertions.assertEquals(19, evaluator.getLastReturnedValue().getVal());
        ExecuteString("!a");
        Assertions.assertEquals(true, evaluator.getLastReturnedValue().getVal());
        ExecuteString("!c");
        Assertions.assertEquals(false, evaluator.getLastReturnedValue().getVal());

    }
}
