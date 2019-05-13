package com.znaka.ParserStructures.Expression;

import com.znaka.Parser;
import com.znaka.ParserStructures.Expression.ExpressionAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public class VarAST extends ExpressionAST {
    private String type;
    private String accessType;
    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public VarAST() {
        super("var");
        this.type = "";
        this.accessType = "";
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getVariableType() {
        return type;
    }

    public void setVariableType(String type) {
        this.type = type;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        boolean flag_punc = false;
        boolean flag_symbol = false;
        String value = "";
        String t = "";
        String at = "";
        int i = 0;

        if(tokens.size() >= 2) {
            if (tokens.get(i).getType().equals("access")) {
                //System.out.println("start");

                at = tokens.get(i).getValue();
                i++;

            }
            if (tokens.get(i).getType().equals("type")) {
                //System.out.println("start type");

                t = tokens.get(i).getValue();
                i++;

            }
        }
        //System.out.println( tokens.get(i).getValue());
        if(tokens.size() > i + 1 || parser.parsedAllTokens(parser.getLexer().getTokens())) {
            for (; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                if (token.getValue().equals("(") || token.getValue().equals("[")) {
                     return false;
                } else if (token.getType().equals("symbol") && !flag_symbol) {
                    flag_symbol = true;
                    value = token.getValue();

                 }
            }
        }
        if(flag_symbol){
            //System.out.println(i);

            this.setName(value);
            this.setVariableType(t);
            this.setAccessType(at);
            int next = 1;
            if(!t.equals("")) {
                next += 1;
            }

            if(!at.equals("")){
                next+=1;
            }
            parser.next(next);
            return true;
        }
        return false;
    }

    public String getText() {
        return name;
    }

    @Override
    public String toString() {
         return "[var" + ":" + getVariableType() + ":" + getText() + "]";
    }
}
