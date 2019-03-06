package com.znaka;

import com.znaka.ParserStructures.*;
import com.znaka.Tokens.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Parser {
    Lexer lexer;
    MainAST mainAST;
    int last_token = 0;
    int max_token = 0;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        mainAST = new MainAST(new Stack<>());
    }

    public boolean parseLIne() throws IOException {
        max_token = 0;
        last_token = 0;
        if(!lexer.readLine()){
            return false;
        }
        //System.out.println(lexer.printTokens());
        ArrayList<Token> all_tokens = lexer.getTokens();
        MainAST to_order = new MainAST(new Stack<>());
        while(max_token < all_tokens.size()) {
            DefaultASTMatcher defaultASTMatcher = new DefaultASTMatcher(new ArrayList<DefaultAST>(), this);

            if(last_token < max_token) {
                ArrayList<Token> tokens = new ArrayList(all_tokens.subList(last_token, max_token));

                DefaultAST defaultAST = defaultASTMatcher.match(tokens);
                if (defaultAST != null) {
                    to_order.addAST(defaultAST);
                    //printASTS();
                    //System.out.println(" ");
                }
            }
            max_token++;
        }
        orderAST(to_order, 0, null);
        return true;
    }

    private DefaultAST orderAST(MainAST to_order, int level, DefaultAST last) {
        //System.out.println(to_order.getAll_AST().get(0).getType());

        if(to_order.has(2)) {

            if (to_order.getAll_AST().get(0).getType().equals("assign")) {
                AssignAST assign = (AssignAST) to_order.getAll_AST().get(0);
                DefaultAST left = last;
                DefaultAST right = to_order.getAll_AST().get(1);
                assign.setLeft(left);
                to_order.popFrontAST(1);
                if(to_order.has(2)) {
                    if (to_order.getAll_AST().get(1).getType().equals("operator")) {
                        assign.setRight(orderAST(to_order, level + 1, assign));
                        if(to_order.getAll_AST().size() == 0) {
                            mainAST.addAST(assign);
                        }
                            orderAST(to_order, level + 1, assign);
                    }else {
                        assign.setRight(right);
                        to_order.popFrontAST(1);
                        if(level == 0  && to_order.getAll_AST().size() == 0) {
                            mainAST.addAST(assign);
                        }
                        return assign;
                    }
                }else {

                    assign.setRight(right);
                    to_order.popFrontAST(1);
                    if(level == 0) {
                        mainAST.addAST(assign);
                    }
                    return assign;
                }
            }
        }
        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("operator")){
                OperatorAST operatorAST = (OperatorAST) to_order.getAll_AST().get(0);
                DefaultAST left = last;
                DefaultAST right = to_order.getAll_AST().get(1);
                operatorAST.setLeft(left);
                to_order.popFrontAST(1);
                if(to_order.has(2)) {
                    if (to_order.getAll_AST().get(1).getType().equals("operator")) {
                        operatorAST.setRight(orderAST(to_order ,level + 1, operatorAST));
                        if(level == 0  && to_order.getAll_AST().size() == 0) {
                            mainAST.addAST(operatorAST);
                        }
                        if(level == 0){
                            orderAST(to_order, level, operatorAST);
                        }else {
                            orderAST(to_order, level - 1, operatorAST);
                        }
                    }else {
                        operatorAST.setRight(right);
                        to_order.popFrontAST(1);
                        if(level == 0  && to_order.getAll_AST().size() == 0) {
                            mainAST.addAST(operatorAST);
                        }
                        return operatorAST;
                    }
                }else {

                    operatorAST.setRight(right);
                    to_order.popFrontAST(1);
                    if(level == 0) {
                        mainAST.addAST(operatorAST);
                    }
                    return operatorAST;
                }

            }
        }
        if(to_order.has(1)) {

            DefaultAST defaultAST = to_order.getAll_AST().get(0);
            //mainAST.addAST(defaultAST);
            to_order.popFrontAST(1);
            if(level == 0) {
                return orderAST(to_order, 0, defaultAST);
            }else{
                return orderAST(to_order, level, defaultAST);
            }
        }else{
            return null;
        }
    }

    public void next(int index){
        last_token += index;
    }

    public String printASTS(){
        String output = "";
        for(int i = 0; i < mainAST.getAll_AST().size(); i++){
            output = output.concat(mainAST.getAll_AST().get(i).printAST());
        }
        return output;
    }

}
