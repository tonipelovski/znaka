package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.Exceptions.WrongType;

public abstract class BasicOperation {
    private String type;

    public BasicOperation(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    protected abstract DataVal convert(DataVal right);

    protected void ValidationCheck(DataVal val) throws WrongType {
        if(!val.getType().equals(type)){
            throw new WrongType(String.format("Cannot convert %s into %s", val.getType(), type));
        }
    }

    public DataVal convertWithCheck(DataVal right) throws WrongType {
        ValidationCheck(right);
        return convert(right);
    }
}
