package com.znaka.EvaluatorStructures.Functions;

import com.znaka.EvaluatorStructures.Variable;
import com.znaka.ParserStructures.DefaultAST;

import java.util.List;
import java.util.Objects;

public class Function {
    private String name;
    private String return_type;
    private List<Variable> args;
    private List<DefaultAST> body;

    public String getName() {
        return name;
    }

    public String getReturn_type() {
        return return_type;
    }

    public List<Variable> getArgs() {
        return args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return name.equals(function.name)
                && args.equals(function.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, args);
    }

    public List<DefaultAST> getBody() {
        return body;
    }

    public Function(String name, String return_type, List<Variable> args, List<DefaultAST> body) {
        this.name = name;
        this.return_type = return_type;
        this.args = args;
        this.body = body;
    }


}
