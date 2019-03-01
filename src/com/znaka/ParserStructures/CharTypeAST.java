package com.znaka.ParserStructures;

public class CharTypeAST extends DefaultAST{
    private char value;

    public CharTypeAST(char value) {
        super("charType");
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

    public char getValue() {
        return value;
    }
}
