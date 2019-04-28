package com.znaka.Tests.EvaluatorTests;

import com.znaka.Evaluator;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.Lexer;
import com.znaka.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;

public class EvaluatorTests {
    private Evaluator evaluator;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        URL url = EvaluatorTests.class.getResource("test.zk");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        evaluator = new Evaluator(parser);
    }

    @Test
    public void GeneralTest() throws ParserException, IOException, LexerException {

//        evaluator.run();
        evaluator.EvaluateLine();
    }

}
