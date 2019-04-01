package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class VarAST extends DefaultAST {
    private String type;
    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public VarAST() {
        super("var");
        this.type = "";

    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        boolean flag_punc = false;
        boolean flag_symbol = false;
        String value = "";
        String t = "";
        int i = 0;

        //System.out.println("start");
        if (tokens.get(i).getType().equals("type")) {
            t = tokens.get(i).getValue();
            i++;

        }
        if(tokens.size() > i + 1) {
            for (; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                //System.out.println(token.getValue());
                if (token.getValue().equals("(") || token.getValue().equals("[")) {
                     return false;
                } else if (token.getType().equals("symbol")) {
                    flag_symbol = true;
                    value = token.getValue();

                 }
            }
        }
        if(flag_symbol){
            //System.out.println("end");

            this.setName(value);
            this.setType(t);
            if(!t.equals("")) {
                parser.next(2);
            }else{
                parser.next(1);
            }

            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    @Override
    public String printAST() {
         return "[var" + ":" + getType() + ":" + getName() + "]";
    }
}