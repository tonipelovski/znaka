package com.znaka.ParserStructures;

import com.znaka.Contracts.FunctionDefinitionASTInter;
import com.znaka.Parser;
import com.znaka.ParserStructures.Expression.VarAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FunctionDefAST extends DefaultAST implements FunctionDefinitionASTInter {
    private String ret_type;
    private String name;
    private Stack<VarAST> args;
    private List<DefaultAST> body;

    public List<DefaultAST> getBody() {
        return body;
    }

    public void setBody(List<DefaultAST> body) {
        this.body = body;
    }

    public String getText() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public FunctionDefAST(String return_type, Stack<VarAST> arguments, List<DefaultAST> body) {
        super("def");
        this.ret_type = return_type;
        this.args = arguments;
        this.body = body;
    }

    public String getRet_type() {
        return ret_type;
    }

    @Override
    public String getName() {
        return null;
    }

    public List<VarAST> getArgs() {
        return args;
    }

    public void setRet_type(String ret_type) {
        this.ret_type = ret_type;
    }

    public void setArgs(Stack<VarAST> args) {
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
            for (DefaultAST defaultAST : getBody()) {
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
