package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class CharTypeAST extends DefaultAST{
    private char value;

    public void setValue(char value) {
        this.value = value;
    }

    public CharTypeAST(char value) {
        super("charType");
        this.value = value;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        boolean flag = false;
        for(Token token: tokens){
            //System.out.println(token.getType() + ":" + token.getValue());
            if(token.getType().equals("type") && token.getValue().equals("char")){
                flag = true;

            }else if(flag && token.getType().equals("symbol")){
                this.setValue(token.getValue().charAt(0));
                parser.next(2);
                return true;
            }
        }
        return false;
    }

    @Override
    public String printAST() {
        return "[" + getType() + ":" + getValue() + "]";
    }

    public char getValue() {
        return value;
    }
}
