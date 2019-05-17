package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public abstract class KeywordAST extends DefaultAST {
    public KeywordAST(String type) {
        super(type);
    }

}
