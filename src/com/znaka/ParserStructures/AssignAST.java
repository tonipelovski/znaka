package com.znaka.ParserStructures;

public class AssignAST extends DefaultAST {
    private String operator;
    private DefaultAST left;
    private DefaultAST right;

    public AssignAST(DefaultAST l, DefaultAST r) {
        super("assign");
        this.left = l;
        this.right = r;
    }

    public String getOperator() {
        return operator;
    }

    public DefaultAST getLeft() {
        return left;
    }

    public DefaultAST getRight() {
        return right;
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
