package com.znaka.EvaluatorStructures;

public class DataVal<T> {
    private T val;
    private String type;

    public String getType() {
        return type;
    }

    public DataVal(T val, String type) {
        this.val = val;
        this.type = type;
    }

    public T getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }
}
