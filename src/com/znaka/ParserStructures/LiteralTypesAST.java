package com.znaka.ParserStructures;

import com.znaka.ParserStructures.Expression.ExpressionAST;

public abstract class LiteralTypesAST extends ExpressionAST {
    public LiteralTypesAST(String type) {
        super(type);
    }
}
