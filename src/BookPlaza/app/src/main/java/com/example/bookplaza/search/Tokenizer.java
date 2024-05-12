package com.example.bookplaza.search;

public class Tokenizer {
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.


    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and save it to currentToken
     */
    public Tokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }

    /**
     * This function will find and extract a next token from {@code _buffer} and
     * save the token to {@code currentToken}.
     */
    public void next() {
        boolean closequote = false;
        buffer = buffer.trim();     // remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        char firstChar = buffer.charAt(0);
        if (Character.isAlphabetic(firstChar)) {
            String token = Character.toString(firstChar);
            if (!buffer.isEmpty()) {
                int start = 0;
                for (int k = 1; k < buffer.length(); k++) {
                    if (Character.isAlphabetic(buffer.charAt(k))) {
                        token += buffer.charAt(k);
                    }
                    else {
                        start = k;
                        break;
                    }
                }
                switch (token) {
                    case "rating":
                        currentToken = new Token(token, Token.Type.RATING);
                        break;
                    case "reviews":
                        currentToken = new Token(token, Token.Type.REVIEWS);
                        break;
                    case "price":
                        currentToken = new Token(token, Token.Type.PRICE);
                        break;
                    case "genre":
                        if (buffer.charAt(start) != '\"') throw new Token.IllegalTokenException("no open quote detected error");
                        token += "\"";
                        closequote = false;
                        for (int k = start+1; k < buffer.length(); k++) {
                            if (buffer.charAt(k) != '"') {
                                token += buffer.charAt(k);
                            }
                            else {
                                System.out.println("end: token:" + token);
                                token += buffer.charAt(k);
                                currentToken = new Token(token, Token.Type.GENRE);
                                closequote = true;
                                break;
                            }
                        }
                        if (!closequote) throw new Token.IllegalTokenException("no closed quote detected aborting");
                        break;
                    case "title":
                        if (buffer.charAt(start) != '\"') throw new Token.IllegalTokenException("no open quote detected error");
                        token += "\"";
                        for (int k = start+1; k < buffer.length(); k++) {
                            if (buffer.charAt(k) != '"') {
                                token += buffer.charAt(k);
                            }
                            else {
                                System.out.println("end: token:" + token);
                                token += buffer.charAt(k);
                                currentToken = new Token(token, Token.Type.CONTAINS);
                                closequote = true;
                                break;
                            }
                        }
                        if (!closequote) throw new Token.IllegalTokenException("no closed quote detected aborting");
                        break;
                    default:
                        throw new Token.IllegalTokenException("unrecognised tree query");
                }
            }
        }
        else if (firstChar == '(') currentToken = new Token("(", Token.Type.LBRA);
        else if (firstChar == ')') currentToken = new Token(")", Token.Type.RBRA);
        else if (firstChar == '=') currentToken = new Token("=", Token.Type.EQUATE);
        else if (firstChar == '>') currentToken = new Token(">", Token.Type.MT);
        else if (firstChar == '<') currentToken = new Token("<", Token.Type.LT);
        else if (firstChar == '&') currentToken = new Token("&", Token.Type.INTER);
        else if (firstChar == '|') currentToken = new Token("|", Token.Type.UNION);
        else if (Character.isDigit(firstChar)) {
            String token = Character.toString(firstChar);
            boolean floatCheck = false;
            if (!buffer.isEmpty()) {
                for (int k = 1; k < buffer.length(); k++) {
                    if (Character.isDigit( buffer.charAt(k))) token += buffer.charAt(k);
                    else if (buffer.charAt(k) == '.') {
                        if (floatCheck) throw new Token.IllegalTokenException("two periods in a token error");
                        token += buffer.charAt(k);
                        floatCheck = true;
                    }
                    else break;
                }
            }
            if (floatCheck) currentToken = new Token(token, Token.Type.FLOAT);
            else currentToken = new Token(token, Token.Type.INT);
        }
        else throw new Token.IllegalTokenException("token not recognized");
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted by {@code next()}
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Check whether tokenizer still has tokens left
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}
