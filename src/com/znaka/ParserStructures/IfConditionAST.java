package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class IfConditionAST extends ConditionalsAST {


    public IfConditionAST(MainAST cond, MainAST th) {
        super(cond, th);

    }
    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        for(Token token: tokens){
            if(token.getType().equals("keyword") && token.getValue().equals("if")){
                this.setBody(null);
                this.setCond(null);
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
        if (getCond() != null){
            for(DefaultAST defaultAST : getCond().getAll_AST()) {
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

        return "\n[" + "if" + ":" + condition + ":" + then + "]";
    }

    @Override
    public String getText() {
        return "if";
    }

}
