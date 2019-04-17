package com.znaka;

import com.znaka.Exceptions.LexerException;
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

    public boolean parseLIne() throws IOException, LexerException {
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
                DefaultAST defaultAST = defaultASTMatcher.match(tokens);
                if (defaultAST != null) {
                    to_order.addAST(defaultAST);
                }
            }
            max_token++;
        }
        DefaultASTMatcher defaultASTMatcher = new DefaultASTMatcher(new ArrayList<DefaultAST>(), this);

        if(last_token < max_token - 1){
            ArrayList<Token> tokens = new ArrayList(all_tokens.subList(last_token, max_token - 1));

            DefaultAST defaultAST = defaultASTMatcher.match(tokens);
            if (defaultAST != null) {
                to_order.addAST(defaultAST);
                //System.out.println(defaultAST);
            }
        }
        orderAST(to_order, 0, null, mainAST);
        return true;
    }

    private DefaultAST orderAST(MainAST to_order, int level, DefaultAST last, MainAST be_ordered) throws IOException, LexerException {
        /*if (last != null) {
            System.out.println(last.printAST());
        }
        */

        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("open_curly")){
                DefaultAST openCurlyAST = to_order.getAll_AST().get(0);
                to_order.popFrontAST(1);
                ArrayAST asts = new ArrayAST(new Stack<>());
                DefaultAST result = openCurlyAST;
                while(true){
                    result = to_order.getAll_AST().get(0);

                    if(result != null) {
                        if (result.getType().equals("close_curly")) {
                            break;
                        }else{
                            if(!result.getType().equals("open_curly") && !result.getType().equals("coma")) {
                                asts.addAST(result);
                            }
                        }
                    }
                    to_order.popFrontAST(1);
                }
                to_order.popFrontAST(1);
                return order_redo(to_order, level, asts, be_ordered);
            }
        }


        if(to_order.has(1)){
            if(to_order.getAll_AST().get(0).getType().equals("close_curly")){
                DefaultAST closeCurlyAST = to_order.getAll_AST().get(0);
                to_order.popFrontAST(1);
                be_ordered.addAST(closeCurlyAST);
                return order_redo(to_order, level, closeCurlyAST, be_ordered);
            }
        }

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
                MainAST asts = new MainAST(new Stack<DefaultAST>());
                if(to_order.has(1)) {
                    if (to_order.getAll_AST().get(0).getType().equals("open_curly")) {
                        Parser temp_parser = new Parser(lexer);

                        while (temp_parser.parseLIne()) {
                            //System.out.println("cccc");

                            for (DefaultAST defaultAST1 : temp_parser.mainAST.getAll_AST()) {
                                //System.out.println("ala: " + defaultAST1.printAST());
                                if (defaultAST1.getType().equals("close_curly")) {
                                    to_order.popFrontAST(1);
                                    func.setBody(asts);
                                    be_ordered.addAST(func);
                                    be_ordered.addAST(temp_parser.mainAST);
                                    temp_parser.mainAST.getAll_AST().clear();
                                    return null;
                                } else {
                                    temp_parser.mainAST.popFrontAST(1);
                                    asts.addAST(defaultAST1);

                                }
                            }
                        }
                    }
                    else if (to_order.getAll_AST().get(0).getType().equals("operator")) {
                        DefaultAST defaultAST = orderAST(to_order, level + 1, func, be_ordered);
                        if (level == 0) {
                            be_ordered.addAST(defaultAST);
                        }
                        return defaultAST;

                    }else{
                        return order_redo(to_order, level, func, be_ordered);

                    }
                }else{
                    return order_redo(to_order, level, func, be_ordered);

                }
            }
        }

        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("conditional")){

                ConditionalsAST conditionalAST = (ConditionalsAST) to_order.getAll_AST().get(0);
                to_order.popFrontAST(2);
                DefaultAST defaultAST = null;
                MainAST temp = new MainAST(new Stack<DefaultAST>());
                int subAST = 1;
                while(subAST > 0){
                    defaultAST = to_order.getAll_AST().get(0);
                    if(defaultAST.getType().equals("open_punc")){
                        subAST++;
                    }

                    if(defaultAST.getType().equals("close_punc")){
                        subAST--;
                    }
                    if(subAST > 0) {
                        to_order.popFrontAST(1);
                        temp.addAST(defaultAST);
                        //System.out.println(defaultAST.getType() + subAST);

                    }
                }
//                temp.addAST(defaultAST);

                MainAST ordered = new MainAST(new Stack<DefaultAST>());
                orderAST(temp, 0, null, ordered);
                to_order.popFrontAST(1);
                conditionalAST.setCond(ordered);

                MainAST asts = new MainAST(new Stack<>());
                return getBody(to_order, level, last, be_ordered, conditionalAST, asts);

            }
        }

       if(to_order.has(2)) {

            if (to_order.getAll_AST().get(0).getType().equals("operator")) {
                BasicOperators assign = (BasicOperators) to_order.getAll_AST().get(0);
                if(assign.getOperator().equals("=")) {
                    DefaultAST left = last;
                    DefaultAST right = to_order.getAll_AST().get(1);

                    assign.setLeft(left);
                    to_order.popFrontAST(1);
                    return getRight(to_order, level, last, be_ordered, assign, right);
                }
            }
        }

        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("operator")){
                BasicOperators operatorAST = (BasicOperators) to_order.getAll_AST().get(0);
                if(!operatorAST.getOperator().equals("=")) {

                    DefaultAST left = last;
                    DefaultAST right = to_order.getAll_AST().get(1);
                    operatorAST.setLeft(left);
                    to_order.popFrontAST(1);
                    //System.out.println("debaaa" + operatorAST.printAST());

                    return getRight(to_order, level, last, be_ordered, operatorAST, right);
                }
            }
        }

        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("open_punc")){
                to_order.popFrontAST(1);
                DefaultAST defaultAST = null;
                MainAST temp = new MainAST(new Stack<DefaultAST>());
                //System.out.println(defaultAST.printAST() + to_order.getAll_AST().size());

                int subAST = 1;
                while(subAST > 0){

                    defaultAST = to_order.getAll_AST().get(0);
                    //System.out.println(defaultAST.getType() + to_order.getAll_AST().size());

                    if(defaultAST.getType().equals("open_punc")){
                        subAST++;
                    }

                    if(defaultAST.getType().equals("close_punc")){
                        subAST--;
                    }
                    if(subAST > 0) {
                        to_order.popFrontAST(1);
                        temp.addAST(defaultAST);
                    }
                }
                //System.out.println(temp.getAll_AST().get(temp.getAll_AST().size() -1).getType());

                MainAST ordered = new MainAST(new Stack<DefaultAST>());
                orderAST(temp, 0, null, ordered);
                //System.out.println(ordered.getAll_AST().get(0).printAST());
                to_order.popFrontAST(1);
                if(to_order.has(2)) {

                    if ("operator".equals(to_order.getAll_AST().get(0).getType())) {
                        MainAST new_ordered = new MainAST(new Stack<DefaultAST>());

                        ordered.addAST(orderAST(to_order, level + 1, ordered.getAll_AST().get(0), be_ordered));

                        DefaultAST defaultAST1 = order_redo(to_order, level, ordered.getAll_AST().get(ordered.getAll_AST().size() - 1), be_ordered);
                        return order_redo(to_order, level, defaultAST1, be_ordered);
                    }
                    return order_redo(to_order, level, ordered.getAll_AST().get(ordered.getAll_AST().size() - 1), be_ordered);
                }else {
                    return order_redo(to_order, level, ordered.getAll_AST().get(ordered.getAll_AST().size() - 1), be_ordered);

                }

            }
        }

        if(to_order.has(1)){
            if(to_order.getAll_AST().get(0).getType().equals("coma")){
                DefaultAST comaAST = to_order.getAll_AST().get(0);
                to_order.popFrontAST(1);
                if(level == 0) {
                    be_ordered.addAST(last);
                }
                return order_redo(to_order, level, comaAST, be_ordered);
            }
        }

        if(to_order.has(1)) {

            DefaultAST defaultAST = to_order.getAll_AST().get(0);
            //System.out.println(defaultAST.printAST());
            //mainAST.addAST(defaultAST);
            to_order.popFrontAST(1);

            return order_redo(to_order, level, defaultAST, be_ordered);
        }else{
            //System.out.println();
            //printASTS();
            return null;
        }
    }


    public void next(int index){
        last_token += index;
    }

    public String printASTS(){
        String output = mainAST.printAST();
        for(int i = 0; i < mainAST.getAll_AST().size(); i++){
            if(mainAST.getAll_AST().get(i).printAST() != null) {
                output = output.concat(mainAST.getAll_AST().get(i).printAST());
            }
        }
        return output;
    }
    private DefaultAST order_redo(MainAST to_order, int level, DefaultAST defaultAST, MainAST be_ordered) throws IOException, LexerException {
        if(level == 0 && to_order.getAll_AST().size() == 0){
            be_ordered.addAST(defaultAST);
            //System.out.println(printASTS());

        }

        if(level == 0) {
            //mainAST.addAST(operatorAST);
            return orderAST(to_order, level, defaultAST, be_ordered);
        }else{
            return defaultAST;
        }
    }

    private DefaultAST getRight(MainAST to_order, int level, DefaultAST last, MainAST be_ordered, BasicOperators basicOperators, DefaultAST right) throws IOException, LexerException {
        if(to_order.has(2)) {

            if (to_order.getAll_AST().get(1).getType().equals("operator")) {
                BasicOperators next = (BasicOperators) to_order.getAll_AST().get(1);
                if(!next.getOperator().equals("=")) {
                    to_order.popFrontAST(1);
                    basicOperators.setRight(orderAST(to_order, level + 1, right, be_ordered));
                    return order_redo(to_order, level, basicOperators, be_ordered);

                }else{
                    basicOperators.setRight(right);
                    to_order.popFrontAST(1);

                    return order_redo(to_order, level, basicOperators, be_ordered);
                }
            }else if(to_order.getAll_AST().get(0).getType().equals("call")){
                basicOperators.setRight(orderAST(to_order, level + 1, basicOperators, be_ordered));
                return order_redo(to_order, level, basicOperators, be_ordered);

            }else if(to_order.getAll_AST().get(0).getType().equals("open_punc")){
                basicOperators.setRight(orderAST(to_order, level + 1, basicOperators, be_ordered));
                return order_redo(to_order, level, basicOperators, be_ordered);

            }else if(to_order.getAll_AST().get(0).getType().equals("open_curly")){
                basicOperators.setRight(orderAST(to_order, level + 1, basicOperators, be_ordered));
                return order_redo(to_order, level, basicOperators, be_ordered);

            }
            else {
                basicOperators.setRight(right);
                to_order.popFrontAST(1);

                return order_redo(to_order, level, basicOperators, be_ordered);

            }
        }else {

            basicOperators.setRight(right);
            to_order.popFrontAST(1);
            return order_redo(to_order, level, basicOperators, be_ordered);

        }
    }


    private DefaultAST getBody(MainAST to_order, int level, DefaultAST last, MainAST be_ordered, ConditionalsAST conditionalsAST, MainAST asts) throws IOException, LexerException {
        if(!to_order.has(2)) {
            Parser temp_parser = new Parser(lexer);

            while(temp_parser.parseLIne()){
                //System.out.println("cccc");
                for(DefaultAST defaultAST1 : temp_parser.mainAST.getAll_AST()){
                    if(defaultAST1.getType().equals("close_curly")){
                        to_order.popFrontAST(1);
                        //System.out.println(asts.getAll_AST().get(1));

                        conditionalsAST.setBody(asts);
                        be_ordered.addAST(conditionalsAST);
                        be_ordered.addAST(temp_parser.mainAST);
                        temp_parser.mainAST.getAll_AST().clear();
                        return null;
                    }else if(!defaultAST1.getType().equals("open_curly")){
                        temp_parser.mainAST.popFrontAST(1);
                        asts.addAST(defaultAST1);

                    }
                }
            }
        }else{
            orderAST(to_order, 0, null, asts);
            conditionalsAST.setBody(asts);
            return order_redo(to_order, level, conditionalsAST, be_ordered);

        }
        return order_redo(to_order, level, conditionalsAST, be_ordered);
    }
}


