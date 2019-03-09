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
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        boolean flag_punc = false;
        boolean flag_symbol = false;
        String value = "";
        if(tokens.size() > 1) {
            if (tokens.get(1).getValue().equals("(")) {
                flag_punc = true;
            } else if (tokens.get(0).getType().equals("symbol")) {
                flag_symbol = true;
                value = tokens.get(0).getValue();
            }
        }
        if(flag_symbol && !flag_punc){
            this.setName(value);
            parser.next(1);
            return true;
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
