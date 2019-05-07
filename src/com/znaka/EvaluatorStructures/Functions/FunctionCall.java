package com.znaka.EvaluatorStructures.Functions;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Scope;

import java.util.List;

public class FunctionCall {
    private Function func;
    private Scope scope;

    public FunctionCall(Function func, List<DataVal> args) {
        this.func = func;
        
    }

    public void call(){

    }
}
