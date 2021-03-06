package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Contracts.FunctionCallASTInter;
import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.EvaluatorStructures.Functions.NativeFunction;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.NoSuchFunction;
import com.znaka.Exceptions.WrongType;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.ExpressionAST;
import com.znaka.ParserStructures.Expression.MethodAST;

import java.util.ArrayList;
import java.util.List;

public class FunctionCalling extends BaseExecuteOper {
    public FunctionCalling(Evaluator eval) {
        super(FunctionCallASTInter.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {
        FunctionCallASTInter fnc = (FunctionCallASTInter) ast;
        if(ast instanceof MethodAST){
            MethodAST st = (MethodAST) ast;
            ExpressionAST instance = st.getArgs().get(0);
            DataVal ins = getEvaluator().Eval(instance);
            if(ins.getType().equals("array")){

            }

        }

        Function f = getEvaluator().getFunctions().stream()
                .filter(o -> o.getName().equals(fnc.getName()))
                .findFirst()
                .orElse(null);
        if(f == null){
            f = getEvaluator().getLibraries().stream()
                    .flatMap(o -> o.getFunctions().stream())
                    .filter(o -> o.getName().equals(fnc.getName()))
                    .findFirst()
                    .orElse(null);
            if(f==null){
                throw new NoSuchFunction("No such function: " + fnc.getName());
            }
        }
        List<DataVal> args = new ArrayList<>();
        for (DefaultAST arg : fnc.getArgs()) {
            if(arg == null){
                break;
            }
            args.add(getEvaluator().Eval(arg));
            // evaluate and add each
        }
        DataVal returned = new DataVal<>(null, "void");
        if(f instanceof NativeFunction){
            returned = ((NativeFunction) f).call(args);
            if(f.getReturn_type().equals("void")){
                returned = new DataVal<>(null, "void");
            }
        }
        else{
            FunctionCall call = new FunctionCall(f, args);
            call.getScope().functions.addAll(getEvaluator().getFunctions());
            getEvaluator().getCallStack().push(call);
            getEvaluator().switchScope();
            for (DefaultAST line : f.getBody()) {
                getEvaluator().ExecLine(line);
                if(getEvaluator().getCallStack().isEmpty() || !getEvaluator().getCallStack().lastElement().equals(call)){
                    break;
                }
            }
            returned = getEvaluator().getLastReturnedValue();
        }

        validateReturnType(returned, f);
        /*if(f.getReturn_type().equals("void")){
            returned = new DataVal<>(null, "void");
        }
        validateReturnType(returned, f);
        getEvaluator().getCallStack().pop();
        getEvaluator().switchScope();*/
        return returned;
    }

    public static void validateReturnType(DataVal lastReturnedValue, Function f) throws WrongType {
        if(!f.getReturn_type().equals("any") && !lastReturnedValue.getType().equals(f.getReturn_type())){
            throw new WrongType(lastReturnedValue.getType(), f.getReturn_type(), lastReturnedValue.toString());
        }
    }
}
