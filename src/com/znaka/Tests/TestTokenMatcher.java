package com.znaka.Tests;

import com.znaka.Tokens.Token;
import com.znaka.Tokens.TokenExceptions.TokenMatchException;
import com.znaka.Tokens.TokenMatcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestTokenMatcher {
    @Test
    public void testTokenizeLine(){
        TokenMatcher tm = new TokenMatcher();
        try {
            ArrayList<Token> tokens = tm.tokenizeLine("int a = 10");
            for(Token tk : tokens){
                System.out.printf("%s ", tk.printer());
            }
        }
        catch (TokenMatchException te){
            System.out.println(te.getMessage());
            return;
        }

    }
}
