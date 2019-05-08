package com.znaka;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.*;
import com.znaka.EvaluatorStructures.ExecuteOperations.BinaryOper.BinaryOper;
import com.znaka.EvaluatorStructures.ExecuteOperations.UnaryOper.UnaryOper;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.EvaluatorStructures.Scope;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.CannotEvaluate;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.ParserStructures.DefaultAST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Evaluator {
    private Parser parser;
    private List<BaseExecuteOper> operations;
    private DataVal lastReturnedValue;
    private Scope mainScope;
    private Scope currentScope;
    private Stack<FunctionCall> callStack;

    public Evaluator(Parser parser) {
        this.parser = parser;
        mainScope = new Scope();
        currentScope = mainScope;
        addAllOperations();
    }

    public HashSet<Variable> getVariables() {
        return currentScope.variables;
    }

    private void addAllOperations() {
        operations = new ArrayList<>();
        operations.add(new AssignOper(this));
        operations.add(new ConditionOper(this));
        operations.add(new VarGetOper(this));
        operations.add(new LiterValueOper(this));
        operations.add(new BinaryOper(this));
        operations.add(new UnaryOper(this));

    }

    public void run() throws ParserException, IOException, LexerException {
        while(parser.parseLine()){
        }

        System.out.println(parser);
    }

    public void ProcessLine() throws ParserException, IOException, LexerException, EvaluatorException {
        parser.parseLine();
        DefaultAST ast = parser.mainAST.getAll_AST().get(parser.mainAST.getAll_AST().size() - 1); // needs to be changed....

        /*lastReturnedValue = new DataVal<>(2);
        System.out.println(lastReturnedValue.getVal());
        lastReturnedValue = new DataVal<>("Asd");
        System.out.println(lastReturnedValue.getVal());*/

        /*System.out.println(ast);
        System.out.println(ast instanceof AssignAST);
        System.out.println(ast.getText());
        AssignAST ast1 = (AssignAST)ast;

        System.out.println(ast1.getOperator());
        System.out.println(ast1.getLeft());
        System.out.println(ast1.getRight());*/

            ExecLine(ast);
//        System.out.println("Last expression returned: " + lastReturnedValue);
    }

    private void ErrorMessagePrint(Throwable exc){
        System.out.printf("%d: %s\n  error: %s",
                parser.getLexer().getLineNum(), parser.getLexer().getLast_line(), exc.getMessage());
    }

    public DataVal getLastReturnedValue() {
        return lastReturnedValue;
    }

    public void setLastReturnedValue(DataVal lastReturnedValue) {
        this.lastReturnedValue = lastReturnedValue;
    }

    public void ExecLine(DefaultAST ast) throws EvaluatorException {
        DataVal returned = Eval(ast);
        if(returned != null){ // if there is a return type (no return type are: statements and void)
            lastReturnedValue = returned;
        }
       System.out.println(lastReturnedValue);
    }

    public DataVal Eval(DefaultAST ast) throws EvaluatorException {
        for (BaseExecuteOper oper : operations) {
            //System.out.println(ast.getClass());
            if(ast.getClass().isAssignableFrom(oper.getMatchClass()) || oper.getMatchClass().isAssignableFrom(ast.getClass())){ // can it be casted to the oper MatchClass
                return oper.exec(ast);
            }
        }
        throw new CannotEvaluate(String.format("Couldn't evaluate(%d): %s", parser.getLexer().getLineNum(),
                parser.getLexer().getLast_line()));
    }


}
