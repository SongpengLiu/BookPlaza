package com.example.bookplaza.search;
import com.example.bookplaza.trees.BooksAttributes;


/**
 * Grammar:
 * <exp>    ::=  <term> or <term> | <exp>
 * <term>   ::=  <factor> or <factor> & <term>
 * <factor> ::=  <coeff> > v | <coeff> < v | <coeff> = v | <contains> | <genre> |<coeff>
 * <coefficient> ::= <query tree> or ( <exp> )
 *
 * @author Tay Shao An(u7553225)
 */
public class Parser {
    /**
     * The following exception should be thrown if the parse is faced with series of tokens that do not
     * correlate with any possible production rule.
     */
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    // The tokenizer (class field) this parser will use.
    Tokenizer tokenizer;

    /**
     * Parser class constructor
     * Simply sets the tokenizer field.
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Does tokenizer.next() but catches token.IllegalTokenException exception
     *
     * @author Tay Shao An(u7553225)
     */
    public void safeNext() {
        try {
            tokenizer.next();
        } catch (Token.IllegalTokenException e) {
            throw new Parser.IllegalProductionException("Invalid token");
        }
    }


    /**
     * Adheres to the grammar rule:
     * <exp>    ::=  <term> / <term> | <exp>
     *
     * @return type: Exp.
     * @author Tay Shao An(u7553225)
     */
    public Exp parseExp(BooksAttributes attributes) {
        Token.Type prevToken = null;
        tokenizer.current();
        int inBracket = 0;
        String term = "";

        while (tokenizer.current() != null) {
            if (tokenizer.current().getType().equals(Token.Type.UNION) && inBracket == 0) {
                // if there is no term or there is no next token nothing to operate on
                if (term.isEmpty() || !tokenizer.hasNext()) throw new Parser.IllegalProductionException("expression is wrong");
                safeNext();
                // does a union with term and next token
                return new UnionExp((new Parser(new Tokenizer(term))).parseTerm(attributes), this.parseExp(attributes));
            }
            else if (tokenizer.current().getType().equals(Token.Type.LBRA)) inBracket += 1;
            else if (tokenizer.current().getType().equals(Token.Type.RBRA)) inBracket -= 1;

            // if two tokens in a row are ths same and it is not LBRA and RBRA
            if (tokenizer.current().getType().equals(prevToken)
                    && !tokenizer.current().getType().equals(Token.Type.LBRA)
                    &&  !tokenizer.current().getType().equals(Token.Type.RBRA)) {
                throw new Parser.IllegalProductionException("expression is wrong");
            }

            prevToken = tokenizer.current().getType();
            term += tokenizer.current().getToken();
            safeNext();
        }
        // if no more tokens and the brackets are not balanced
        if (inBracket != 0) throw new Parser.IllegalProductionException("unbalanced brackets expression is wrong");
        return (new Parser(new Tokenizer(term))).parseTerm(attributes);
    }

    /**
     * Adheres to the grammar rule:
     * <term>   ::=  <coefficient> / <coefficient> & <term>
     *
     * @return type: Exp.
     * @author Tay Shao An(u7553225)
     */
    public Exp parseTerm(BooksAttributes attributes) {
        //Token.Type prevToken = null;
        tokenizer.current();
        int inBracket = 0;
        String factor = "";

        while (tokenizer.current() != null) {
            if (tokenizer.current().getType().equals(Token.Type.INTER) && inBracket == 0) {
                // if there is no term or there is no next token nothing to operate on
                if (factor.isEmpty() || !tokenizer.hasNext())
                    throw new Parser.IllegalProductionException("term is wrong");
                safeNext();
                return new InterExp((new Parser(new Tokenizer(factor))).parseFactor(attributes),
                        this.parseTerm(attributes));
            }
            else if (tokenizer.current().getType().equals(Token.Type.LBRA)) inBracket += 1;
            else if (tokenizer.current().getType().equals(Token.Type.RBRA)) inBracket -= 1;
            factor += tokenizer.current().getToken();
            safeNext();
        }
        return (new Parser(new Tokenizer(factor))).parseFactor(attributes);
    }

