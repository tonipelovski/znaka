package com.znaka;

import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;

import java.io.*;
import java.net.URL;

public class StartFromFile {
    public static void main(String[] args) throws IOException, EvaluatorException, ParserException, LexerException {
        URL url = Main.class.getResource("ParserPrinting");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));

        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Evaluator evaluator = new Evaluator(parser);
        evaluator.ProcessLine();
        evaluator.ProcessLine();
        evaluator.ProcessLine();


        //evaluator.ProcessLine();
        while(parser.parseLine()){

        }
        System.out.println(parser.toString());

        //reader.close();
    }
}
