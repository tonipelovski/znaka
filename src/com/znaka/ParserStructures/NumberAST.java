package com.znaka.ParserStructures;

public class NumberAST extends DefaultAST{
    private double value;

    public NumberAST(double value) {
        super("number");
        this.value = value;
    }
    public double getValue() {
        return value;
    }

    @Override
    boolean matchAST(DefaultAST ast) {
        if(ast.getType().equals(this.getType())){
            return true;
        }else {
            return false;
        }
    }
}
