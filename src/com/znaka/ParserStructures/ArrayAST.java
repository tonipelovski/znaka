package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;
import java.util.Stack;

public class ArrayAST extends DefaultAST{
    private String type;
    private Stack<DefaultAST> all_AST;

    public ArrayAST(Stack<DefaultAST> all_AST) {
        super("array");
        this.all_AST = all_AST;
    }

    public String getType() {
        return type;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        return false;
    }

    @Override
    public String printAST() {
        String result = "{";
        for(DefaultAST defaultAST : all_AST){
            result = result.concat(defaultAST.printAST());
        }
        return result + "}";
    }
    public void addAST(DefaultAST ast){
        all_AST.add(ast);
    }

}
