package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class DefaultASTMatcher {
    ArrayList<DefaultAST> asts;
    Parser parsesr;

    public DefaultASTMatcher(ArrayList<DefaultAST> asts, Parser parser) {
        this.asts = asts;
        this.parsesr = parser;
        asts.add(new VarAST());
        asts.add(new IntTypeAST(""));
        asts.add(new CharTypeAST(' '));
        asts.add(new BoolAST(true));
        asts.add(new NumberAST(0));
        asts.add(new StringAST(""));
        asts.add(new AssignAST(null, null));
        asts.add(new OperatorAST(null, null));
        asts.add(new FunctionAST(null, null));
        asts.add(new IfConditionAST(null, null, null));

    }

    public DefaultAST match(ArrayList<Token> tokens){
        for(DefaultAST defaultAST : asts){
            if(defaultAST.matchAST(tokens, parsesr)){
                return defaultAST;
            }
        }
        return null;
    }
}
