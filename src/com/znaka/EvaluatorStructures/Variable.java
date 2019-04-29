package com.znaka.EvaluatorStructures;

import com.znaka.Exceptions.CannotModifyConstant;

import java.util.Objects;

public class Variable<T> {
    private String name;
    private DataVal<T> val;
    private boolean isConst;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable<?> variable = (Variable<?>) o;
        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Variable(String name, DataVal<T> val, boolean constant) {
        this.name = name;
        this.val = val;
        this.isConst = constant;
    }

    public DataVal<T> getVal() {
        return val;
    }

    public void setVal(DataVal<T> val) throws CannotModifyConstant {
        if(isConst){
            throw new CannotModifyConstant();
        }
        this.val = val;
    }

    public String getName() {
        return name;
    }
}
