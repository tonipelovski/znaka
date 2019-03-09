package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class DefaultASTMatcher {
    private ArrayList<DefaultAST> asts;
    Parser parser;

    public DefaultASTMatcher(ArrayList<DefaultAST> asts, Parser parser) {
        this.asts = asts;
        this.parser = parser;
        asts.add(new StringTypeAST(""));
        asts.add(new CharTypeAST(""));
        asts.add(new IntTypeAST(""));
        asts.add(new BooleanTypeAST(""));
        asts.add(new NumberAST(0));
        asts.add(new CharAST(' '));
        asts.add(new BooleanAST(false));
        asts.add(new StringAST(""));
        asts.add(new AssignAST(null, null));
        asts.add(new OperatorAST(null, null));
        asts.add(new IfConditionAST(null, null, null));
        asts.add(new FunctionAST(null, null));
        asts.add(new OpenPuncAST());
        asts.add(new ClosePuncAST());
        asts.add(new ComaAST());
        asts.add(new VarAST());

    }

    public DefaultAST match(ArrayList<Token> tokens){
        for(DefaultAST defaultAST : asts){
            if(defaultAST.matchAST(tokens, parser)){
                return defaultAST;
            }
        }
        return null;
    }
}
