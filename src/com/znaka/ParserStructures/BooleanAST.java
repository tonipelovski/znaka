package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class BooleanAST extends LiteralTypesAST{
    private boolean value;

    public BooleanAST(boolean value) {
        super("boolean");
        this.value = value;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            //System.out.println(token.getType() + ":" + token.getValue());
            if(token.getType().equals("boolean")){
                setValue(Boolean.parseBoolean(token.getValue()));
                parser.next(1);
                return true;
            }else{
                return false;
            }
        }
        return false;    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + getType() + ":" + getValue() + "]";
    }

    @Override
    public String getText() {
        return String.valueOf(getValue());
    }
}
