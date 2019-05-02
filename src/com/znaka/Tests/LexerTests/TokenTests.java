package com.znaka.Tests.LexerTests;

import com.znaka.Tokens.TokenMatches.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//import com.znaka.Tokens.TokenMatches.TokenIndexMatch;

public class TokenTests {

    @Test
    public void TestBoolMatch() {
        TokenBoolMatch bm = new TokenBoolMatch();
        Assertions.assertEquals(5, bm.nextTokenEndIndex("False-"));
        Assertions.assertEquals(5, bm.nextTokenEndIndex("False"));
        Assertions.assertEquals(4, bm.nextTokenEndIndex("True-asdTrue"));
        Assertions.assertEquals(4, bm.nextTokenEndIndex("True+True"));
        Assertions.assertEquals(0, bm.nextTokenEndIndex("FalseTrue"));
        Assertions.assertEquals(0, bm.nextTokenEndIndex("-True"));
        Assertions.assertEquals(0, bm.nextTokenEndIndex("Truef"));
        Assertions.assertEquals(0, bm.nextTokenEndIndex("True23"));
        Assertions.assertEquals(0, bm.nextTokenEndIndex("a = False"));
        Assertions.assertEquals(4, bm.nextTokenEndIndex("True != False"));
        Assertions.assertEquals(5, bm.nextTokenEndIndex("False == False"));

    }

    @Test
    public void TestNumberMatch() {
        TokenNumberMatch nm = new TokenNumberMatch();
        Assertions.assertEquals(3, nm.nextTokenEndIndex("123"));
        Assertions.assertEquals(0, nm.nextTokenEndIndex("a123"));
        Assertions.assertEquals(0, nm.nextTokenEndIndex("a 123"));
        Assertions.assertEquals(1, nm.nextTokenEndIndex("2"));
        Assertions.assertEquals(3, nm.nextTokenEndIndex("-10"));
        Assertions.assertEquals(5, nm.nextTokenEndIndex("-12.3"));
        Assertions.assertEquals(4, nm.nextTokenEndIndex("12.3"));
        Assertions.assertEquals(0, nm.nextTokenEndIndex(".12"));

    }

    @Test
    void TestIntegerMatch(){
        TokenIntegerMatch im = new TokenIntegerMatch();
        Assertions.assertEquals(2, im.nextTokenEndIndex("20"));
        Assertions.assertEquals(1, im.nextTokenEndIndex("2"));
        Assertions.assertEquals(0, im.nextTokenEndIndex("-2"));
        Assertions.assertEquals(0, im.nextTokenEndIndex("-2.6"));
        Assertions.assertEquals(1, im.nextTokenEndIndex("2.633"));
    }

    @Test
    void TestFloatMatch(){
        TokenFloatMatch fm = new TokenFloatMatch();
        Assertions.assertEquals(4, fm.nextTokenEndIndex("10.5"));
        Assertions.assertEquals(4, fm.nextTokenEndIndex("10.5"));
        Assertions.assertEquals(6, fm.nextTokenEndIndex("10.512"));
        Assertions.assertEquals(6, fm.nextTokenEndIndex("30.512"));
        Assertions.assertEquals(0, fm.nextTokenEndIndex("10"));
        Assertions.assertEquals(0, fm.nextTokenEndIndex("200"));
        Assertions.assertEquals(0, fm.nextTokenEndIndex("200/"));
    }

    @Test
    public void TestOperatorMatch(){
        TokenOperatorMatch om = new TokenOperatorMatch();
        Assertions.assertEquals(1, om.nextTokenEndIndex("%2"));
        Assertions.assertEquals(1, om.nextTokenEndIndex("+2"));
        Assertions.assertEquals(2, om.nextTokenEndIndex("++2"));
        Assertions.assertEquals(2, om.nextTokenEndIndex("--2"));
        Assertions.assertEquals(0, om.nextTokenEndIndex("a--"));
        Assertions.assertEquals(0, om.nextTokenEndIndex("a.f"));
        Assertions.assertEquals(0, om.nextTokenEndIndex("True/2"));
        Assertions.assertEquals(1, om.nextTokenEndIndex("//4"));
        Assertions.assertEquals(1, om.nextTokenEndIndex("<4"));
        Assertions.assertEquals(2, om.nextTokenEndIndex("<=4"));

    }

    @Test
    public void TestPunctuationMatch(){ // ,;(){}[]
        TokenPunctuationMatch pm = new TokenPunctuationMatch();
        Assertions.assertEquals(1, pm.nextTokenEndIndex(","));
        Assertions.assertEquals(1, pm.nextTokenEndIndex(";"));
        Assertions.assertEquals(1, pm.nextTokenEndIndex("("));
        Assertions.assertEquals(1, pm.nextTokenEndIndex("}"));
        Assertions.assertEquals(0, pm.nextTokenEndIndex(" {"));
        Assertions.assertEquals(0, pm.nextTokenEndIndex("f["));
        Assertions.assertEquals(0, pm.nextTokenEndIndex("asd;"));
        Assertions.assertEquals(0, pm.nextTokenEndIndex("+;"));
        Assertions.assertEquals(0, pm.nextTokenEndIndex("-;"));

    }

