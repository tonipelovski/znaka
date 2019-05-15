package com.znaka;

import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException, LexerException, ParserException, EvaluatorException {
        // write your code here
        BufferedReader reader = Files.newBufferedReader(Paths.get("TestResources/General/Basic.zk"),
                StandardCharsets.US_ASCII);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Evaluator evaluator = new Evaluator(parser);
        evaluator.setDebug(true);
        evaluator.run();
    }
}
