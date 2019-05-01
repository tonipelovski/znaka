package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;

public class IntOper extends BasicOperation {
    public IntOper() {
        super("int");
    }

    @Override
    public DataVal convert(DataVal dataVal) {
        return new DataVal<>(dataVal.getVal(), "int");
    }
}
