package EvaluatorTests;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.ExecuteOperations.VarGetOper;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.Lexer;
import com.znaka.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;
import java.net.URL;

public abstract class EvaluatorTest {
    protected Evaluator evaluator;
    protected Lexer lexer;

    @BeforeEach
    public void setUp() throws IOException {

        URL url = AssignmentTests.class.getResource("test.zk");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        evaluator = new Evaluator(parser);
        reader.close();
    }

    protected void ExecuteString(String s1) throws LexerException, ParserException, IOException, EvaluatorException {
        lexer.resetInput(new BufferedReader(new StringReader(s1)));
        evaluator.ProcessLine();
    }

    protected Variable findVar(String varName){
        return VarGetOper.findVar(evaluator.getVariables(), varName);
    }

    protected  <T> void checkLastValAndType(T val, String type){
        Assertions.assertEquals(val, evaluator.getLastReturnedValue().getVal());
        Assertions.assertEquals(type, evaluator.getLastReturnedValue().getType());
    }

    protected  <T extends Throwable> void ExecuteAndCheckThrows(String s1, Class<T> exception) {
        Assertions.assertThrows(exception, () -> {
            ExecuteString(s1);
        });
    }

    protected void ExecuteStringNoExceptions(String s1) {
        Assertions.assertDoesNotThrow(() -> {
            ExecuteString(s1);
        });
    }


}
