package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class VarAST extends DefaultAST {
    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public VarAST() {
        super("var");

    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        for(Token token: tokens){
            if(token.getType().equals("symbol")){
                this.setName(token.getValue());
                parsesr.next(1);
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    @Override
    public String printAST() {
         return "[var" + ":" + getName() + "]";
    }
}
