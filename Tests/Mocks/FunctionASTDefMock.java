package Mocks;

import com.znaka.Contracts.FunctionDefinitionASTInter;
import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.VarAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;
import java.util.List;

public class FunctionASTDefMock extends DefaultAST implements FunctionDefinitionASTInter {
    private List<DefaultAST> body;
    private String ret;
    private String name;
    private List<VarAST> args;

    public FunctionASTDefMock(String name, String ret, List<VarAST> args, List<DefaultAST> body ) {
        super("func");
        this.body = body;
        this.ret = ret;
        this.name = name;
        this.args = args;
    }

    @Override
    public List<DefaultAST> getBody() {
        return body;
    }

    @Override
    public String getRet_type() {
        return ret;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<VarAST> getArgs() {
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
