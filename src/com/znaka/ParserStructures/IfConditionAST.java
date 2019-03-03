package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class IfConditionAST extends DefaultAST {
    private DefaultAST condition;
    private DefaultAST then;
    private DefaultAST else_condition;

    public IfConditionAST(DefaultAST cond, DefaultAST th, DefaultAST else_cond) {
        super("if");
        this.condition = cond;
        this.then = th;
        this.else_condition = else_cond;

    }

    public DefaultAST getCondition() {
        return condition;
    }

    public DefaultAST getThen() {
        return then;
    }

    public DefaultAST getElse_condition() {
        return else_condition;
    }


    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        for(Token token: tokens){
            if(token.getType().equals("keyword") && token.getValue().equals("if")){
                this.condition = null;
                this.else_condition = null;
                this.then = null;
                parsesr.next(1);
                return true;
            }
        }
        return false;
    }

    @Override
    public String printAST() {
        return "[" + getType() + ":" + getCondition() + ":" + getElse_condition() + ":" + getThen() + "]";
    }

}
