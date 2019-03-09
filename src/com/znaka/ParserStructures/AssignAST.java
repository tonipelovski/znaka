package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class AssignAST extends DefaultAST {
    private String operator;
    private DefaultAST left;
    private DefaultAST right;

    public AssignAST(DefaultAST l, DefaultAST r) {
        super("assign");
        this.left = l;
        this.right = r;
    }

    public String getOperator() {
        return operator;
    }

    public DefaultAST getLeft() {
        return left;
    }

    public DefaultAST getRight() {
        return right;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
           // System.out.println("assign: " + token.getType() + ":" + token.getValue());

            if(token.getType().equals("operator") && token.getValue().equals("=")){
                this.setOperator("=");
                this.setLeft(null);
                this.setRight(null);
                parser.next(1);
                //System.out.println("assigning");
                return true;
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

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setLeft(DefaultAST left) {
        this.left = left;
    }

    public void setRight(DefaultAST right) {
        this.right = right;
    }
}
