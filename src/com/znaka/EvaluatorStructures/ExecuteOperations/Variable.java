package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.Exceptions.CannotModifyConstant;

public class Variable<T> {
    private String name;
    private DataVal<T> val;
    private boolean isConst;

    public Variable(String name, DataVal<T> val, boolean constant) {
        this.name = name;
        this.val = val;
        this.isConst = constant;
    }

    public DataVal<T> getVal() {
        return val;
    }

    public void setVal(DataVal<T> val) throws CannotModifyConstant {
        if(isConst){
            throw new CannotModifyConstant();
        }
        this.val = val;
    }

    public String getName() {
        return name;
    }
}
