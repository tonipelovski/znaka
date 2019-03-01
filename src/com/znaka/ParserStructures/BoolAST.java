package com.znaka.ParserStructures;

public class BoolAST extends DefaultAST{
    private boolean value;

    public BoolAST(boolean value) {
        super("bool");
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

    public boolean isValue() {
        return value;
    }
}
