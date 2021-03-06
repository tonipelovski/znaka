package com.znaka.ParserStructures.Expression;

import com.znaka.Contracts.FunctionCallASTInter;
import com.znaka.Parser;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
//add arg_count
public class FunctionCallAST extends ExpressionAST implements FunctionCallASTInter {
    private String ret_type;
    private String name;
    private List<ExpressionAST> args;
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


    public FunctionCallAST(String name, List<ExpressionAST> args) {
        super("call");
        this.name = name;
        this.args = args;
    }

    public String getRet_type() {
        return ret_type;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<ExpressionAST> getArgs() {
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

        String output = "[" + getText() + ":";
        for(int i = 0; i < getArgs().size(); i++){
            if(getArgs().get(i) != null) {
                output = output.concat(getArgs().get(i).toString());
            }
        }return output.concat("]");
    }

}
