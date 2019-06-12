package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;

public class ArrayOper extends BasicOperation {
    public ArrayOper() {
        super("array");
    }

    @Override
    protected DataVal convert(DataVal right) {
        return new DataVal<>(right.getVal(), "array");
    }
}
