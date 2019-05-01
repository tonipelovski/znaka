package com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations;

import com.znaka.EvaluatorStructures.DataVal;

public abstract class BasicOperation {
    private String type;

    public BasicOperation(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract DataVal convert(DataVal right);
}
