package com.znaka;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        //Interpretator interpretator = new Interpretator("TestResources/EvaluatorResources/Recursion.zk", true);
        Interpretator interpretator = new Interpretator();
        interpretator.run();
    }
}
