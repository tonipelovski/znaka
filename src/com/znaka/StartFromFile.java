package com.znaka;

import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.LexerException;
import com.znaka.Exceptions.ParserException;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class StartFromFile {
    public static void main(String[] args) throws IOException, EvaluatorException, ParserException, LexerException {
        Interpretator interpretator;
        boolean debug = false;
        ArrayList<String> s = new ArrayList<>();
        for (String arg : args) {
            if(arg.equals("-d")){
                debug = true;
            }
            else {
                s.add(arg);
            }
        }
        if(s.size() == 0){
                interpretator = new Interpretator();
                interpretator.setDebug(debug);
        }
        else {
            try{

                interpretator = new Interpretator(s.get(0), debug);
            }catch (NoSuchFileException e){
                System.out.println("No such file: " + e.getMessage());
                return;
            }
        }
        interpretator.run();
        //reader.close();
    }
}
