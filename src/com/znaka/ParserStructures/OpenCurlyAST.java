package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class OpenCurlyAST extends DefaultAST {
    public OpenCurlyAST() {
        super("open_curly");
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        if(tokens.get(0).getType().equals("punc") && tokens.get(0).getValue().equals("{")){
            parser.next(1);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String getText() {
        return "{";
    }
}
