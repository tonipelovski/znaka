package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;
import java.util.Stack;

public class FunctionDefAST extends DefaultAST {
    private String ret_type;
    private String name;
    private Stack<DefaultAST> args;
    private MainAST body;

    public MainAST getBody() {
        return body;
    }

    public void setBody(MainAST body) {
        this.body = body;
    }

    public String getText() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public FunctionDefAST(String return_type, Stack<DefaultAST> arguments, MainAST body) {
        super("def");
        this.ret_type = return_type;
        this.args = arguments;
        this.body = body;
    }

    public String getRet_type() {
        return ret_type;
    }

    public Stack<DefaultAST> getArgs() {
        return args;
    }

    public void setRet_type(String ret_type) {
        this.ret_type = ret_type;
    }

    public void setArgs(Stack<DefaultAST> args) {
        this.args = args;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        boolean flag = false;
        String type = "";
        String func_name = "";
        for(Token token: tokens){
            //
            //System.out.println(token.getType() + ":" + token.getValue() + ":" + flag);

            if(token.getType().equals("type")){
                type = token.getValue();
            }else if(token.getType().equals("symbol")){
                func_name = token.getValue();
                flag = true;

            }else if(flag && token.getType().equals("punc") && token.getValue().equals("(") && !type.equals("")){
                if(type.length() > 0){
                    setRet_type(type);
                    setName(func_name);
                    parser.next(2);
                }else {
                    parser.next(1);
                    setName(func_name);
                }
                return true;
            }else{
                //System.out.println("here");
                return false;
            }

        }
        //System.out.println("end");
        return false;
    }

    @Override
    public String toString() {
        String body = "";
        if (getBody() != null) {
            for (DefaultAST defaultAST : getBody().getAll_AST()) {
                if (defaultAST != null) {
                    body = body.concat(defaultAST.toString());
                }
            }
        }
        String output = "[" + getText() + ":" + getRet_type() + ":";
        for(int i = 0; i < getArgs().size(); i++){
            if(getArgs().get(i) != null) {
                output = output.concat(getArgs().get(i).toString());
            }
        }
        output = output.concat(body);
        return output.concat("]");
    }
}
