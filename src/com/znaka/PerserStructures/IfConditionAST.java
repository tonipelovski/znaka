package com.znaka.PerserStructures;

public class IfConditionAST extends DefaultAST {
    private DefaultAST condition;
    private DefaultAST then;
    private DefaultAST else_condition;

    public IfConditionAST(DefaultAST cond, DefaultAST th, DefaultAST else_cond) {
        super("if");
        this.condition = cond;
        this.then = th;
        this.else_condition = else_cond;

    }

    public DefaultAST getCondition() {
        return condition;
    }

    public DefaultAST getThen() {
        return then;
    }

    public DefaultAST getElse_condition() {
        return else_condition;
    }

    @Override
    boolean matchAST(DefaultAST ast) {
        if(ast.getType().equals(this.getType())){
            return true;
        }else {
            return false;
        }
    }
}
