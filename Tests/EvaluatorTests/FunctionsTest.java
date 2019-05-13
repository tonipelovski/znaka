package EvaluatorTests;


import Mocks.FunctionASTDefMock;
import Mocks.FunctionCallAstMock;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.*;
import com.znaka.ParserStructures.*;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.Expression.ExpressionAST;
import com.znaka.ParserStructures.Expression.FunctionCallAST;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

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
        Variable[] args = {new Variable<>("arg1", new DataVal<>("", "int"), false)};

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
        Variable[] args = {new Variable<String>("arg1", new DataVal<>("", "int"), false)};

        Function f1 = new Function("test1", "int", Arrays.asList(args), Arrays.asList(body));
        Assertions.assertEquals("test1", f1.getName());
        Assertions.assertEquals("int", f1.getReturn_type());
        functions.put(f1.getName(), f1);
    }

    @Test
    public void FunctionCallArgumentValidationTest() throws EvaluatorException, LexerException, ParserException, IOException {
        Function f1 = functions.get("test1");
        DataVal[] args = {new DataVal<>("6", "int")};
        DataVal[] wrong_args1 = {new DataVal<>('c', "char")};
        DataVal[] wrong_args2 = {new DataVal<>("6", "int"), new DataVal<>("6", "int")};
        DataVal[] wrong_args3 = {};
        Assertions.assertDoesNotThrow(() -> new FunctionCall(f1, Arrays.asList(args)));
        Assertions.assertThrows(WrongType.class, () -> new FunctionCall(f1, Arrays.asList(wrong_args1))) ;
        Assertions.assertThrows(ArgumentException.class, () -> new FunctionCall(f1, Arrays.asList(wrong_args2))) ;
        Assertions.assertThrows(ArgumentException.class, () -> new FunctionCall(f1, Arrays.asList(wrong_args3))) ;

    }

    @Disabled
    @Test
    public void CreateFunctionFromASTAndRun() throws EvaluatorException {
        VarAST var1 = new VarAST();
        var1.setVariableType("string");
        var1.setName("var1");

        Stack<DefaultAST> args = new Stack<>();
        VarAST arg1 = new VarAST();
        arg1.setVariableType("int");
        arg1.setName("var1");
        args.push(arg1);

        NumberAST number_10 = new NumberAST("10");
        number_10.setNumberType("integer");
        Stack<DefaultAST> body_code = new Stack<>();
        body_code.push(new AssignAST(var1, new StringAST("Hello")));
        body_code.push(number_10);

        MainAST body= new MainAST(body_code);

        FunctionDefAST fn = new FunctionDefAST("int", args, body);
        fn.setName("test_func1");
        // Function ast created
        evaluator.ExecLine(fn);
        Assertions.assertEquals(1, evaluator.getFunctions().size());
        Stack<DefaultAST> args2 = new Stack<>();
        args2.push(number_10);
        FunctionCallAST fn_call = new FunctionCallAST(null, args2, null);
        fn_call.setName("test_func1");
        evaluator.ExecLine(fn_call);
        checkLastValAndType(10, "int");
    }

    @Test
    public void FuncCreationFromASTAndCallMockTest() throws EvaluatorException, ParserException, IOException, LexerException {


        List<VarAST> args = new ArrayList<>();
        args.add((VarAST)getAstFromString("int var1"));

        List<DefaultAST> body_code = new ArrayList<>();
        body_code.add(getAstFromString("string var1 = \"Hello\""));
        body_code.add(getAstFromString("10"));


        FunctionASTDefMock fn = new FunctionASTDefMock("test_func1", "int", args, body_code);
        evaluator.ExecLine(fn);
        Assertions.assertEquals(1, evaluator.getFunctions().size());

        List<ExpressionAST> callArgs = new ArrayList<>();
        callArgs.add((ExpressionAST) getAstFromString("10"));

        FunctionCallAstMock fn_call = new FunctionCallAstMock(fn.getName(), callArgs);
        evaluator.ExecLine(fn_call);
        checkLastValAndType(10, "int");
    }

    @Test
    public void FuncWrongRetTypeException() throws ParserException, IOException, LexerException, EvaluatorException {
        List<VarAST> args = new ArrayList<>();
        args.add((VarAST)getAstFromString("int var1"));

        List<DefaultAST> body_code = new ArrayList<>();
        body_code.add(getAstFromString("string var1 = \"Hello\""));
        body_code.add(getAstFromString("\"Beak it all\""));


        FunctionASTDefMock fn = new FunctionASTDefMock("test_func1", "int", args, body_code);
        evaluator.ExecLine(fn);

        List<ExpressionAST> callArgs = new ArrayList<>();
        callArgs.add((ExpressionAST) getAstFromString("10"));

        FunctionCallAstMock fn_call = new FunctionCallAstMock(fn.getName(), callArgs);
        Assertions.assertThrows(WrongType.class, () -> evaluator.ExecLine(fn_call));


    }

    @Disabled
    @Test
    public void FunctionCreationFromString() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("func foo(string a) -> string{\n" +
                "  ret a + \" + B\"\n" +
                "}");
    }
}
