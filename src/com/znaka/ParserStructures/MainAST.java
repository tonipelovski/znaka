package com.znaka.ParserStructures;

import java.util.Stack;

public class MainAST extends DefaultAST{
    private Stack<DefaultAST> all_AST;

    public MainAST(Stack<DefaultAST> all_AST) {
        super("main");
        this.all_AST = all_AST;
    }

    public void addAST(DefaultAST ast){
        all_AST.add(ast);
    }

    @Override
    boolean matchAST(DefaultAST ast) {
        if(ast.getType().equals(this.getType())){
            return true;
        }else {
            return false;
        }
    }
}
