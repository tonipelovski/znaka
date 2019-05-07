package com.znaka.EvaluatorStructures;

import com.znaka.EvaluatorStructures.Functions.Function;

import java.util.HashSet;

public class Scope {
    public HashSet<Variable> variables;
    public HashSet<Function> functions;

    public Scope() {
        this.variables = new HashSet<>();
        this.functions = new HashSet<>();
    }
}
