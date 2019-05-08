package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.Expression.FunctionCallAST;
import com.znaka.ParserStructures.Expression.OperatorAST;
import com.znaka.ParserStructures.Expression.UnaryOperatorAST;
import com.znaka.ParserStructures.Statement.ElseConditionAST;
import com.znaka.ParserStructures.Statement.IfConditionAST;
import com.znaka.ParserStructures.Statement.LoopAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public class DefaultASTMatcher {
    private ArrayList<DefaultAST> asts;
    Parser parser;

    public DefaultASTMatcher(ArrayList<DefaultAST> asts, Parser parser) {
        this.asts = asts;
        this.parser = parser;

        //asts.add(new StringTypeAST(""));
        //asts.add(new CharTypeAST(""));
        //asts.add(new IntTypeAST(""));
        //asts.add(new BooleanTypeAST(""));
        asts.add(new ReturnAST(null));
        asts.add(new BreakAST());
        asts.add(new AssignAST(null, null));
        asts.add(new FunctionDefAST("", null, null));
        asts.add(new FunctionCallAST("", null, null));
        asts.add(new UnaryOperatorAST(null));
        asts.add(new OperatorAST(null, null));
        asts.add(new IfConditionAST(null, null));
        asts.add(new ElseConditionAST(null, null));

        asts.add(new LoopAST(null, null));

        asts.add(new NumberAST(""));
        asts.add(new CharAST(' '));
        asts.add(new BooleanAST(false));
        asts.add(new StringAST(""));

        asts.add(new ComaAST());

        asts.add(new OpenPuncAST());
        asts.add(new ClosePuncAST());
        asts.add(new OpenCurlyAST());
        asts.add(new CloseCurlyAST());


        asts.add(new VarAST());
        asts.add(new ArrayTypeAST());
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
