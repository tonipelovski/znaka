package com.znaka.ParserStructures;

import java.util.Stack;

public class FunctionAST extends DefaultAST{
    private DefaultAST ret_type;
    private Stack<DefaultAST> args;

    public FunctionAST(DefaultAST return_type, Stack<DefaultAST> arguments) {
        super("call");
        this.ret_type = return_type;
        this.args = arguments;
    }

    public DefaultAST getRet_type() {
        return ret_type;
    }

    public Stack<DefaultAST> getArgs() {
        return args;
    }

    @Override
    boolean matchAST(DefaultAST ast) {
        if(ast.getType().equals(this.getType())){
            return true;
        }else {
            return false;
        }
    }

}
