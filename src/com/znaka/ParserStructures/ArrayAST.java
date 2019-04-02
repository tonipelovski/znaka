package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.Token;

import java.util.ArrayList;

public class ArrayAST extends DefaultAST {
    private String type;
    private String name;
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayAST() {
        super("array");
        this.type = "";
        this.name = "";
        this.size = 0;
    }

    @Override
    boolean matchAST(ArrayList<Token> tokens, Parser parser) {
        boolean flag_symbol = false;
        String value = "";
        String t = "";
        int i = 0;

        //System.out.println("start");
        if (tokens.get(i).getType().equals("type")) {
            t = tokens.get(i).getValue();
            i++;

        }
        if(tokens.size() > i + 3) {
            for (; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                if (token.getValue().equals("[") && flag_symbol) {
                    i++;
                    Token number = tokens.get(i);
                    i++;

                    this.setName(value);
                    this.setType(t);
                    this.setSize(Integer.parseInt(number.getValue()));
                    if(!t.equals("")) {
                        parser.next(5);
                    }else{
                        parser.next(4);
                    }
                    return true;

                } else if (token.getType().equals("symbol")) {

                    flag_symbol = true;
                    value = token.getValue();

                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    @Override
    public String printAST() {
        return "[array" + ":" + getType() + ":" + getName() + ":index:" + getSize() + "]";
    }
}
