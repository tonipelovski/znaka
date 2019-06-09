package com.znaka;

import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.ParserStructures.*;
import com.znaka.ParserStructures.Expression.*;
import com.znaka.ParserStructures.Statement.ConditionalsAST;
import com.znaka.ParserStructures.Statement.ElseConditionAST;
import com.znaka.ParserStructures.Statement.IfConditionAST;
import com.znaka.Tokens.TokenMatches.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//import sun.security.krb5.internal.PAEncTSEnc;
public class Parser {
    Lexer lexer;
    public MainAST mainAST;
    private int last_token = 0;
    private int max_token = 0;
    private boolean error = false;
    private String line = "";
    private int indexOf;
    private String last_line = "";

    public Lexer getLexer() {
        return lexer;
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        mainAST = new MainAST(new Stack<>());
    }

    public DefaultAST getLastAst(){
        return mainAST.getAll_AST().get(mainAST.getAll_AST().size() - 1);
    }

    public boolean parsedAllTokens(ArrayList<Token> tokens){
        return tokens.size() == max_token;
    }

    public boolean parseLine() throws IOException, LexerException, ParserException {
        error = false;
        last_token = 0;
        if(!lexer.readLine()){
            return false;
        }
        line = lexer.getLast_line();
        last_line = line;
        indexOf = 0;
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
                    //System.out.println(defaultAST.getType());
                    max_token--;
                }
            }
            //System.out.println(last_token + " " + max_token);
            max_token++;

        }
        //System.out.println("line");

        orderAST(to_order, 0, null, mainAST);
        return true;
    }

    private DefaultAST orderAST(MainAST to_order, int level, DefaultAST last, MainAST be_ordered) throws IOException, LexerException, ParserException {

        if(to_order.has(2)){
            if(to_order.getAll_AST().get(0).getType().equals("open_curly")){
                error = false;
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
            if(to_order.getAll_AST().get(0).getType().equals("call")){

                FunctionCallAST func = (FunctionCallAST) to_order.getAll_AST().get(0);
                to_order.popFrontAST(1);
                MainAST temp = new MainAST(new Stack<>());
                DefaultAST defaultASTTemp = null;
                int subAST = 1;
                to_order.popFrontAST(1);

                while (subAST > 0) {
                    defaultASTTemp = to_order.getAll_AST().get(0);
                    if (defaultASTTemp.getType().equals("open_punc")) {
                        subAST++;
                    }

                    if (defaultASTTemp.getType().equals("close_punc")) {
                        subAST--;
                    }
                    if (subAST > 0) {
                        to_order.popFrontAST(1);
                        temp.addAST(defaultASTTemp);
                        //System.out.println(defaultASTTemp.toString() + subAST);

                    }
                }
//                temp.addAST(defaultAST);

                MainAST ordered = new MainAST(new Stack<DefaultAST>());
                orderAST(temp, 0, null, ordered);
                to_order.popFrontAST(1);
                Stack<ExpressionAST> list = new Stack<>();
                for(DefaultAST defaultAST1 : ordered.getAll_AST()){
                    list.add((ExpressionAST) defaultAST1);
                }
                func.setArgs(list);
                MainAST asts = new MainAST(new Stack<DefaultAST>());
                //System.out.println(func.getRet_type());
                if(to_order.has(1)) {
                    if (to_order.getAll_AST().get(0).getType().equals("open_curly")) {

                        Parser temp_parser = new Parser(lexer);

                        while (temp_parser.parseLine()) {
                            //System.out.println("cccc");

                            for (DefaultAST defaultAST1 : temp_parser.mainAST.getAll_AST()) {
                                //System.out.println("ala: " + defaultAST1.toString());
                                if (defaultAST1.getType().equals("close_curly")) {
                                    temp_parser.mainAST.popFrontAST(1);
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

            if(to_order.getAll_AST().get(0).getType().equals("def")){

                FunctionDefAST func = (FunctionDefAST) to_order.getAll_AST().get(0);
                to_order.popFrontAST(2);
                Stack<VarAST> args = new Stack<>();
                DefaultAST result = func;
                DefaultAST temp = null;
                while(true){
                    error = false;

                    result = orderAST(to_order, level + 1, result, be_ordered);

                    if(result != null) {
                        if (result.getType().equals("close_punc")) {
                            args.add((VarAST) temp);
                            break;
                        }else{
                            if(!result.getType().equals("open_punc") && !result.getType().equals("coma")) {
                                temp = result;
                            }else if(result.getType().equals("coma")){
                                args.add((VarAST) temp);
                            }
                        }
                    }
                    error = false;
                }
                func.setArgs(args);
                MainAST asts = new MainAST(new Stack<DefaultAST>());
                //System.out.println(func.getRet_type());

                return getFuncBody(to_order, level, last, be_ordered, func, asts);

            }

            if(to_order.getAll_AST().get(0).getType().equals("conditional")){
                error = false;

                ConditionalsAST conditionalAST = (ConditionalsAST) to_order.getAll_AST().get(0);
                to_order.popFrontAST(1);
                DefaultAST defaultAST = to_order.getAll_AST().get(0);
                MainAST temp = new MainAST(new Stack<DefaultAST>());
                //System.out.println(defaultAST.getType());

                if(defaultAST.getType().equals("open_punc")) {
                    int subAST = 1;
                    to_order.popFrontAST(1);

                    while (subAST > 0) {
                        defaultAST = to_order.getAll_AST().get(0);
                        if (defaultAST.getType().equals("open_punc")) {
                            subAST++;
                        }

                        if (defaultAST.getType().equals("close_punc")) {
                            subAST--;
                        }
                        if (subAST > 0) {
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
                }
                MainAST asts = new MainAST(new Stack<>());
                return getBody(to_order, level, last, be_ordered, conditionalAST, asts);

            }
            if(to_order.getAll_AST().get(0).getType().equals("open_punc")){
                error = false;
                to_order.popFrontAST(1);
                DefaultAST defaultAST = null;
                MainAST temp = new MainAST(new Stack<DefaultAST>());
                //System.out.println(defaultAST.toString() + to_order.getAll_AST().size());

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

                MainAST ordered = new MainAST(new Stack<DefaultAST>());
                orderAST(temp, 0, null, ordered);
                //System.out.println("here" + ordered.getAll_AST().get(0).toString());
                to_order.popFrontAST(1);
                if(to_order.has(2)) {
                    if (to_order.getAll_AST().get(0).getType().equals("operator")) {
                        ordered.addAST(orderAST(to_order, level + 1, ordered.getAll_AST().get(0), be_ordered));
                        DefaultAST defaultAST1 = ordered.getAll_AST().get(1);

                        return order_redo(to_order, level, defaultAST1, be_ordered);
                    }
                    return order_redo(to_order, level, ordered.getAll_AST().get(ordered.getAll_AST().size() - 1), be_ordered);
                }else {
                    return order_redo(to_order, level, ordered.getAll_AST().get(ordered.getAll_AST().size() - 1), be_ordered);

                }

            }
        }

        if(to_order.has(1)) {
            //System.out.println(to_order.getAll_AST().get(0).getType());

            if(to_order.getAll_AST().get(0).getText() != null) {
                if (line.contains(to_order.getAll_AST().get(0).getText())) {
                    indexOf += line.indexOf(to_order.getAll_AST().get(0).getText()) + 1;
                    line = last_line.substring(indexOf);
                    //System.out.println(line + " "  + error + " " + indexOf + " " + to_order.getAll_AST().get(0).getText());

                }
            }

            if (to_order.getAll_AST().get(0).getType().equals("operator")  && to_order.getAll_AST().get(0) instanceof UnaryOperatorAST) {

                //System.out.println("bala");
                UnaryOperatorAST unaryOperatorAST = (UnaryOperatorAST) to_order.getAll_AST().get(0);
                error = false;
                if (unaryOperatorAST.getOperator().equals("!")) {
                    to_order.popFrontAST(1);
                    unaryOperatorAST.setLeft(orderAST(to_order, level + 1, to_order.getAll_AST().get(0), be_ordered));
                    return order_redo(to_order, level, unaryOperatorAST, be_ordered);
                }else {
                    DefaultAST left = last;
                    unaryOperatorAST.setLeft(left);
                    to_order.popFrontAST(1);
                    return order_redo(to_order, level, unaryOperatorAST, be_ordered);
                }
            }
            if (to_order.getAll_AST().get(0).getType().equals("operator")) {
                error = false;
                BasicOperators operatorAST = (BasicOperators) to_order.getAll_AST().get(0);

                to_order.popFrontAST(1);
                //if(!operatorAST.getOperator().equals("=")) {

                DefaultAST left = last;
                if(left != null) {
                    if (to_order.has(1)) {
                        DefaultAST right = to_order.getAll_AST().get(0);
                        operatorAST.setLeft(left);
                        //System.out.println("debaaa" + operatorAST.toString());

                        return getRight(to_order, level, last, be_ordered, operatorAST, right);
                    } else {
                        StringBuffer outputBuffer = new StringBuffer();
                        String message = "Expected right: ";
                        for (int i = 0; i < indexOf + message.length(); i++) {
                            outputBuffer.append(" ");
                        }
                        throw new ParserException("\n" + message + last_line + "\n" + outputBuffer.toString() + "^");
                    }
                }else{
                    operatorAST.setRight(orderAST(to_order, level + 1, operatorAST, be_ordered));
                    return order_redo(to_order, level, operatorAST, be_ordered);
                }
                //}
            }
            if(to_order.getAll_AST().get(0).getType().equals("close_curly")){
                error = false;
                DefaultAST closeCurlyAST = to_order.getAll_AST().get(0);
                to_order.popFrontAST(1);
                if(last != null) {
                    be_ordered.addAST(last);
                }
                be_ordered.addAST(closeCurlyAST);
               //System.out.println("From close curly: ");
                return order_redo(to_order, level, closeCurlyAST, be_ordered);
            }

            if(to_order.getAll_AST().get(0) instanceof KeywordAST ){
                error = false;
                DefaultAST keywordAST = to_order.getAll_AST().get(0);
                to_order.popFrontAST(1);
                if(keywordAST instanceof ReturnAST){
                    last = to_order.getAll_AST().get(0);
                    //System.out.println(last.getType());
                    if(to_order.has(2)) {
                        //System.out.println("ala" + to_order.getAll_AST().get(1).getType());

                        if (to_order.getAll_AST().get(1).getType().equals("operator")) {

                            BasicOperators next = (BasicOperators) to_order.getAll_AST().get(1);

                            if(!next.getOperator().equals("=")) {
                                to_order.popFrontAST(1);
                                ((ReturnAST) keywordAST).setToReturn(orderAST(to_order, level + 1, last, be_ordered));
                                return order_redo(to_order, level, keywordAST, be_ordered);

                            }else{
                                ((ReturnAST) keywordAST).setToReturn(last);
                                to_order.popFrontAST(1);

                                return order_redo(to_order, level, keywordAST, be_ordered);
                            }
                        }else if(to_order.getAll_AST().get(0).getType().equals("operator")) {
                            //BasicOperators defaultAST = (BasicOperators) to_order.getAll_AST().get(0);
                            ((ReturnAST) keywordAST).setToReturn(orderAST(to_order, level + 1, null, be_ordered));
                            //System.out.println("operator" + basicOperators.toString());
                            //to_order.popFrontAST(1);
                            return order_redo(to_order, level, keywordAST, be_ordered);
                        }else {
                            //error = true;
                            ((ReturnAST) keywordAST).setToReturn(orderAST(to_order, level + 1, keywordAST, be_ordered));
                            to_order.popFrontAST(1);

                            return order_redo(to_order, level, keywordAST, be_ordered);

                        }
                    }else {
                        ((ReturnAST) keywordAST).setToReturn(orderAST(to_order, level + 1, keywordAST, be_ordered));
                        to_order.popFrontAST(1);
                        return order_redo(to_order, level, keywordAST, be_ordered);

                    }
                }else {
                    be_ordered.addAST(keywordAST);
                    return order_redo(to_order, level, keywordAST, be_ordered);
                }
            }

            if(to_order.getAll_AST().get(0).getType().equals("coma")){
                error = false;
                DefaultAST comaAST = to_order.getAll_AST().get(0);
                to_order.popFrontAST(1);
                if(level == 0) {
                    be_ordered.addAST(last);
                }
                return order_redo(to_order, level, comaAST, be_ordered);
            }
            DefaultAST defaultAST = to_order.getAll_AST().get(0);
            if(error){
                StringBuffer outputBuffer = new StringBuffer();
                String message = "Expected operator: ";
                for (int i = 0; i < indexOf + message.length() - 1; i++){
                    outputBuffer.append(" ");
                }
                throw new ParserException("\n" + message + last_line + "\n" + outputBuffer.toString() + "^");
            }else {
                to_order.popFrontAST(1);
                error = true;
                return order_redo(to_order, level, defaultAST, be_ordered);
            }
        }else{
            return null;
        }
    }


    public void next(int index){
        last_token += index;
    }

    public String toString(){
        String output = mainAST.toString();
        for(int i = 0; i < mainAST.getAll_AST().size(); i++){
            if(mainAST.getAll_AST().get(i) != null) {
                if(mainAST.getAll_AST().get(i).toString() != null) {
                    output = output.concat(mainAST.getAll_AST().get(i).toString());
                }
            }
        }
        return output;
    }
    private DefaultAST order_redo(MainAST to_order, int level, DefaultAST defaultAST, MainAST be_ordered) throws IOException, LexerException, ParserException {
        if(level == 0 && to_order.getAll_AST().size() == 0 && defaultAST != null){
            error = false;
            be_ordered.addAST(defaultAST);
            //System.out.println(toString());
        }

        if(level == 0) {
            //mainAST.addAST(operatorAST);
            return orderAST(to_order, level, defaultAST, be_ordered);
        }else{
            return defaultAST;
        }
    }

    private DefaultAST getRight(MainAST to_order, int level, DefaultAST last, MainAST be_ordered, BasicOperators basicOperators, DefaultAST right) throws IOException, LexerException, ParserException {

        if(to_order.has(2)) {
            //System.out.println("ala" + to_order.getAll_AST().get(1).getType());

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
            }else if(to_order.getAll_AST().get(0).getType().equals("operator")) {
                //BasicOperators defaultAST = (BasicOperators) to_order.getAll_AST().get(0);
                basicOperators.setRight(orderAST(to_order, level + 1, null, be_ordered));

                //System.out.println("operator" + basicOperators.toString());
                //to_order.popFrontAST(1);
                return order_redo(to_order, level, basicOperators, be_ordered);
            }/*else if(to_order.getAll_AST().get(1).getType().equals("close_curly")){
                System.out.println("close_curly");
                basicOperators.setRight(right);
                to_order.popFrontAST(1);

                return order_redo(to_order, level, basicOperators, be_ordered);

            }*/else {
                //System.out.println(to_order.getAll_AST().get(0).getType());

                //error = true;
                basicOperators.setRight(orderAST(to_order, level + 1, basicOperators, be_ordered));
                //to_order.popFrontAST(1);

                return order_redo(to_order, level, basicOperators, be_ordered);
            }
        }else {
           // System.out.println("From operator: " + to_order.getAll_AST().get(0));

            basicOperators.setRight(orderAST(to_order, level + 1, basicOperators, be_ordered));
            to_order.popFrontAST(1);
            return order_redo(to_order, level, basicOperators, be_ordered);

        }
    }


    private DefaultAST getBody(MainAST to_order, int level, DefaultAST last, MainAST be_ordered, ConditionalsAST conditionalsAST, MainAST asts) throws IOException, LexerException, ParserException {
        Parser temp_parser;
        DefaultAST defaultAST;
        boolean noParsing = false;
        int open = 0;
        boolean oneLine = false;
        if(to_order.has(1)) {
            defaultAST = to_order.getAll_AST().get(0);
            if(defaultAST.getType().equals("open_curly")) {
                to_order.popFrontAST(1);
                open = 1;

                if (to_order.has(1)) {
                    for (DefaultAST defaultAST1 : to_order.getAll_AST()) {
                        if (defaultAST1.getType().equals("open_curly")) {
                            open++;

                        } else if (defaultAST1.getType().equals("close_curly")) {
                            open--;
                        }

                        if (open == 0) {
                            oneLine = true;
                        }
                    }
                }
            }else{
                if (to_order.has(1)) {
                    if(to_order.getAll_AST().get(0).getType().equals("conditional")){
                        noParsing = true;
                    }
                    for (DefaultAST defaultAST1 : to_order.getAll_AST()) {
                        if (defaultAST1.getType().equals("open_curly")) {
                            open++;

                        } else if (defaultAST1.getType().equals("close_curly")) {
                            open--;
                        }

                    }

                    if (open == 0) {
                        oneLine = true;
                    }else{
                        open = 0;
                    }
                }
            }
        }else{
            oneLine = false;
        }
        if(!oneLine){
            //System.out.println("not one line");
            MainAST currentLine = new MainAST(new Stack<>());
            orderAST(to_order, level, conditionalsAST, currentLine);
            temp_parser = new Parser(lexer);
            MainAST body = new MainAST(new Stack<>());
            for(DefaultAST toAdd : currentLine.getAll_AST()){
                body.addAST(toAdd);
            }
            if(noParsing){
                conditionalsAST.setBody(body);
                be_ordered.addAST(conditionalsAST);
                return null;
            }
            while(temp_parser.parseLine()) {
                for(DefaultAST toAdd : temp_parser.mainAST.getAll_AST()){
                    if(toAdd.getType().equals("open_curly")){
                        open++;
                    }else if(toAdd.getType().equals("close_curly")){
                        open--;
                    }else{
                        body.addAST(toAdd);
                    }
                    //System.out.println("Open: " + open);
                    if(open == 0){
                        conditionalsAST.setBody(body);
                        be_ordered.addAST(conditionalsAST);
                        temp_parser.mainAST.popFrontAST(1);
                        if(temp_parser.mainAST.getAll_AST().size() > 0) {
                            if(temp_parser.mainAST.getAll_AST().get(0) instanceof ConditionalsAST){
                                ((ConditionalsAST) be_ordered.getAll_AST().get(be_ordered.getAll_AST().size()-1)).setElse_cond((ConditionalsAST) temp_parser.mainAST.getAll_AST().get(0));
                            }else{
                                //System.out.println("start printing");
                                for(DefaultAST defaultAST1 : temp_parser.mainAST.getAll_AST()){
                                   // System.out.println(defaultAST1.toString());
                                    if(defaultAST1.toString() != null){
                                        be_ordered.addAST(defaultAST1);
                                    }
                                }
                            }
                        }
                        return null;
                    }
                    temp_parser.mainAST.popFrontAST(1);

                }
            }
        }else{
            //System.out.println("oneline");
            MainAST currentLine = new MainAST(new Stack<>());
            orderAST(to_order, level, conditionalsAST, currentLine);
            MainAST body = new MainAST(new Stack<>());

            for(DefaultAST toAdd : currentLine.getAll_AST()){
                if(toAdd.getType().equals("open_curly")){
                    open++;
                }else if(toAdd.getType().equals("close_curly")){
                    open--;
                }else{
                    //System.out.println("toAdd: " + toAdd.toString());

                    body.addAST(toAdd);
                }
                //System.out.println("Open: " + open);

                if(open == 0){
                    conditionalsAST.setBody(body);
                    be_ordered.addAST(conditionalsAST);
                    currentLine.popFrontAST(1);
                    if(currentLine.getAll_AST().size() > 1) {
                        if(currentLine.getAll_AST().get(1) instanceof ConditionalsAST){
                            ((ConditionalsAST) be_ordered.getAll_AST().get(be_ordered.getAll_AST().size()-1)).setElse_cond((ConditionalsAST) currentLine.getAll_AST().get(1));
                        }else{

                            for(DefaultAST defaultAST1 : currentLine.getAll_AST()){
                                // System.out.println(defaultAST1.toString());
                                if(defaultAST1.toString() != null){
                                    be_ordered.addAST(defaultAST1);
                                }
                            }                        }
                    }
                    //System.out.println(conditionalsAST.toString());

                    return null;
                }
                currentLine.popFrontAST(1);

            }
        }
        return order_redo(to_order, level, conditionalsAST, be_ordered);
    }

    private DefaultAST getFuncBody(MainAST to_order, int level, DefaultAST last, MainAST be_ordered, FunctionDefAST functionDefAST, MainAST asts) throws IOException, LexerException, ParserException {
        if(!to_order.has(2)) {
            Parser temp_parser = new Parser(lexer);

            while(temp_parser.parseLine()){
                //System.out.println("cccc");
                for(DefaultAST defaultAST1 : temp_parser.mainAST.getAll_AST()){
                    if(defaultAST1.getType().equals("close_curly")){
                        temp_parser.mainAST.popFrontAST(1);
                        //System.out.println(asts.getAll_AST().get(1));

                        functionDefAST.setBody(asts.getAll_AST());
                        be_ordered.addAST(functionDefAST);
                        if(temp_parser.mainAST.getAll_AST().size() > 1) {
                            be_ordered.addAST(temp_parser.mainAST);
                        }

                        temp_parser.mainAST.getAll_AST().clear();
                        return null;
                    }else if(!defaultAST1.getType().equals("open_curly")){
                        temp_parser.mainAST.popFrontAST(1);
                        asts.addAST(defaultAST1);

                    }else if(defaultAST1.getType().equals("open_curly")){
                        temp_parser.mainAST.popFrontAST(1);
                    }
                    //System.out.println(defaultAST1.toString());

                }
            }
        }else{
            if(to_order.getAll_AST().get(0).getType().equals("open_curly")) {
                to_order.popFrontAST(1);
            }
            orderAST(to_order, 0, null, asts);
            MainAST body = new MainAST(new Stack<>());
            for(DefaultAST defaultAST : asts.getAll_AST()){
                body.addAST(defaultAST);

            }
            functionDefAST.setBody(asts.getAll_AST());
            return order_redo(to_order, level, functionDefAST, be_ordered);

        }
        return order_redo(to_order, level, functionDefAST, be_ordered);
    }
}