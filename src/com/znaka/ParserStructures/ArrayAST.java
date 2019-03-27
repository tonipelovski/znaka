package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class ArrayAST extends DefaultAST {
    public ArrayAST(String type) {
        super(type);
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        return false;
    }

    @Override
    public String printAST() {
        return null;
    }
}
