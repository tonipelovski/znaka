package com.znaka.ParserStructures;

import com.znaka.Parser;
import com.znaka.Tokens.TokenMatches.Token;

import java.util.ArrayList;

public class ImportAST extends KeywordAST {
    private String fileImport;

    public ImportAST(String fileImport) {
        super("import");
        this.fileImport = fileImport;
    }

    public String getFileImport() {
        return fileImport;
    }

    public void setFileImport(String fileImport) {
        this.fileImport = fileImport;
    }

    @Override
    protected boolean matchAST(ArrayList<Token> tokens, Parser parser) {

        boolean flag_name = false;
        boolean flag_keyword = false;

        String fileName = "";
        String fileExtension = "";
        if(tokens.size() >= 4)
        for(Token token: tokens){
            if(token.getType().equals("keyword") && token.getValue().equals("import")){
                flag_keyword = true;
            }else if(token.getType().equals("symbol") && flag_name){
                fileExtension = token.getValue();
                parser.next(4);
                setFileImport(fileName + "." + fileExtension);
                return true;
            }else if(token.getType().equals("symbol") && flag_keyword){
                fileName = token.getValue();
                flag_name = true;
            }else if(token.getValue().equals(".")){
                continue;
            }else {
                return false;
            }
        }
        return false;    }

    @Override
    public String getText() {
        return "import";
    }

    @Override
    public String toString() {
        String toImportString = "";
        if(getFileImport() != null){
            toImportString = getFileImport().toString();
        }
        return "[" +
                "return:" + toImportString +
                ']';
    }
}
