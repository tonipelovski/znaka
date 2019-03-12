package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class OperatorAST extends DefaultAST {
    private String operator;
    private DefaultAST left;
    private DefaultAST right;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public DefaultAST getLeft() {
        return left;
    }

    public void setLeft(DefaultAST left) {
        this.left = left;
    }

    public DefaultAST getRight() {
        return right;
    }

    public void setRight(DefaultAST right) {
        this.right = right;
    }

    public OperatorAST(DefaultAST l, DefaultAST r) {
        super("operator");
        this.left = l;
        this.right = r;
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
