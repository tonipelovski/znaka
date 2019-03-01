package com.znaka.ParserStructures;

public abstract class DefaultAST {
    private String type;

    public DefaultAST(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    abstract boolean matchAST(DefaultAST ast);
}
