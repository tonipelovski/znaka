package com.znaka;

import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;
import com.znaka.Tokens.TokenMatches.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, LexerException, ParserException, EvaluatorException {
	// write your code here
        Evaluator evaluator;
        Lexer lexer;
        URL url = Main.class.getResource("stdlib");
        File file = new File(url.getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        evaluator = new Evaluator(parser);
        evaluator.run();
        reader.close();
        //String lexerOutput = "";
        //while (lexer.readLine()) {
        //   lexerOutput = lexerOutput.concat(lexer.tokensToString());
        //}
        //System.out.println(lexerOutput);
        //System.out.println(lexer.tokensToString());
        BufferedReader reader1 = Files.newBufferedReader(Paths.get("/home/theking/IdeaProjects/ZnakaInterpreter/src/com/znaka/ParserPrinting"),
                StandardCharsets.US_ASCII);
        lexer.resetInput(reader1);
        evaluator.run();
        //evaluator.ProcessLine();
       // evaluator.ProcessLine();


        //evaluator.ProcessLine();
        //evaluator.ProcessLine();
        //while(parser.parseLine()){

        //}
        System.out.println(parser.toString());
    }
}
