package com.znaka.ParserStructures.Statement;

import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public abstract class ConditionalsAST extends StatementAST {
    private MainAST cond;
    private MainAST body;
    private ConditionalsAST else_cond;

    public ConditionalsAST getElse_cond() {
        return else_cond;
    }

    public void setElse_cond(ConditionalsAST else_cond) {
        this.else_cond = else_cond;
    }

    public ConditionalsAST(MainAST cond, MainAST body, ConditionalsAST else_cond) {
        super("conditional");
        this.cond = cond;
        this.body = body;
        this.else_cond = else_cond;
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

}
