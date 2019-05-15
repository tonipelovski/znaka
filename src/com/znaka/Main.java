package com.znaka;

import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, LexerException, ParserException, EvaluatorException {
        // write your code here
        Interpretator interpretator = new Interpretator("TestResources/General/Basic.zk", true);
        //Interpretator interpretator = new Interpretator();
        interpretator.run();
    }
}
