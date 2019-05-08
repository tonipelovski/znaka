package com.znaka.Contracts;

import com.znaka.ParserStructures.Expression.ExpressionAST;

import java.util.List;

public interface FunctionCallASTInter {
    String getName(); // the name of the function being called
    List<ExpressionAST> getArgs(); // the arguments of the called function
}
