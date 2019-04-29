package com.znaka.EvaluatorStructures;

public class DataVal<T> {
    private T val;

    public DataVal(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }
}