    /**
     * Adheres to the grammar rule:
     * <factor> ::=  <coeff> > v | <coeff> < v | <coeff> = v | <contains> | <genre> |<coeff>
     *
     * @return type: Exp.
     * @author Tay Shao An(u7553225)
     */
    public Exp parseFactor(BooksAttributes attributes) {
        tokenizer.current();
        int inBracket = 0;
        String coefficient = "";
        Token.Type prevToken = null;

        while (tokenizer.current() != null) {
            if (tokenizer.current().getType().equals(Token.Type.LBRA)) inBracket += 1;
            else if (tokenizer.current().getType().equals(Token.Type.RBRA)) inBracket -= 1;
            else if (tokenizer.current().getType().equals(Token.Type.MT) && (inBracket == 0)) {
                // if no prevToken to operate on
                if (prevToken == null) throw new Parser.IllegalProductionException("bad expression parsefactor");
                safeNext();

                // coefficient to operate on
                Exp exp =  (new Parser(new Tokenizer(coefficient))).parseCoefficient(attributes);
                if (tokenizer.current().getType() == Token.Type.INT)
                    return new MoreThanExp<>(exp, Integer.parseInt(tokenizer.current().getToken()));
                else if (tokenizer.current().getType() == Token.Type.FLOAT)
                    return new MoreThanExp<>(exp, Float.parseFloat(tokenizer.current().getToken()));
                else
                    throw new Parser.IllegalProductionException("no numeric value after operator");
            }

            else if (tokenizer.current().getType().equals(Token.Type.LT) && (inBracket == 0)) {
                // if no prevToken to operate on
                if (prevToken == null) throw new Parser.IllegalProductionException("bad expression parsefactor");
                safeNext();

                // coefficient to operate on
                Exp exp = (new Parser(new Tokenizer(coefficient))).parseCoefficient(attributes);
                if (tokenizer.current().getType() == Token.Type.INT)
                    return new LessThanExp<>(exp, Integer.parseInt(tokenizer.current().getToken()));
                else if (tokenizer.current().getType() == Token.Type.FLOAT)
                    return new LessThanExp<>(exp, Float.parseFloat(tokenizer.current().getToken()));
                else
                    throw new Parser.IllegalProductionException("no numeric value after operator");
            }
            else if (tokenizer.current().getType().equals(Token.Type.EQUATE) && (inBracket == 0)) {
                if (prevToken == null) {throw new Parser.IllegalProductionException("bad expression parsefactor");}

                safeNext();
                Exp exp = (new Parser(new Tokenizer(coefficient))).parseCoefficient(attributes);
                if (tokenizer.current().getType() == Token.Type.INT)
                    return new EquateExp<>(exp, Integer.parseInt(tokenizer.current().getToken()));
                else if (tokenizer.current().getType() == Token.Type.FLOAT)
                    return new EquateExp<>(exp, Float.parseFloat(tokenizer.current().getToken()));
                else
                    throw new Parser.IllegalProductionException("no numeric value after operator");
            }
            else if (tokenizer.current().getType().equals(Token.Type.CONTAINS) && (inBracket == 0))
                return new ContainsExp(tokenizer.current().getToken());
            else if (tokenizer.current().getType().equals(Token.Type.GENRE) && (inBracket == 0))
                return new GenreExp(tokenizer.current().getToken());

            prevToken = tokenizer.current().getType();
            coefficient += tokenizer.current().getToken();
            safeNext();
        }
        return (new Parser(new Tokenizer(coefficient))).parseCoefficient(attributes);
    }


    /**
     * Adheres to the grammar rule:
     * <coefficient> ::= <search query> / ( <exp> )
     *
     * @return type: Exp.
     * @author Tay Shao An(u7553225)
     */
    public Exp parseCoefficient(BooksAttributes attributes) {
        tokenizer.current();
        int inBracket = 0;
        String unint = "";

        while (tokenizer.current() != null) {
            if (tokenizer.current().getType().equals(Token.Type.LBRA) ) {
                if (inBracket == 0) {
                    inBracket += 1;
                    safeNext();
                    continue;
                }
                inBracket += 1;
            }

            else if (tokenizer.current().getType().equals(Token.Type.RBRA)) {
                inBracket -= 1;
                if (inBracket == 0) return (new Parser(new Tokenizer(unint))).parseExp(attributes);
            }

            else if ((tokenizer.current().getType().equals(Token.Type.REVIEWS) ||
                    tokenizer.current().getType().equals(Token.Type.RATING) ||
                    (tokenizer.current().getType().equals(Token.Type.PRICE)))
                    && (inBracket == 0)) {
                return new QueryExp(tokenizer.current().getType(), attributes);
            }
            unint += tokenizer.current().getToken();
            safeNext();
        }
        throw new Parser.IllegalProductionException("bad expression parseCoefficient");
    }
}
