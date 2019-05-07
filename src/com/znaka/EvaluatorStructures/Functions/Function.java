package com.znaka.EvaluatorStructures.Functions;

import com.znaka.EvaluatorStructures.Variable;
import com.znaka.ParserStructures.DefaultAST;

import java.util.List;

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

    public List<DefaultAST> getBody() {
        return body;
    }

    public Function(String name, String return_type, List<Variable> args) {
        this.name = name;
        this.return_type = return_type;
        this.args = args;
    }


}
