package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.Exceptions.CannotEvaluate;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.LiteralTypesAST;
import com.znaka.ParserStructures.NumberAST;

public class LiterValueOper extends BaseExecuteOper {
    public LiterValueOper(Evaluator eval) {
        super(LiteralTypesAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws UnknownVariable, CannotEvaluate {
        LiteralTypesAST ast1 = (LiteralTypesAST)ast;
        if(ast1.getType().equals("number")){
            NumberAST num = (NumberAST)ast1;
            if(num.getNumberType().equals("integer")){
                return new DataVal(Integer.parseInt(ast1.getText()), "int");
            }
            if(num.getNumberType().equals("float")){
                return new DataVal(Double.parseDouble(ast1.getText()), num.getNumberType());
            }
            //return new DataVal(ast1.getText(), num.getNumberType());
        }

        //return new DataVal(ast1.getText(), ast1.getType());
        if(ast1.getType().equals("char")){
            return new DataVal(ast1.getText().charAt(0), ast1.getType());
        }
        if(ast1.getType().equals("string_literal")){
            return new DataVal(ast1.getText(), "string");
        }
        if(ast1.getType().equals("boolean")){
            return new DataVal(Boolean.parseBoolean(ast1.getText()), "bool");
        }
        /*if(ast1.getNumberType().equals("double")){
            return new DataVal(Double.parseDouble(ast1.getText()), ast1.getNumberType());
        }*/

        return new DataVal(ast1.getText(), ast1.getType());
    }
}
