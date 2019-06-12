package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;
import java.util.Stack;

public class ArrayAST extends LiteralTypesAST{
    private Stack<DefaultAST> all_AST;

    public ArrayAST(Stack<DefaultAST> all_AST) {
        super("array");
        this.all_AST = all_AST;
    }

    public Stack<DefaultAST> getContent() {
        return all_AST;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        return false;
    }

    @Override
    public String toString() {
        String result = "{";
        for(DefaultAST defaultAST : all_AST){
            result = result.concat(defaultAST.toString());
        }
        return result + "}";
    }

    @Override
    public String getText() {
        return null;
    }

    public void addAST(DefaultAST ast){
        all_AST.add(ast);
    }

}
