package com.znaka.ParserStructures.Expression;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.BasicOperators;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class UnaryOperatorAST extends BasicOperators {
    public UnaryOperatorAST(DefaultAST l) {
        super(l, null);

    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            if(token.getType().equals("operator") && (token.getValue().equals("++")
                    || token.getValue().equals("--")
                    || token.getValue().equals("!"))){
                //System.out.println("unary: " + token.getType() + ":" + token.getValue());
                this.setOperator(token.getValue());
                this.setLeft(null);
                parser.next(1);
                return true;
            }else{
                return false;
            }

        }
        return false;
    }

    @Override
    public String toString() {
        String printLeft = "";
        if(getLeft() == null){
            printLeft = "null";
        }else{
            printLeft = getLeft().toString();
        }
        return "[" + getType() + ":" + getOperator() + ":" + printLeft + "]";
    }

    @Override
    public String getText() {
        return getOperator();
    }
}
