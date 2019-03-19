package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class CloseCurlyAST extends DefaultAST {
    public CloseCurlyAST() {
        super("close_curly");
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        if(tokens.get(0).getType().equals("punc") && tokens.get(0).getValue().equals("}")){
            parser.next(1);
            return true;
        }
        return false;
    }

    @Override
    public String printAST() {
        return null;
    }

}
