package com.znaka;

import com.znaka.ParserStructures.*;
import com.znaka.Tokens.Token;

import java.io.IOException;
import java.util.*;

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
        last_token = 0;
        if(!lexer.readLine()){
            return false;
        }
        //System.out.println(lexer.printTokens());
        ArrayList<Token> all_tokens = lexer.getTokens();
        max_token = 0;
        MainAST to_order = new MainAST(new Stack<>());
        while(max_token <= all_tokens.size()) {
            DefaultASTMatcher defaultASTMatcher = new DefaultASTMatcher(new ArrayList<DefaultAST>(), this);

            if(last_token < max_token) {
                ArrayList<Token> tokens = new ArrayList(all_tokens.subList(last_token, max_token));
                //System.out.println(tokens.get(tokens.size() - 1).getValue());

                DefaultAST defaultAST = defaultASTMatcher.match(tokens);
                if (defaultAST != null) {
                    to_order.addAST(defaultAST);
                    //System.out.println(defaultAST.printAST());
                }
            }
            max_token++;
        }
        orderAST(to_order, 0, null, mainAST);
        return true;
    }

    private DefaultAST orderAST(MainAST to_order, int level, DefaultAST last, MainAST be_ordered) {
        /*if (last != null) {
            System.out.println(last.printAST());
        }
        */

        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("call")){
                FunctionAST func = (FunctionAST) to_order.getAll_AST().get(0);
                to_order.popFrontAST(2);
                Stack<DefaultAST> args = new Stack<>();
                DefaultAST ret_type = null;
                DefaultAST result = func;
                DefaultAST temp = null;
                while(true){

                    result = orderAST(to_order, level + 1, result, be_ordered);

                    if(result != null) {
                        if (result.getType().equals("close_punc")) {
                            args.add(temp);
                            break;
                        }else{
                            if(!result.getType().equals("open_punc") && !result.getType().equals("coma")) {
                                temp = result;
                            }else if(result.getType().equals("coma")){
                                args.add(temp);
                            }
                        }
                    }

                }
                func.setArgs(args);
                if(to_order.getAll_AST().size() > 0){
                    if(to_order.getAll_AST().get(0).getType().equals("operator")){
                        DefaultAST defaultAST = orderAST(to_order, level + 1, func, be_ordered);
                        if(level == 0) {
                            be_ordered.addAST(defaultAST);
                        }
                        return defaultAST;
                    }
                }else {
                    return order_redo(to_order, level, func, be_ordered);

                }
            }
        }

        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("if")){

                IfConditionAST ifConditionAST = (IfConditionAST) to_order.getAll_AST().get(0);
                to_order.popFrontAST(2);
                DefaultAST defaultAST = to_order.getAll_AST().get(0);
                MainAST temp = new MainAST(new Stack<DefaultAST>());
                int subAST = 1;
                while(subAST > 0){
                    temp.addAST(defaultAST);
                    to_order.popFrontAST(1);
                    defaultAST = to_order.getAll_AST().get(0);
                    if(defaultAST.getType().equals("open_punc")){
                        subAST++;
                    }

                    if(defaultAST.getType().equals("close_punc")){
                        subAST--;
                    }

                }
                MainAST ordered = new MainAST(new Stack<DefaultAST>());
                orderAST(temp, 0, null, ordered);
                to_order.popFrontAST(1);
                ifConditionAST.setCondition(ordered);
                return order_redo(to_order, level, ifConditionAST, be_ordered);
            }
        }


        if(to_order.has(2)) {

            if (to_order.getAll_AST().get(0).getType().equals("assign")) {
                AssignAST assign = (AssignAST) to_order.getAll_AST().get(0);
                DefaultAST left = last;
                DefaultAST right = to_order.getAll_AST().get(1);

                assign.setLeft(left);
                to_order.popFrontAST(1);
                if(to_order.has(2)) {

                    if (to_order.getAll_AST().get(1).getType().equals("operator")) {

                        to_order.popFrontAST(1);
                        assign.setRight(orderAST(to_order, level + 1, right, be_ordered));
                        return order_redo(to_order, level, assign, be_ordered);

                    }else if(to_order.getAll_AST().get(0).getType().equals("call")){
                        assign.setRight(orderAST(to_order, level + 1, assign, be_ordered));
                        return order_redo(to_order, level, assign, be_ordered);

                    }else if(to_order.getAll_AST().get(0).getType().equals("open_punc")){
                        assign.setRight(orderAST(to_order, level + 1, assign, be_ordered));
                        return order_redo(to_order, level, assign, be_ordered);

                    }
                    else {
                        assign.setRight(right);
                        to_order.popFrontAST(1);

                        return order_redo(to_order, level, assign, be_ordered);

                    }
                }else {

                    assign.setRight(right);

                    to_order.popFrontAST(1);

                    return order_redo(to_order, level, assign, be_ordered);

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

                        to_order.popFrontAST(1);
                        operatorAST.setRight(orderAST(to_order ,level + 1, right, be_ordered));
                        return order_redo(to_order, level, operatorAST, be_ordered);


                    }else if(to_order.getAll_AST().get(0).getType().equals("call")){
                        operatorAST.setRight(orderAST(to_order, level + 1, operatorAST, be_ordered));
                        return order_redo(to_order, level, operatorAST, be_ordered);

                    }else if(to_order.getAll_AST().get(0).getType().equals("open_punc")){
                        operatorAST.setRight(orderAST(to_order, level + 1, operatorAST, be_ordered));
                        return order_redo(to_order, level, operatorAST, be_ordered);

                    }
                    else {
                        operatorAST.setRight(right);
                        to_order.popFrontAST(1);

                        return order_redo(to_order, level, operatorAST, be_ordered);

                    }
                }else {

                    operatorAST.setRight(right);
                    to_order.popFrontAST(1);
                    return order_redo(to_order, level, operatorAST, be_ordered);

                }

            }
        }

        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("open_punc")){
                to_order.popFrontAST(1);
                DefaultAST defaultAST = to_order.getAll_AST().get(0);
                MainAST temp = new MainAST(new Stack<DefaultAST>());
                int subAST = 1;
                while(subAST > 0){
                    temp.addAST(defaultAST);
                    to_order.popFrontAST(1);

                    defaultAST = to_order.getAll_AST().get(0);

                    if(defaultAST.getType().equals("open_punc")){
                        subAST++;
                    }

                    if(defaultAST.getType().equals("close_punc")){
                        subAST--;
                    }

                }
                MainAST ordered = new MainAST(new Stack<DefaultAST>());
                orderAST(temp, 0, null, ordered);
                to_order.popFrontAST(1);
                return order_redo(to_order, level, ordered.getAll_AST().get(0), be_ordered);

            }
        }

        if(to_order.has(1)) {

            DefaultAST defaultAST = to_order.getAll_AST().get(0);
            //System.out.println(defaultAST.printAST());
            //mainAST.addAST(defaultAST);
            to_order.popFrontAST(1);

            return order_redo(to_order, level, defaultAST, be_ordered);
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
            if(mainAST.getAll_AST().get(i).printAST() != null) {
                output = output.concat(mainAST.getAll_AST().get(i).printAST());
            }
        }
        return output;
    }
    private DefaultAST order_redo(MainAST to_order, int level, DefaultAST defaultAST, MainAST be_ordered){
        if(level == 0 && to_order.getAll_AST().size() == 0){
            be_ordered.addAST(defaultAST);
        }

        if(level == 0) {
            //mainAST.addAST(operatorAST);
            return orderAST(to_order, level, defaultAST, be_ordered);
        }else{
            return defaultAST;
        }
    }
}


