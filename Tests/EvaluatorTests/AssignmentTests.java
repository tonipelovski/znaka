package EvaluatorTests;

import com.znaka.Exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AssignmentTests extends EvaluatorTest {

    @Test
    public void GeneralTest() throws ParserException, IOException, LexerException, EvaluatorException {

//        evaluator.run();
//        evaluator.ProcessLine();
//        evaluator.ProcessLine();
//        ExecuteString("void c = if(1==1){}");
        ExecuteString("h = True");
        ExecuteString("j = 10 + 20.7");
        ExecuteString("a = 2");
        ExecuteString("d = 2.6");
        ExecuteString("double g = 2.234");
        ExecuteString("b = 'c'");
        ExecuteString("c = \"Hi\"");
        ExecuteString("int c = a");
    }

    @Test
    public void AnyTypeTest(){
        ExecuteStringNoExceptions("any a = 10");
        checkLastValAndType(10, "any");
        ExecuteStringNoExceptions("a = \"cqla nosht rakiq sum pil, razbiram 4e, kirow e debil\"");
        Assertions.assertEquals("any", evaluator.getLastReturnedValue().getType());
        ExecuteStringNoExceptions("any a = \"asd\"");
        checkLastValAndType("\"asd\"", "any");
        ExecuteStringNoExceptions("any a = True");
        checkLastValAndType(true, "any");
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

    @Test
    public void VariableCorrectTypeException() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteStringNoExceptions("double d = 20.8");
        checkLastValAndType(20.8, "double");
        ExecuteStringNoExceptions("float d = 20.8");
        checkLastValAndType(Float.parseFloat("20.8"), "float");
        ExecuteAndCheckThrows("double b = 20", WrongType.class);
        ExecuteAndCheckThrows("float b = 20", WrongType.class);
        ExecuteAndCheckThrows("float b = 'c'", WrongType.class);
        ExecuteAndCheckThrows("int b = 20.8", WrongType.class);
        ExecuteAndCheckThrows("int b = 'b'", WrongType.class);
        ExecuteAndCheckThrows("string a = 5", WrongType.class);
        ExecuteAndCheckThrows("int a = 5.9", WrongType.class);
        ExecuteAndCheckThrows("char a = 95", WrongType.class);
    }

    @Test
    public void IncorrectAssignmentTypes() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteStringNoExceptions("char a = 'c'");
        ExecuteAndCheckThrows("a = 15", WrongType.class);
        ExecuteAndCheckThrows("a = \"asd\"", WrongType.class);
        ExecuteStringNoExceptions("a = 'J'");
        ExecuteStringNoExceptions("float a = 3.8");
        ExecuteStringNoExceptions("double a = 3.886868");
        ExecuteAndCheckThrows("int a = True", WrongType.class);
        ExecuteStringNoExceptions("bool a = True");
        ExecuteStringNoExceptions("a = False");
        ExecuteStringNoExceptions("bool a = True");
    }

    @Test
    public void VariableCreationCorrectNamesAndValues() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 20");
        Assertions.assertEquals("a", findVar("a").getName());
        Assertions.assertEquals(20, findVar("a").getVal().getVal());

        ExecuteString("b = 20.5");
        Assertions.assertEquals("b", findVar("b").getName());
        Assertions.assertEquals(Float.parseFloat("20.5"), findVar("b").getVal().getVal());

        ExecuteString("double d2 = -6.7");
        Assertions.assertEquals("d2", findVar("d2").getName());
        Assertions.assertEquals(-6.7, findVar("d2").getVal().getVal());

        ExecuteString("c1 = 'f'");
        Assertions.assertEquals("c1", findVar("c1").getName());
        Assertions.assertEquals('f', findVar("c1").getVal().getVal());

    }

    @Test
    public void VariableGetTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 10");
        checkLastValAndType(10, "int");
        ExecuteString("a  ");
        checkLastValAndType(10, "int");

        ExecuteString("float a = 10.5");
        checkLastValAndType(Float.parseFloat("10.5"), "float");
        ExecuteString("a  ");
        checkLastValAndType(Float.parseFloat("10.5"), "float");

        ExecuteString("double a = 23.678");
        checkLastValAndType(23.678, "double");
        ExecuteString("a  ");
        checkLastValAndType(23.678, "double");

        ExecuteString("bool a = True");
        checkLastValAndType(true, "bool");
        ExecuteString("a  ");
        checkLastValAndType(true, "bool");

        ExecuteString("b = 'c'");
        checkLastValAndType('c', "char");
        ExecuteString("b  ");
        checkLastValAndType('c', "char");

        ExecuteString("c = \"Hey\"");
        checkLastValAndType("\"Hey\"", "string");
        ExecuteString("c  ");
        checkLastValAndType("\"Hey\"", "string");
    }

    @Test
    public void ConstantsTest() {
        ExecuteStringNoExceptions("a = 10");
        ExecuteStringNoExceptions("a = 5");
        ExecuteStringNoExceptions("non-var a = 10");
        ExecuteAndCheckThrows("a = 8", CannotModifyConstant.class);
        ExecuteStringNoExceptions("b = 'c'");
        ExecuteStringNoExceptions("b = 'H'");
        ExecuteStringNoExceptions("non-var b = 'a'");
        ExecuteAndCheckThrows("b = 'f'", CannotModifyConstant.class);
    }

}
