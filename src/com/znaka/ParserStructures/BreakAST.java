package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public class BreakAST extends KeywordAST {
    public BreakAST() {
        super("break");
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            if(token.getType().equals("keyword") && token.getValue().equals("break")){
                parser.next(1);
                return true;
            }else {
                return false;
            }
        }
        return false;    }

    @Override
    public String getText() {
        return null;
    }
}
