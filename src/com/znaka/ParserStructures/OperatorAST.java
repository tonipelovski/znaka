package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class OperatorAST extends BasicOperators {

    public OperatorAST(DefaultAST l, DefaultAST r) {
        super(l, r);
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            // System.out.println("assign: " + token.getType() + ":" + token.getValue());

            if(token.getType().equals("operator") && !token.getValue().equals("=")){
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
    public String printAST() {
        String printLeft = "";
        String printRight = "";
        if(getLeft() == null){
            printLeft = "null";
        }else{
            printLeft = getLeft().printAST();
        }
        if(getRight() == null){
            printRight = "null";
        }else {
            printRight = getRight().printAST();
        }
        return "[" + getType() + ":" + getOperator() + ":" + printLeft + ":" + printRight + "]";
    }

}
