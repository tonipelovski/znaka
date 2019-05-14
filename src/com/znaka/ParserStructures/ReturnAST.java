package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public class ReturnAST extends KeywordAST {
    private DefaultAST toReturn;

    public ReturnAST(DefaultAST toReturn) {
        this.toReturn = toReturn;
    }

    public DefaultAST getToReturn() {
        return toReturn;
    }

    public void setToReturn(DefaultAST toReturn) {
        this.toReturn = toReturn;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {

        for(Token token: tokens){
            if(token.getType().equals("keyword") && token.getValue().equals("ret")){
                parser.next(1);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getText() {
        return "ret";
    }

    @Override
    public String toString() {
        String toReturnString = "";
        if(getToReturn() != null){
            toReturnString = getToReturn().toString();
        }
        return "[" +
                "return:" + toReturnString +
                ']';
    }
}
