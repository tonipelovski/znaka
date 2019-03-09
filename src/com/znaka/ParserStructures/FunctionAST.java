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
        boolean flag = false;
        for(Token token: tokens){
            //
            //System.out.println(token.getType() + ":" + token.getValue() + ":" + flag);

            if(token.getType().equals("symbol")){
                flag = true;

            }else if(flag && token.getType().equals("punc") && token.getValue().equals("(")){
                parser.next(1);
                return true;
            }else{
                //System.out.println("here");
                flag = false;
            }

        }
        //System.out.println("end");
        return false;
    }

    @Override
    public String printAST() {
        String output = "\n[" + getType() + ":" + getRet_type() + ":";
        for(int i = 0; i < getArgs().size(); i++){
            output = output.concat(getArgs().get(i).printAST());
        }
        return output;
    }

}
