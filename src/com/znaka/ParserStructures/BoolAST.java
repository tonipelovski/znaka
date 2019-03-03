package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class BoolAST extends DefaultAST{
    private boolean value;

    public BoolAST(boolean value) {
        super("bool");
        this.value = value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            if(token.getType().equals("bool")){
                if(token.getValue().equals("true")) {
                    this.setValue(true);
                }else{
                    this.setValue(false);
                }
                parser.next(1);
                return true;
            }
        }
        return false;
    }

    @Override
    public String printAST() {
        return "[" + getType() + ":" + isValue() + "]";
    }

    public boolean isValue() {
        return value;
    }
}
