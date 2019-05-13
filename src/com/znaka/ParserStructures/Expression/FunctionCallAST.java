package com.znaka.ParserStructures.Expression;

import com.znaka.Contracts.FunctionCallASTInter;
import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;
import java.util.Stack;
//add arg_count
public class FunctionCallAST extends ExpressionAST implements FunctionCallASTInter {
    private String ret_type;
    private String name;
    private Stack<ExpressionAST> args;
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


    public FunctionCallAST(String return_type, Stack<ExpressionAST> arguments, MainAST body) {
        super("call");
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

    public Stack<ExpressionAST> getArgs() {
        return args;
    }

    public void setRet_type(String ret_type) {
        this.ret_type = ret_type;
    }

    public void setArgs(Stack<ExpressionAST> args) {
        this.args = args;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        boolean flag = false;
        String func_name = "";
        for(Token token: tokens){
            //System.out.println(token.getType() + ":" + token.getValue() + ":" + flag);

            if(token.getType().equals("type")){
                return false;
            }else if(token.getType().equals("symbol")){
                func_name = token.getValue();
                flag = true;

            }else if(flag && token.getType().equals("punc") && token.getValue().equals("(")){
                setName(func_name);
                parser.next(1);
                System.out.println("matched");
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
