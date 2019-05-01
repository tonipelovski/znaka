package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;

public class DoubleOper extends BasicOperation {
    public DoubleOper() {
        super("double");
    }

    @Override
    public DataVal convert(DataVal right) {
        return new DataVal<>(Double.parseDouble(right.toString()), "double");
    }
}
