package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;

public class FloatOper extends BasicOperation {
    public FloatOper() {
        super("float");
    }

    @Override
    public DataVal convert(DataVal right) {
        return new DataVal<>(Float.parseFloat(right.toString()), "float");
    }
}
