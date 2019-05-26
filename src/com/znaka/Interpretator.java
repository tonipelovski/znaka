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
        evaluator = createEvaluator(Files.newBufferedReader(Paths.get(filename),
                StandardCharsets.US_ASCII));
        Library.addFunctions(evaluator.getFunctions());
        evaluator.setDebug(debug);

    }

    public Interpretator(){ // interprator mode
        evaluator = createEvaluator(new BufferedReader(new InputStreamReader(System.in)));
        Library.addFunctions(evaluator.getFunctions());
        evaluator.setDebug(true);
        interpretatorMode = true;
    }

    private static Evaluator createEvaluator(BufferedReader reader){
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        return new Evaluator(parser);
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
//                    e.printStackTrace();

                    if(e instanceof ExitException || !interpretatorMode){
                        break;
                    }
                }
            }

    }

    private void ErrorMessagePrint(Throwable exc){

        final Parser parser = evaluator.getParser();
        int linenum = interpretatorMode ? 1 : parser.getLexer().getLineNum()-1;
        System.out.printf("%d: %s\n  error: %s\n", linenum,
                parser.getLexer().getLast_line(), exc.getMessage());
    }
}
