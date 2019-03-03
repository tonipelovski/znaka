package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;
import java.util.Stack;
//add arg_count
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

    public void setRet_type(DefaultAST ret_type) {
        this.ret_type = ret_type;
    }

    public void setArgs(Stack<DefaultAST> args) {
        this.args = args;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        for(Token token: tokens){
            if(token.getType().equals("symbol")){
                if(token.getType().equals("punc") && token.getValue().equals("(")){
                    this.args = null;
                    this.ret_type = null;
                    parser.next(2);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String printAST() {
        return "[" + getType() + ":" + getRet_type() + ":" + getArgs() + "]";
    }

}
