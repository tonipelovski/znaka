package com.znaka.ParserStructures.Statement;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public class IfConditionAST extends ConditionalsAST {

    public IfConditionAST(MainAST cond, MainAST th) {
        super(cond, th, null);

    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
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
    public String toString() {
        String condition = "";
        if (getCond() != null){
            for(DefaultAST defaultAST : getCond().getAll_AST()) {
                if(defaultAST != null) {
                    condition = condition.concat(defaultAST.toString());
                }
            }
        }

        String then = "";
        if (getBody() != null){
            for(DefaultAST defaultAST : getBody().getAll_AST()) {
                if(defaultAST != null) {
                    then = then.concat(defaultAST.toString());
                }
            }        }
        String else_cond = "";

        if(getElse_cond() != null){
            else_cond = getElse_cond().toString();
        }
        return "\n[" + "if" + ":" + condition + ":" + then + ":" + else_cond + "]";
    }

    @Override
    public String getText() {
        return "if";
    }

}
