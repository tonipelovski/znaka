package com.znaka;

import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.ParserStructures.DefaultAST;

import java.io.IOException;

public class Evaluator {
    private Parser parser;

    public Evaluator(Parser parser) {
        this.parser = parser;
    }

    public void run() throws ParserException, IOException, LexerException {
        while(parser.parseLine()){
        }

        System.out.println(parser.printASTS());
    }

    public void EvaluateLine() throws ParserException, IOException, LexerException {
        parser.parseLine();
        DefaultAST ast = parser.mainAST.getAll_AST().peek();
        System.out.println(ast);
    }
}
