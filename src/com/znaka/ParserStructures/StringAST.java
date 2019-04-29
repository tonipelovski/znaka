package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class StringAST extends LiteralTypesAST {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringAST(String value) {
        super("string_literal");
        this.value = value;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            //System.out.println(token.getType() + ":" + token.getValue());
            if(token.getType().equals("string_literal")){
                setValue(token.getValue());
                parser.next(1);
                return true;
            }else {
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
        return getValue();
    }
}
