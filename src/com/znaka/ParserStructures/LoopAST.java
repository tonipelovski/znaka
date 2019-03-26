package com.znaka.ParserStructures;

import com.znaka.Main;
import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class LoopAST extends DefaultAST{
    private MainAST condition;
    private MainAST body;

    public MainAST getCondition() {
        return condition;
    }

    public void setCondition(MainAST condition) {
        this.condition = condition;
    }

    public MainAST getBody() {
        return body;
    }

    public void setBody(MainAST body) {
        this.body = body;
    }

    public LoopAST() {
        super("loop");
        this.condition = null;
        this.body = null;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        for(Token token: tokens){
            if(token.getType().equals("keyword") && (token.getValue().equals("while"))){
                this.condition = null;
                this.body = null;
                parsesr.next(1);
                return true;
            }else {
                return false;
            }
        }
        return false;    }

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
        if (getBody() != null){
            for(DefaultAST defaultAST : getBody().getAll_AST()) {
                if(defaultAST != null) {
                    then = then.concat(defaultAST.printAST());
                }
            }        }

        return "\n[" + getType() + ":" + condition + ":" + then + "]";    }
}
