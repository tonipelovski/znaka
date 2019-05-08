package com.znaka.Contracts;

import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.VarAST;

import java.util.List;

public interface FunctionDefinitionASTInter {
    List<DefaultAST> getBody();
    List<VarAST> getArgs();
    String getRet_type();
    String getName();

}
