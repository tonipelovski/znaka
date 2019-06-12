package EvaluatorTests;

import com.znaka.Evaluator;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.Lexer;
import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class StatementTests extends EvaluatorTest {
    @Test
    public void IfConditionTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 10");
        ExecuteString("int b = 5");

        ExecuteString("if(b < a){ b = a }");
        ExecuteString("int c = b");

        Assertions.assertEquals(10, evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void IfElseConditionTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 10");
        ExecuteString("int b = 5");

        ExecuteString("if(b > a){ b = a }else{ b = 100}");
        ExecuteString("int c = b");

        Assertions.assertEquals(100, evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void LoopTest() throws LexerException, ParserException, EvaluatorException, IOException {
        ExecuteString("int a = 10");
        ExecuteString("int b = 5");

        ExecuteString("while(b < a){ b = b + 1}");
        ExecuteString("int c = b");

        Assertions.assertEquals(10, evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void ElseIfConditionTestFalse() throws LexerException, ParserException, EvaluatorException, IOException {
        URL url = StatementTests.class.getResource("StatementElseIfTest");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        String lexerOutput = "";
        while (lexer.readLine()) {
            lexerOutput = lexerOutput.concat(lexer.tokensToString());
        }
        lexer.resetInput(new BufferedReader(new FileReader(file)));
        Parser parser = new Parser(lexer);
        Evaluator evaluator = new Evaluator(parser);

        evaluator.ProcessLine();
        evaluator.ProcessLine();
        evaluator.ProcessLine();


        //evaluator.ProcessLine();
        //evaluator.ProcessLine();

        Assertions.assertEquals(false, evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void ElseIfConditionTestTrue() throws LexerException, ParserException, EvaluatorException, IOException {
        URL url = StatementTests.class.getResource("StatementElseIfTestTrue");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Token> tokens = new ArrayList<>();
        Lexer lexer = new Lexer(tokens, reader);

        String lexerOutput = "";
        while (lexer.readLine()) {
            lexerOutput = lexerOutput.concat(lexer.tokensToString());
        }
        lexer.resetInput(new BufferedReader(new FileReader(file)));
        Parser parser = new Parser(lexer);
        Evaluator evaluator = new Evaluator(parser);

        evaluator.ProcessLine();
        evaluator.ProcessLine();
        evaluator.ProcessLine();


        //evaluator.ProcessLine();
        //evaluator.ProcessLine();

        Assertions.assertEquals(100, evaluator.getLastReturnedValue().getVal());
    }

    @Test
    public void ScopesTest() throws IOException, EvaluatorException, ParserException, LexerException {
        setNewFile("ParserResources/ConditionalTests.zk");
//        Library.addFunctions(evaluator.getFunctions());
        evaluator.run();
        ExecuteStringNoExceptions("if(True){a=10}");
        ExecuteStringNoExceptions("a");
        checkLastValAndType(10, "int");
        ExecuteStringNoExceptions("a");
        ExecuteStringNoExceptions("b");
        ExecuteAndCheckThrows("c", UnknownVariable.class);
        ExecuteAndCheckThrows("d", UnknownVariable.class);
        ExecuteStringNoExceptions("if(True){a=10}\na");
        checkLastValAndType(10, "int");

    }

    @Test
    public void ScopesTest2() throws IOException, EvaluatorException, ParserException, LexerException {
        setNewFile("ParserResources/ConditionalTest2.zk");
//        Library.addFunctions(evaluator.getFunctions());
        evaluator.run();
        ExecuteAndCheckThrows("g", UnknownVariable.class);
        ExecuteAndCheckThrows("c", UnknownVariable.class);

    }
}
