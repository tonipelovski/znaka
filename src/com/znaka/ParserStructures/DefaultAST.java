package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public abstract class DefaultAST {
    private String type;

    public DefaultAST(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    abstract boolean matchAST(ArrayList<Token> tokens, Parser parsesr);

    public abstract String printAST();

    public abstract String getText();
}
