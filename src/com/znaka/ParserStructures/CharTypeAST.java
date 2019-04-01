package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class CharTypeAST extends DefaultAST{
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public CharTypeAST(String value) {
        super("charType");
        this.value = value;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        boolean flag = false;
        Token last = tokens.get(0);
        IntTypeAST temp = new IntTypeAST("");
        if(tokens.size() > 2) {
            for (Token token : tokens) {
                if (last.getValue().equals("char") && last.getType().equals("type") && token.getType().equals("symbol")) {
                    temp.setValue(token.getValue());
                    flag = true;
                } else if (flag && (!token.getValue().equals("("))) {
                    //System.out.println(token.getValue());

                    this.setValue(temp.getValue());
                    parser.next(2);
                    return true;
                } else {
                    flag = false;
                }
                last = token;
            }
            if (flag) {
                this.setValue(temp.getValue());
                parser.next(2);
                return true;
            }
        }
        return false;
    }

    @Override
    public String printAST() {
        return "[" + getType() + ":" + getValue() + "]";
    }

    public String getValue() {
        return value;
    }
}
