package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;

public class BoolOper extends BasicOperation {
    public BoolOper() {
        super("bool");
    }

    @Override
    public DataVal convert(DataVal right) {
        return new DataVal<>(right.getVal(), "bool");
    }
}
