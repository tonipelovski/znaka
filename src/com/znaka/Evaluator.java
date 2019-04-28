package com.znaka;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.AssignOper;
import com.znaka.EvaluatorStructures.ExecuteOperations.BaseExecuteOper;
import com.znaka.EvaluatorStructures.ExecuteOperations.IfOper;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.ParserStructures.DefaultAST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Evaluator {
    private Parser parser;
    private List<BaseExecuteOper> operations;
    private DataVal lastReturnedValue;

    public Evaluator(Parser parser) {
        this.parser = parser;
        addAllOperations();
    }

    private void addAllOperations() {
        operations = new ArrayList<>();
        operations.add(new AssignOper(this));
        operations.add(new IfOper(this));
    }

    public void run() throws ParserException, IOException, LexerException {
        while(parser.parseLine()){
        }

        System.out.println(parser);
    }

    public void EvaluateLine() throws ParserException, IOException, LexerException {
        parser.parseLine();
        DefaultAST ast = parser.mainAST.getAll_AST().peek();

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
        System.out.println(lastReturnedValue);
    }

    private void ExecLine(DefaultAST ast){
        for (BaseExecuteOper oper : operations) {
            if(ast.getClass().isAssignableFrom(oper.getMatchClass())){ // can it be casted to the oper MatchClass
                DataVal returned =  oper.exec(ast);

                if(returned != null){ // if there is a return type (no return type are: statements and void)
                    lastReturnedValue = returned;
                }
                break;
            }
        }
    }


}
