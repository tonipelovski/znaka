package com.znaka.ParserStructures.Statement;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class ElseConditionAST extends ConditionalsAST {
    public ElseConditionAST(MainAST cond, MainAST body) {
        super(cond, body);
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        for(Token token: tokens){
            if(token.getType().equals("keyword") && token.getValue().equals("else")){
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
        String then = "";
        if (getBody() != null){
            for(DefaultAST defaultAST : getBody().getAll_AST()) {
                if(defaultAST != null) {
                    then = then.concat(defaultAST.toString());
                }
            }        }

        return "\n[" + "else" + ":" + then + "]";
    }

    @Override
    public String getText() {
        return "else";
    }
}
