package com.znaka.Tests.EvaluatorTests;

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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;

public class EvaluatorTests {
    private Evaluator evaluator;
    private Lexer lexer;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        URL url = EvaluatorTests.class.getResource("test.zk");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        evaluator = new Evaluator(parser);
    }

    @Disabled
    @Test
    public void GeneralTest() throws ParserException, IOException, LexerException, EvaluatorException {

//        evaluator.run();
        evaluator.ProcessLine();
        evaluator.ProcessLine();
    }

    private void ExecuteString(String s1) throws LexerException, ParserException, IOException, EvaluatorException {
        lexer.resetInput(new BufferedReader(new StringReader(s1)));
        evaluator.ProcessLine();
    }

    private Variable findVar(String varName){
        return VarGetOper.findVar(evaluator.getVariables(), varName);
    }

     // waiting for changes of lexer and parser
    @Test
    public void VariableWithTypesReturnTest() throws LexerException, ParserException,  IOException, EvaluatorException {
        final String wrongExpressionRet = "Expression returns incorrect";

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

}
