package com.znaka.PerserStructures;

public class IntTypeAST extends DefaultAST {
    private int value;

    public IntTypeAST(int value) {
        super("intType");
        this.value = value;
    }

    @Override
    boolean matchAST(DefaultAST ast) {
        if(ast.getType().equals(this.getType())){
            return true;
        }else {
            return false;
        }
    }

    public int getValue() {
        return value;
    }
}
