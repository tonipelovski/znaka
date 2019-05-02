package com.znaka.Tests.LexerTests;

import com.znaka.Exceptions.TokenMatchException;
import com.znaka.Tokens.TokenMatcher;
import com.znaka.Tokens.TokenMatches.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestTokenMatcher {


    private void checkStatementFromTokens(String statement, String s) throws TokenMatchException {
        TokenMatcher tm = new TokenMatcher();
        ArrayList<Token> tokens = tm.tokenizeLine(statement);
        ArrayList<Token> ls2 = Arrays.stream(s.split(", "))
                .map(Token::tokenFromString).collect(Collectors.toCollection(ArrayList::new));
        Assertions.assertEquals(tokens, ls2);

    }

    @Test
    public void testTokenizeLine() throws TokenMatchException {
        TokenMatcher tm = new TokenMatcher();

        checkStatementFromTokens("let int a = -10.2",
                "[access : let], [type : int], [symbol : a], [operator : =], [operator : -], [float : 10.2]");

        checkStatementFromTokens("int a = -5",
                "[type : int], [symbol : a], [operator : =], [operator : -], [integer : 5]");

        checkStatementFromTokens("bool a = True",
                "[type : bool], [symbol : a], [operator : =], [boolean : True]");


    }
}
