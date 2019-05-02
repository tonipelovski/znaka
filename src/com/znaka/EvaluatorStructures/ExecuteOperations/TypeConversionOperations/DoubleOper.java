package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.Exceptions.WrongType;

public class DoubleOper extends BasicOperation {
    public DoubleOper() {
        super("double");
    }

    @Override
    public DataVal convert(DataVal right) {
        return new DataVal<>(Double.parseDouble(right.toString()), "double");
    }

    @Override
    protected void ValidationCheck(DataVal val) throws WrongType {
        if(!val.getType().equals("float")){
            throw new WrongType(String.format("Cannot convert %s into %s", val.getType(), "float"));
        }
    }
}
