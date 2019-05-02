package com.znaka.Tokens.TokenMatches;

import java.util.Objects;

public class Token {
    private String type;
    private String value;

    static public Token tokenFromString(String s1){
        return new Token(s1.substring(1, s1.indexOf(":")-1),
                s1.substring(s1.indexOf(":")+2, s1.length()-1));
    }

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean empty() { return type.isEmpty() || value.isEmpty(); }

    public String toString(){
        String token = "[" + type + " : " + value + "]";
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return type.equals(token.type) &&
                Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
