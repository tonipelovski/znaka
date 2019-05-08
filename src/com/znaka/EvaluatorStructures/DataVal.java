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

    public DataVal(DataVal<T> other){
        this.val = other.val;
        this.type = other.type;

    }

    public T getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }
}
