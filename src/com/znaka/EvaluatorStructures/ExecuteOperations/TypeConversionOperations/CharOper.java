package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;

public class CharOper extends BasicOperation {
    public CharOper() {
        super("char");
    }

    @Override
    public DataVal convert(DataVal right) {
        return new DataVal<>(right.getVal(), "char");
    }
}
