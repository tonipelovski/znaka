package com.znaka;

import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.DefaultASTMatcher;
import com.znaka.ParserStructures.MainAST;
import com.znaka.Tokens.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Parser {
    Lexer lexer;
    MainAST mainAST;
    int last_token = 0;
    int max_token = 0;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        mainAST = new MainAST(new Stack<>());
    }

    public void parseLIne() throws IOException {
        max_token = 0;
        last_token = 0;
        lexer.readLine();
        ArrayList<Token> all_tokens = lexer.getTokens();
        while(max_token < all_tokens.size()) {
            DefaultASTMatcher defaultASTMatcher = new DefaultASTMatcher(new ArrayList<DefaultAST>(), this);

            if(last_token < max_token) {
                ArrayList<Token> tokens = new ArrayList(all_tokens.subList(last_token, max_token));

                DefaultAST defaultAST = defaultASTMatcher.match(tokens);
                if (defaultAST != null) {
                    mainAST.addAST(defaultAST);
                    //printASTS();
                    //System.out.println(" ");
                }
            }
            max_token++;
        }
    }

    public void next(int index){
        last_token += index;
    }

    public String printASTS(){
        String output = "";
        for(int i = 0; i < mainAST.getAll_AST().size(); i++){
            output = output.concat(mainAST.getAll_AST().get(i).printAST());
        }
        return output;
    }

}
