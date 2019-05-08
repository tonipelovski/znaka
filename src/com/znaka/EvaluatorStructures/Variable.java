package com.znaka.EvaluatorStructures;

import com.znaka.Exceptions.CannotModifyConstant;
import com.znaka.Exceptions.WrongType;

import java.util.Objects;

public class Variable<T> {
    private String name;
    private DataVal<T> val;
    private boolean isConst;

    public boolean isConst() {
        return isConst;
    }

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

    public Variable(Variable<T> other) {
        this.name = other.name;
        this.val = new DataVal<>(other.val);
        this.isConst = other.isConst;

    }

    public DataVal<T> getVal() {
        return val;
    }

    public void setVal(DataVal<T> newVal) throws CannotModifyConstant, WrongType {
        if(isConst){
            throw new CannotModifyConstant();
        }
        if(!newVal.getType().equals(val.getType())){
            throw new WrongType(newVal.getType(), val.getType(), newVal.toString());
        }
        this.val = newVal;
    }

    public String getName() {
        return name;
    }
}
