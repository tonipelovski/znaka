package com.znaka;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.*;
import com.znaka.EvaluatorStructures.ExecuteOperations.BinaryOper.BinaryOper;
import com.znaka.EvaluatorStructures.ExecuteOperations.UnaryOper.UnaryOper;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.EvaluatorStructures.Scope;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.*;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.StdLib.Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Evaluator {
    private Parser parser;
    private boolean debug = false;
    private List<BaseExecuteOper> operations;
    private DataVal lastReturnedValue;
    private Scope mainScope;
    private Scope currentScope;

    public boolean isDebug() {
        return debug;
    }

    private Stack<FunctionCall> callStack;
    private HashSet<Library> libraries = new HashSet<>();

    public HashSet<Library> getLibraries() {
        return libraries;
    }

    public Evaluator(Parser parser) {
        this.parser = parser;
        mainScope = new Scope();
        currentScope = mainScope;
        lastReturnedValue = new DataVal<>("null", "void");
        this.callStack = new Stack<>();
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
        operations.add(new FunctionDef(this));
        operations.add(new FunctionCalling(this));
        operations.add(new ReturnExecuteOper(this));

    }

    public void run() throws ParserException, IOException, LexerException, EvaluatorException, ExitException {
        while(ProcessLine()){
        }
    }

    public boolean ProcessLine() throws ParserException, IOException, LexerException, EvaluatorException, ExitException {
        if(!parser.parseLine()){
            return false;
        }
        DefaultAST ast = parser.getLastAst(); // needs to be changed....

        ExecLine(ast);
        if(debug){
            System.out.println("Running: " + getParser().getLastAst().toString());
            System.out.println("Returned: " + (lastReturnedValue == null || lastReturnedValue.getVal() == null
                    ? "Void" : lastReturnedValue.getVal().toString()));
        }

        return true;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private void ErrorMessagePrint(Throwable exc){
        System.out.printf("%d: %s\n  error: %s",
                parser.getLexer().getLineNum(), parser.getLexer().getLast_line(), exc.getMessage());
    }

    public Scope getMainScope() {
        return mainScope;
    }

    public Stack<FunctionCall> getCallStack() {
        return callStack;
    }

    public HashSet<Function> getFunctions(){
        return currentScope.functions;
    }

    public DataVal getLastReturnedValue() {
        return lastReturnedValue;
    }

    public void setLastReturnedValue(DataVal lastReturnedValue) {
        this.lastReturnedValue = lastReturnedValue;
    }

    public Parser getParser() {
        return parser;
    }

    public void ExecLine(DefaultAST ast) throws EvaluatorException, ExitException {
        DataVal returned = Eval(ast);
        if(returned != null){ // if there is a return type (no return type are: statements and void)
            lastReturnedValue = returned;
        }
//       System.out.println(lastReturnedValue);
    }

    public void setMainScope(Scope mainScope) {
        this.mainScope = mainScope;
    }

    public void setCurrentScope(Scope currentScope) {
        this.currentScope = currentScope;
    }

    public Scope getCurrentScope() {
        return currentScope;
    }

    public DataVal Eval(DefaultAST ast) throws EvaluatorException, ExitException {
        for (BaseExecuteOper oper : operations) {
            //System.out.println(ast.getClass());
            if(ast.getClass().isAssignableFrom(oper.getMatchClass()) || oper.getMatchClass().isAssignableFrom(ast.getClass())){ // can it be casted to the oper MatchClass
                return oper.exec(ast);
            }
        }
        throw new CannotEvaluate(String.format("Couldn't evaluate(%d): %s", parser.getLexer().getLineNum(),
                parser.getLexer().getLast_line()));
    }

    public void switchScope(){
        if(callStack.isEmpty()){
            revertMainScope();
        }
        else {
            currentScope = callStack.lastElement().getScope();
        }
    }

    public void revertMainScope(){
        currentScope = mainScope;
    }

}
