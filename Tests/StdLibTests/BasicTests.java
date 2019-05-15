package StdLibTests;

import EvaluatorTests.EvaluatorTest;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.StdLib.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BasicTests extends EvaluatorTest {
    @BeforeEach
    public void addNatives(){
        Library.addFunctions(evaluator.getFunctions());
    }

    @Test
    public void BasicTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("println(\"heya\")");
    }

    @Test
    public void FileTest() throws IOException, EvaluatorException, ParserException, LexerException {
        setNewFile("EvaluatorResources/NativeFunctions.zk");
        evaluator.run();
        checkLastValAndType(null, "void");
    }
}
