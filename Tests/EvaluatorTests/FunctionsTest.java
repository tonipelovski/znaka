package EvaluatorTests;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.*;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.NumberAST;
import com.znaka.ParserStructures.VarAST;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class FunctionsTest extends EvaluatorTest {
    private static HashMap<String, Function> functions = new HashMap<>();

    @BeforeAll
    public static void setAll(){
        VarAST var1 = new VarAST();
        var1.setName("var1");
        var1.setVariableType("int");
        NumberAST number = new NumberAST("20");
        AssignAST create_var = new AssignAST(var1, number);
        DefaultAST[] body = {create_var};
        Variable[] args = {new Variable("arg1", new DataVal("", "int"), false)};

        Function f1 = new Function("test1", "int", Arrays.asList(args), Arrays.asList(body));
        FunctionsTest.functions.put(f1.getName(), f1);
    }

    @Test
    public void FunctionCreationDirectTest(){
        VarAST var1 = new VarAST();
        var1.setName("var1");
        var1.setVariableType("int");
        NumberAST number = new NumberAST("20");
        AssignAST create_var = new AssignAST(var1, number);
        DefaultAST[] body = {create_var};
        Variable[] args = {new Variable("arg1", new DataVal("", "int"), false)};

        Function f1 = new Function("test1", "int", Arrays.asList(args), Arrays.asList(body));
        Assertions.assertEquals("test1", f1.getName());
        Assertions.assertEquals("int", f1.getReturn_type());
        functions.put(f1.getName(), f1);
    }

    @Test
    public void FunctionCallArgumentValidationTest() throws ArgumentException, WrongType {
        Function f1 = functions.get("test1");
        DataVal[] args = {new DataVal("6", "int")};
        DataVal[] wrong_args1 = {new DataVal('c', "char")};
        DataVal[] wrong_args2 = {new DataVal("6", "int"), new DataVal("6", "int")};
        DataVal[] wrong_args3 = {};
        FunctionCall f1_call = new FunctionCall(f1, Arrays.asList(args));
        Assertions.assertThrows(WrongType.class, () -> new FunctionCall(f1, Arrays.asList(wrong_args1))) ;
        Assertions.assertThrows(ArgumentException.class, () -> new FunctionCall(f1, Arrays.asList(wrong_args2))) ;
        Assertions.assertThrows(ArgumentException.class, () -> new FunctionCall(f1, Arrays.asList(wrong_args3))) ;

    }

    @Disabled
    @Test
    public void FunctionCreation() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("func foo(string a) -> string{\n" +
                "  ret a + \" + B\"\n" +
                "}");
    }
}
