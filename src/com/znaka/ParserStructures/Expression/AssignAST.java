package com.znaka.ParserStructures.Expression;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.BasicOperators;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class AssignAST extends BasicOperators {

    public AssignAST(DefaultAST l, DefaultAST r) {
        super(l, r);
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
           // System.out.println("assign: " + token.getType() + ":" + token.getValue());

            if(token.getType().equals("operator") && token.getValue().equals("=")){
                this.setOperator("=");
                this.setLeft(null);
                this.setRight(null);
                parser.next(1);
                //System.out.println("assigning");
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
        return "\n  [" + getType() + "\n    " + getOperator() + "\n     " + printLeft + "\n     " + printRight + "]";
    }

    @Override
    public String getText() {
        return getOperator();
    }


}
