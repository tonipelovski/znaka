package com.znaka.ParserStructures;

import java.util.Stack;

public class MainAST{
    private String type;
    private Stack<DefaultAST> all_AST;

    public MainAST(Stack<DefaultAST> all_AST) {
        this.type = "main";
        this.all_AST = all_AST;
    }

    public String getType() {
        return type;
    }

    public Stack<DefaultAST> getAll_AST() {
        return all_AST;
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
