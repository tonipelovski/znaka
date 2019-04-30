package com.znaka.Tests.EvaluatorTests;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.ExecuteOperations.VarGetOper;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.Exceptions.WrongType;
import com.znaka.Lexer;
import com.znaka.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;

public class EvaluatorTests {
    private Evaluator evaluator;
    private Lexer lexer;

    @BeforeEach
    public void setUp() throws IOException {

        URL url = EvaluatorTests.class.getResource("test.zk");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        evaluator = new Evaluator(parser);
        reader.close();
    }

    @Test
    public void GeneralTest() throws ParserException, IOException, LexerException, EvaluatorException {

//        evaluator.run();
//        evaluator.ProcessLine();
//        evaluator.ProcessLine();
        ExecuteString("h = True");
        ExecuteString("h = 10 + 20.7");
        ExecuteString("a = 2");
        ExecuteString("d = 2.6");
        ExecuteString("double g = 2.234");
        ExecuteString("b = 'c'");
        ExecuteString("c = \"Hi\"");
        ExecuteString("c = a");
    }

    private void ExecuteString(String s1) throws LexerException, ParserException, IOException, EvaluatorException {
        lexer.resetInput(new BufferedReader(new StringReader(s1)));
        evaluator.ProcessLine();
    }

    private Variable findVar(String varName){
        return VarGetOper.findVar(evaluator.getVariables(), varName);
    }

    private <T> void checkLastValAndType(T val, String type){
        Assertions.assertEquals(val, evaluator.getLastReturnedValue().getVal());
        Assertions.assertEquals(type, evaluator.getLastReturnedValue().getType());
    }

    private <T extends Throwable> void ErrorTypeHelper(String s1, Class<T> exception) throws LexerException, ParserException, EvaluatorException, IOException {
        Assertions.assertThrows(exception, () -> {
            ExecuteString(s1);
        });
    }

     // waiting for changes of lexer and parser
    @Test
    public void VariableWithTypesReturnTest() throws LexerException, ParserException,  IOException, EvaluatorException {
        final String wrongExpressionRet = "Expression returns incorrect";

        ExecuteString("int abc1 = -2");
        Assertions.assertEquals(-2, evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("int abc = 2");
        Assertions.assertEquals(2, evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("float abc = -20.0");
        Assertions.assertEquals(Float.parseFloat("-20.0"), evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("double abc = 2.0");
        Assertions.assertEquals(2.0, evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);

        ExecuteString("char a = 's'");
        Assertions.assertEquals('s', evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);


        ExecuteString("string a = \"Hello\"");
        Assertions.assertEquals("\"Hello\"", evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("string s1 = \"Hello\"");
        Assertions.assertEquals("\"Hello\"", evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("string s2 = \"Hello World!\"");
        Assertions.assertEquals("\"Hello World!\"", evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("string s3 = \"I am working correctly.\"");
        Assertions.assertEquals("\"I am working correctly.\"", evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);

    }

    @Test
    public void VariableAutoResolveReturnTest() throws LexerException, ParserException, IOException, EvaluatorException {
        final String wrongExpressionRet = "Expression returns incorrect";

        ExecuteString("abc = 2");
        Assertions.assertEquals(2, evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("fl1 = 29.0");
        Assertions.assertEquals(Float.parseFloat("29.0"), evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);


        ExecuteString("a = 's'");
        Assertions.assertEquals('s', evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("b = 'G'");
        Assertions.assertEquals('G', evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("b = 'G'");
        Assertions.assertEquals('G', evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);


        ExecuteString("s1 = \"Hello\"");
        Assertions.assertEquals("\"Hello\"", evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("s2 = \"Hello World!\"");
        Assertions.assertEquals("\"Hello World!\"", evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);
        ExecuteString("s3 = \"I am working correctly.\"");
        Assertions.assertEquals("\"I am working correctly.\"", evaluator.getLastReturnedValue().getVal(), wrongExpressionRet);

    }

    @Test
    public void VariableCreationCorrectNumber() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 20");
        Assertions.assertEquals(1, evaluator.getVariables().size());
        ExecuteString("char c1 = 'c'");
        ExecuteString("string s1 = \"Hey\"");
        ExecuteString("f1 = 15.7");
        Assertions.assertEquals(4, evaluator.getVariables().size());
    }

    @Disabled
    @Test
    public void VariableCorrectTypeForce() throws LexerException, ParserException, EvaluatorException, IOException {
        ErrorTypeHelper("int b = 20.8", WrongType.class);
        ErrorTypeHelper("string a = 5", WrongType.class);
        ErrorTypeHelper("int a = 5.9", WrongType.class);
        checkLastValAndType(5, "integer");
        ErrorTypeHelper("char a = 95", WrongType.class);
    }

    @Test
    public void VariableCreationCorrectNamesAndValues() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 20");
        Assertions.assertEquals("a", findVar("a").getName());
        Assertions.assertEquals(20, findVar("a").getVal().getVal());

        ExecuteString("a = 20.5");
        Assertions.assertEquals("a", findVar("a").getName());
        Assertions.assertEquals(Float.parseFloat("20.5"), findVar("a").getVal().getVal());

        ExecuteString("double d2 = -6.7");
        Assertions.assertEquals("d2", findVar("d2").getName());
        Assertions.assertEquals(Double.parseDouble("-6.7"), findVar("d2").getVal().getVal());

        ExecuteString("c1 = 'f'");
        Assertions.assertEquals("c1", findVar("c1").getName());
        Assertions.assertEquals('f', findVar("c1").getVal().getVal());

    }

    @Test
    public void VariableGetTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 10");
        checkLastValAndType(10, "integer");
        ExecuteString("a  ");
        checkLastValAndType(10, "integer");

        ExecuteString("float a = 10.5");
        checkLastValAndType(Float.parseFloat("10.5"), "float");
        ExecuteString("a  ");
        checkLastValAndType(Float.parseFloat("10.5"), "float");

        ExecuteString("double a = 23.678");
        checkLastValAndType(23.678, "double");
        ExecuteString("a  ");
        checkLastValAndType(23.678, "double");

        ExecuteString("bool a = True");
        checkLastValAndType(true, "boolean");
        ExecuteString("a  ");
        checkLastValAndType(true, "boolean");

        ExecuteString("a = 'c'");
        checkLastValAndType('c', "char");
        ExecuteString("a  ");
        checkLastValAndType('c', "char");

        ExecuteString("a = \"Hey\"");
        checkLastValAndType("\"Hey\"", "string");
        ExecuteString("a  ");
        checkLastValAndType("\"Hey\"", "string");
    }

}
