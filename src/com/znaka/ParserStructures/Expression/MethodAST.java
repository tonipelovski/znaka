package com.znaka.ParserStructures.Expression;

import java.util.List;

public class MethodAST extends FunctionCallAST {
    public MethodAST(String name, List<ExpressionAST> args) {
        super(name, args);
    }
}
