package com.znaka.PerserStructures;

public class StringAST extends DefaultAST{
    private String value;

    public StringAST(String value) {
        super("str");
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

    public String getValue() {
        return value;
    }
}
