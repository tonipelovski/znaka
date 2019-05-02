package com.znaka.ParserStructures.Expression;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public class OperatorAST extends BasicOperators {

    public OperatorAST(DefaultAST l, DefaultAST r) {
        super(l, r);
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            // System.out.println("assign: " + token.getType() + ":" + token.getValue());

            if(token.getType().equals("operator") && !token.getValue().equals("=")  && (!token.getValue().equals("++")
                    && !token.getValue().equals("--")
                    && !token.getValue().equals("!"))){
                this.setOperator(token.getValue());
                this.setLeft(null);
                this.setRight(null);
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
        String printRight = "";
        if(getLeft() == null){
            printLeft = "null";
        }else{
            printLeft = getLeft().toString();
        }
        if(getRight() == null){
            printRight = "null";
        }else {
            printRight = getRight().toString();
        }
        return "[" + getType() + ":" + getOperator() + ":" + printLeft + ":" + printRight + "]";
    }

    @Override
    public String getText() {
        return getOperator();
    }

}
