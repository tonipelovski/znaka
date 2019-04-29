package com.znaka;

import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.ExpressionAST;

import java.io.IOException;

public class Evaluator {
    private Parser parser;

    public Evaluator(Parser parser) {
        this.parser = parser;
    }

    public void run() throws ParserException, IOException, LexerException {
        while(parser.parseLine()){
        }

        System.out.println(parser);
    }

    public void EvaluateLine() throws ParserException, IOException, LexerException {
        parser.parseLine();
        //DefaultAST ast = parser.mainAST.getAll_AST().get(0);
        for(DefaultAST ast : parser.mainAST.getAll_AST()) {
            System.out.println(ast);
            System.out.println(ast instanceof ExpressionAST);
            System.out.println(ast.getText());
        }
    }
}
