package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public class CharAST extends LiteralTypesAST {
    private char value;

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public CharAST(char value) {
        super("char");
        this.value = value;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for (Token token : tokens) {
            //System.out.println(token.getType() + ":" + token.getValue());
            if (token.getType().equals("character")) {
                setValue(token.getValue().charAt(1));
                parser.next(1);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + getType() + ":" + getValue() + "]";
    }

    @Override
    public String getText() {
        return String.valueOf(getValue());
    }
}
