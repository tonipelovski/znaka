package EvaluatorTests;


import Mocks.FunctionASTDefMock;
import Mocks.FunctionCallAstMock;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.*;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.Expression.ExpressionAST;
import com.znaka.ParserStructures.Expression.FunctionCallAST;
import com.znaka.ParserStructures.Expression.VarAST;
import com.znaka.ParserStructures.FunctionDefAST;
import com.znaka.ParserStructures.NumberAST;
import com.znaka.StdLib.Library;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    @Test
    public void CreateFunctionFromASTAndRun() throws EvaluatorException, ParserException, IOException, LexerException {
        List<VarAST> args = new ArrayList<>();
        args.add((VarAST)getAstFromString("int var1"));

        List<DefaultAST> body_code = new ArrayList<>();
        body_code.add(getAstFromString("string var1 = \"Hello\""));
        body_code.add(getAstFromString("ret 10"));
        body_code.add(getAstFromString("var1 = \"Hello\""));


        FunctionDefAST fn = new FunctionDefAST("test_func1", "int", args, body_code);
        evaluator.ExecLine(fn);
        Assertions.assertEquals(1, evaluator.getFunctions().size());

        List<ExpressionAST> callArgs = new ArrayList<>();
        callArgs.add((ExpressionAST) getAstFromString("10"));

        FunctionCallAST fn_call = new FunctionCallAST(fn.getName(), callArgs);
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
        body_code.add(getAstFromString("ret \"Beak it all\""));


        FunctionDefAST fn = new FunctionDefAST("test_func1", "int", args, body_code);
        evaluator.ExecLine(fn);

        List<ExpressionAST> callArgs = new ArrayList<>();
        callArgs.add((ExpressionAST) getAstFromString("10"));

        FunctionCallAST fn_call = new FunctionCallAST(fn.getName(), callArgs);
        Assertions.assertThrows(WrongType.class, () -> evaluator.ExecLine(fn_call));


    }

    @Test
    public void FunctionCreation() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("string foo(string a){" +
                "  ret a" +
                "}");
        Assertions.assertEquals(1, evaluator.getFunctions().size());
        ExecuteString("foo(\"Toni\")");
        checkLastValAndType("\"Toni\"", "string");
    }

    @Test
    public void fileRun() throws IOException, EvaluatorException, ParserException, LexerException {
        setNewFile("General/FuncTest.zk");
        evaluator.run();
        Assertions.assertEquals(2, evaluator.getFunctions().size());
        checkLastValAndType(40, "int");

//        checkLastValAndType("\"Toni\"", "string");
    }

    @Test
    public void FullFunctionTestFromFile() throws IOException, EvaluatorException, ParserException, LexerException {
        setNewFile("EvaluatorResources/BasicFunctionTest.zk");
        Library.addFunctions(evaluator.getFunctions());
        evaluator.run();
        ExecuteStringNoExceptions("f7(7)");
        checkLastValAndType(7, "int");
        ExecuteStringNoExceptions("f2()");
        checkLastValAndType(1, "int");
        Assertions.assertEquals(0, evaluator.getCallStack().size());
        ExecuteStringNoExceptions("f5()");
        checkLastValAndType(null, "void");
        ExecuteStringNoExceptions("f3()");
        checkLastValAndType(20, "int");
        ExecuteStringNoExceptions("f4()");
        checkLastValAndType(21, "int");
        ExecuteStringNoExceptions("f6()");
        checkLastValAndType(7, "int");
        ExecuteStringNoExceptions("f7(5-1)");
        checkLastValAndType(4, "int");


    }

    @Test
    public void Recursion() throws IOException, EvaluatorException, ParserException, LexerException {
        setNewFile("EvaluatorResources/Recursion.zk");
        Library.addFunctions(evaluator.getFunctions());
        evaluator.run();
        ExecuteStringNoExceptions("fib(1)");
        checkLastValAndType(1, "int");
        Assertions.assertEquals(0, evaluator.getCallStack().size());
        ExecuteStringNoExceptions("fib(3)");
        checkLastValAndType(2, "int");
        ExecuteStringNoExceptions("fib(4)");
        checkLastValAndType(3, "int");
        ExecuteStringNoExceptions("fib(5)");
        checkLastValAndType(5, "int");
        ExecuteStringNoExceptions("fact(1)");
        checkLastValAndType(1, "int");
        ExecuteStringNoExceptions("fact(2)");
        checkLastValAndType(2, "int");
        ExecuteStringNoExceptions("fact(3)");
        checkLastValAndType(6, "int");
        ExecuteStringNoExceptions("fact(4)");
        checkLastValAndType(24, "int");
    }




}
