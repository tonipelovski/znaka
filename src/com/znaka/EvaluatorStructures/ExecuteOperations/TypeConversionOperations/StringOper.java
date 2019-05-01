package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;

public class StringOper extends BasicOperation {
    public StringOper() {
        super("string");
    }

    @Override
    public DataVal convert(DataVal right) {
        return new DataVal<>(right.getVal(), "string");
    }
}
