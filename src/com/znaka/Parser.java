package com.znaka;

import java.io.IOException;

public class Parser {
    Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public void parseLIne() throws IOException {
        lexer.readLine();

    }
}
