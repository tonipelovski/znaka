package com.znaka.Contracts;

import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.VarAST;

import java.util.List;

public interface FunctionDefinitionASTInter {
    List<DefaultAST> getBody(); // the function body as List of AST
    List<VarAST> getArgs(); // the args of the function
    String getRet_type(); //  the functions return type as string
    String getName(); // the name of the function

}
