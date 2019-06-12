package StdLibTests;

import EvaluatorTests.EvaluatorTest;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.ExitException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.StdLib.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class StdlibTests extends EvaluatorTest {
    @BeforeEach
    public void addNatives(){
        Library.addFunctions(evaluator.getFunctions());
    }

    @Test
    public void BasicTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteStringNoExceptions("println(\"heya\")");
        ExecuteStringNoExceptions("println(2)");
        ExecuteStringNoExceptions("println(10)");
    }

    @Test
    public void BasicMathTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("pow(2.0, 3.0)");
        checkLastValAndType(8.0, "double");

        ExecuteString("sqrt(4.0)");
        ExecuteAndCheckThrows("sqrt('s')", ClassCastException.class);
        checkLastValAndType(2.0, "double");

        ExecuteString("floor(3.5)");
        checkLastValAndType(3.0, "double");

        ExecuteString("ceil(3.5)");
        checkLastValAndType(4.0, "double");

        ExecuteString("rand()");
    }

    @Test
    public void AToTests() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("atof(\"11\")");
        checkLastValAndType(Float.parseFloat(String.valueOf(11.0)), "float");

        ExecuteString("atoi(\"11\")");
        checkLastValAndType(Integer.parseInt(String.valueOf(11)), "integer");
    }

    @Test
    public void FileTest() throws IOException, EvaluatorException, ParserException, LexerException, ExitException {
        setNewFile("EvaluatorResources/NativeFunctions.zk");
        evaluator.run();
        checkLastValAndType(null, "void");
    }
}
