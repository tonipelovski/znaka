package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class IfConditionAST extends DefaultAST {
    private MainAST condition;
    private MainAST then;
    private DefaultAST else_condition;

    public void setCondition(MainAST condition) {
        this.condition = condition;
    }

    public void setThen(MainAST then) {
        this.then = then;
    }

    public void setElse_condition(DefaultAST else_condition) {
        this.else_condition = else_condition;
    }

    public IfConditionAST(MainAST cond, MainAST th, DefaultAST else_cond) {
        super("if");
        this.condition = cond;
        this.then = th;
        this.else_condition = else_cond;

    }

    public MainAST getCondition() {
        return condition;
    }

    public MainAST getThen() {
        return then;
    }

    public DefaultAST getElse_condition() {
        return else_condition;
    }


    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        for(Token token: tokens){
            if(token.getType().equals("keyword") && (token.getValue().equals("if") || token.getValue().equals("else")
            || token.getValue().equals("else if"))){
                this.condition = null;
                this.else_condition = null;
                this.then = null;
                parsesr.next(1);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String printAST() {
        String condition = "";
        if (getCondition() != null){
            for(DefaultAST defaultAST : getCondition().getAll_AST()) {
                if(defaultAST != null) {
                    condition = condition.concat(defaultAST.printAST());
                }
            }
        }

        String then = "";
        if (getThen() != null){
            for(DefaultAST defaultAST : getThen().getAll_AST()) {
                if(defaultAST != null) {
                    then = then.concat(defaultAST.printAST());
                }
            }        }

        String else_cond = "";
        if (getElse_condition() != null){
            else_cond = getElse_condition().printAST();
        }
        return "\n[" + getType() + ":" + condition + ":" + else_cond + ":" + then + "]";
    }

}
