package com.znaka.Tests;

import com.znaka.Tokens.Token;
import com.znaka.Tokens.TokenBoolMatch;
import com.znaka.Tokens.TokenIndexMatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenTests {
    @Test
    public void TestBoolMatch() {
        TokenBoolMatch bm = new TokenBoolMatch();
        Assertions.assertNotEquals(new Token("boolean", "False"), bm.match("True"));
        Assertions.assertNotEquals(new Token("boolean", "true"), bm.match("true"));
        Assertions.assertEquals(new Token("boolean", "True"), bm.match("True"));
        Assertions.assertEquals(new Token("boolean", "False"), bm.match("False"));

    }

    @Test
    public void TestIndexingMatch(){
        TokenIndexMatch im = new TokenIndexMatch();
        Assertions.assertEquals(new Token("index", "[5]"), im.match("[5]"));
        Assertions.assertEquals(new Token("index", "[123]"), im.match("[123]"));
    }
}
