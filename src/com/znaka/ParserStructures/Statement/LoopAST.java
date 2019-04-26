package com.znaka.ParserStructures.Statement;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.ParserStructures.Statement.ConditionalsAST;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class LoopAST extends ConditionalsAST {

    public LoopAST(MainAST condition, MainAST body) {
        super(condition, body);
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        for(Token token: tokens){
            if(token.getType().equals("keyword") && token.getValue().equals("while")){
                this.setCond(null);
                this.setBody(null);
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
        if (getCond() != null) {
            for (DefaultAST defaultAST : getCond().getAll_AST()) {
                if (defaultAST != null) {
                    condition = condition.concat(defaultAST.printAST());
                }
            }
        }

        String then = "";
        if (getBody() != null) {
            for (DefaultAST defaultAST : getBody().getAll_AST()) {
                if (defaultAST != null) {
                    then = then.concat(defaultAST.printAST());
                }
            }
        }

        return "\n[" + "while" + ":" + condition + ":" + then + "]";
    }

    @Override
    public String getText() {
        return "while";
    }
}
