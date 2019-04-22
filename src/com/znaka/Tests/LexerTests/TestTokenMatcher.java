package com.znaka.Tests.LexerTests;

import com.znaka.Tokens.Token;
import com.znaka.Exceptions.TokenMatchException;
import com.znaka.Tokens.TokenMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestTokenMatcher {
    @Test
    public void testTokenizeLine(){
        TokenMatcher tm = new TokenMatcher();
        try {
            ArrayList<Token> tokens = tm.tokenizeLine("int a = 10");
   /*         String out;
            for(Token tk : tokens){
                System.out.printf("%s ", tk.toString());

            }*/
            Assertions.assertEquals("[type : int]", tokens.get(0).toString(), "Testing int");
            Assertions.assertEquals("[symbol : a]", tokens.get(1).toString());
            Assertions.assertEquals("[operator : =]", tokens.get(2).toString());
            Assertions.assertEquals("[number : 10]", tokens.get(3).toString());
        }
        catch (TokenMatchException te){
            System.out.println(te.getMessage());
        }

    }
}