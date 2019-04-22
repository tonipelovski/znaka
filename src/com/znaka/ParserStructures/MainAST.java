package com.znaka.ParserStructures;

import com.znaka.Main;
import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;
import java.util.Stack;

public class MainAST extends DefaultAST{
    private String type;
    private Stack<DefaultAST> all_AST;

    public MainAST(Stack<DefaultAST> all_AST) {
        super("main");
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
        String result = "";

        return result;
    }

    @Override
    public String getText() {
        return null;
    }

    public Stack<DefaultAST> getAll_AST() {
        return all_AST;
    }

    public void addAST(MainAST ast){
        all_AST.addAll(ast.getAll_AST());
        //all_AST.add(ast);
    }


    public void addAST(DefaultAST ast){
        all_AST.add(ast);
    }

    public void popFrontAST(int to_pop) {
        Stack<DefaultAST> tmpStack = new Stack<>();
        for(int i = to_pop; i < all_AST.size(); i++){
            tmpStack.push(all_AST.get(i));
        }
        all_AST = tmpStack;
    }

    public boolean has(int count){
        if(all_AST.size() >= count){
            return true;
        }else{
            return false;
        }
    }
}