    @Test
    public void TestSymbolMatch(){
        TokenSymbolMatch sm = new TokenSymbolMatch();
        Assertions.assertEquals(1, sm.nextTokenEndIndex("a"));
        Assertions.assertEquals(3, sm.nextTokenEndIndex("asd"));
        Assertions.assertEquals(3, sm.nextTokenEndIndex("a_d"));
        Assertions.assertEquals(3, sm.nextTokenEndIndex("a2d"));
        Assertions.assertEquals(3, sm.nextTokenEndIndex("a25+10-12"));
        Assertions.assertEquals(0, sm.nextTokenEndIndex("-a25+10-12"));
        Assertions.assertEquals(0, sm.nextTokenEndIndex(";a25+10-12"));
        Assertions.assertEquals(0, sm.nextTokenEndIndex("1variable"));
        Assertions.assertEquals(0, sm.nextTokenEndIndex("12variable"));
    }

    @Test
    public void TestKeywordMatch(){
        TokenResWordMatch kw = new TokenResWordMatch();
        Assertions.assertEquals(5, kw.nextTokenEndIndex("while+2"));
        Assertions.assertEquals(3, kw.nextTokenEndIndex("for"));
        Assertions.assertEquals(0, kw.nextTokenEndIndex("-for"));
        Assertions.assertEquals(0, kw.nextTokenEndIndex("+if"));
        Assertions.assertEquals(0, kw.nextTokenEndIndex("(if"));
        Assertions.assertEquals(0, kw.nextTokenEndIndex("asif"));
        Assertions.assertEquals(0, kw.nextTokenEndIndex("ifbreak"));
        Assertions.assertEquals(0, kw.nextTokenEndIndex("forlife"));
        Assertions.assertEquals(0, kw.nextTokenEndIndex("for4 in a store"));
    }

    @Test
    public void TestTypeMatch(){
        TokenTypeMatch tt = new TokenTypeMatch();
        Assertions.assertEquals(3, tt.nextTokenEndIndex("int"));
        Assertions.assertEquals(5, tt.nextTokenEndIndex("float"));
        Assertions.assertEquals(3, tt.nextTokenEndIndex("int a"));
        Assertions.assertEquals(4, tt.nextTokenEndIndex("bool a = True"));
        Assertions.assertEquals(0, tt.nextTokenEndIndex("-bool a = True"));
        Assertions.assertEquals(0, tt.nextTokenEndIndex("anint"));
        Assertions.assertEquals(0, tt.nextTokenEndIndex("asbool"));
        Assertions.assertEquals(0, tt.nextTokenEndIndex("bools"));
        Assertions.assertEquals(0, tt.nextTokenEndIndex("ints"));
        Assertions.assertEquals(0, tt.nextTokenEndIndex("Float"));
    }

    @Test
    public void TestIndexingMatch(){ // Deprecated
        TokenIndexMatch im = new TokenIndexMatch();
        Assertions.assertEquals(3, im.nextTokenEndIndex("[5]"));
        Assertions.assertEquals(5, im.nextTokenEndIndex("[523]"));
        Assertions.assertEquals(2, im.nextTokenEndIndex("[]"));
        Assertions.assertEquals(0, im.nextTokenEndIndex("-[5]"));
        Assertions.assertEquals(0, im.nextTokenEndIndex("-[5]"));
        Assertions.assertEquals(0, im.nextTokenEndIndex("a[1]"));
    }

    @Test
    public void TestStringMatch(){
        TokenStringMatch sm = new TokenStringMatch();
        Assertions.assertEquals(5, sm.nextTokenEndIndex("\"asd\""));
        Assertions.assertEquals(2, sm.nextTokenEndIndex("\"\""));
        Assertions.assertEquals(6, sm.nextTokenEndIndex("\"help\", v1"));
        Assertions.assertEquals(2, sm.nextTokenEndIndex("\"\"+ \"alabala\""));
        Assertions.assertEquals(0, sm.nextTokenEndIndex("3\"\""));
        Assertions.assertEquals(0, sm.nextTokenEndIndex("asd\"\""));
    }

    @Test
    public void TestCharMatch(){
        TokenCharMatch cm = new TokenCharMatch();
        Assertions.assertEquals(3, cm.nextTokenEndIndex("'a'"));
        Assertions.assertEquals(3, cm.nextTokenEndIndex("'1'"));
        Assertions.assertEquals(3, cm.nextTokenEndIndex("'5'"));
        Assertions.assertEquals(0, cm.nextTokenEndIndex("'asd'"));
        Assertions.assertEquals(0, cm.nextTokenEndIndex("'12'"));
        Assertions.assertEquals(0, cm.nextTokenEndIndex("-'12'"));
        Assertions.assertEquals(0, cm.nextTokenEndIndex("char a = '12'"));
        Assertions.assertEquals(0, cm.nextTokenEndIndex("'a"));
    }

    @Test
    public void TestAccessType(){
        TokenAccessTypeMatch acc = new TokenAccessTypeMatch();
        Assertions.assertEquals(3, acc.nextTokenEndIndex("let"));
        Assertions.assertEquals(3, acc.nextTokenEndIndex("var"));
        Assertions.assertEquals(0, acc.nextTokenEndIndex("int"));
        Assertions.assertEquals(0, acc.nextTokenEndIndex("-let"));
        Assertions.assertEquals(0, acc.nextTokenEndIndex("varlet"));
        Assertions.assertEquals(0, acc.nextTokenEndIndex("letvar"));
        Assertions.assertEquals(0, acc.nextTokenEndIndex("let3"));
    }
}
