package Mocks;

import com.znaka.Contracts.FunctionCallASTInter;
import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.ExpressionAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallAstMock extends DefaultAST implements FunctionCallASTInter {
    private String name;
    private List<ExpressionAST> args;

    public FunctionCallAstMock(String name, List<ExpressionAST> args) {
        super("call");
        this.name = name;
        this.args = args;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ExpressionAST> getArgs() {
        return args;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parsesr) {
        return false;
    }

    @Override
    public String getText() {
        return null;
    }
}
