package com.znaka.ParserStructures.Statement;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public abstract class ConditionalsAST extends StatementAST {
    private MainAST cond;
    private MainAST body;

    public ConditionalsAST(MainAST cond, MainAST body) {
        super("conditional");
        this.cond = cond;
        this.body = body;
    }

    public MainAST getCond() {
        return cond;
    }

    public void setCond(MainAST cond) {
        this.cond = cond;
    }

    public MainAST getBody() {
        return body;
    }

    public void setBody(MainAST body) {
        this.body = body;
    }

    protected abstract boolean matchAST(ArrayList<Token> tokens, Parser parsesr);

    @Override
    abstract public String printAST();
}
