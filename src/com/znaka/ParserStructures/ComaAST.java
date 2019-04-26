package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class ComaAST extends DefaultAST{
    public ComaAST() {
        super("coma");
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token : tokens){
            //System.out.println(token.getType());
            if(token.getType().equals("punc") && token.getValue().equals(",")){
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
        return null;
    }

    @Override
    public String getText() {
        return ",";
    }
}
