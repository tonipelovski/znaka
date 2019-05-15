package com.znaka;

import com.znaka.Exceptions.ExitException;
import com.znaka.StdLib.Library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Interpretator {
    private Evaluator evaluator;
    private boolean interpretatorMode = false;

    public Interpretator(String filename, boolean debug) throws IOException { // exec file
        BufferedReader reader = Files.newBufferedReader(Paths.get(filename),
                StandardCharsets.US_ASCII);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        evaluator = new Evaluator(parser);
        Library.addFunctions(evaluator.getFunctions());
        evaluator.setDebug(debug);

    }

    public Interpretator(){ // interprator mode
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        evaluator = new Evaluator(parser);
        Library.addFunctions(evaluator.getFunctions());
        evaluator.setDebug(true);
        interpretatorMode = true;
    }

    public void run(){

            while(true){
                if(interpretatorMode){
                    System.out.print("<o> ");
                }
                try {
                    evaluator.ProcessLine();
                } catch (Throwable e) {
                    ErrorMessagePrint(e);
                    System.out.println();
                    if(e instanceof ExitException){
                        break;
                    }
                }
            }

    }

    private void ErrorMessagePrint(Throwable exc){

        final Parser parser = evaluator.getParser();
        System.out.printf("0: %s\n  error: %s",
                parser.getLexer().getLast_line(), exc.getMessage());
    }
}
