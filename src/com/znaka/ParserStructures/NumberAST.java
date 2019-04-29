package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class NumberAST extends DefaultAST{
    private String value;

    public NumberAST(String value) {
        super("number");
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            if(token.getType().equals("number")){
                this.setValue(token.getValue());
                parser.next(1);
                return true;
            }else{
                return false;
            }
        }
        return false;
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
