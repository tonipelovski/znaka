package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class IntTypeAST extends DefaultAST {
    private String value;

    public IntTypeAST(String value) {
        super("intType");
        this.value = value;
    }


    public void setValue(String value) {
        this.value = value;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        boolean flag = false;
        for(Token token: tokens){
            if(token.getType().equals("type") && token.getValue().equals("int")){
                flag = true;

            }else if(flag && token.getType().equals("symbol")){
                this.setValue(token.getValue());
                parsesr.next(2);
                return true;
            }else{
                flag = false;
            }
        }
        return false;
    }

    @Override
    public String printAST() {
        return "[" + getType() + ":" + getValue() + "]";
    }

    public String getValue() {
        return value;
    }
}
