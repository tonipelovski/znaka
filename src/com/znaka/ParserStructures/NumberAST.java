package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.awt.*;
import java.util.ArrayList;

public class NumberAST extends LiteralTypesAST {
    private String value;
    private String numberType;

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

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            if(token.getType().equals("integer")){
                this.setValue(token.getValue());
                this.setNumberType("integer");
                parser.next(1);
                return true;
            }else if(token.getType().equals("float")){
                this.setValue(token.getValue());
                this.setNumberType("float");
                parser.next(1);
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + getNumberType() + ":" + getValue() + "]";
    }

    @Override
    public String getText() {
        return String.valueOf(getValue());
    }
}
