package com.znaka.ParserStructures.Expression;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.Tokens.Token;

import java.util.ArrayList;
//TO DO create unary operator
public abstract class BasicOperators extends ExpressionAST {
    private String operator;
    private DefaultAST left;
    private DefaultAST right;

    public BasicOperators(DefaultAST l, DefaultAST r) {
        super("operator");
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
    protected abstract boolean matchAST(ArrayList<Token> tokens, Parser parsesr);

    @Override
    abstract public String toString();

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
