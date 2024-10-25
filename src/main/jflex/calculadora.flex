package com.chrisvega;

import java_cup.runtime.*;

%%
%public
%class IdLexer
%cup

digit = [0-9]
letter = [a-zA-Z]
whitespace = [ \t\n]

%{
    StringBuffer string = new StringBuffer();

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

%eofval{
    return symbol(ParserSym.EOF);
%eofval}

%%

{digit}+ { return symbol(ParserSym.NUMBER, Integer.valueOf(yytext())); }
"(" { return symbol(ParserSym.LPAREN, yytext()); }
")" { return symbol(ParserSym.RPAREN, yytext()); }
"+" { return symbol(ParserSym.PLUS, yytext()); }
"*" { return symbol(ParserSym.TIMES, yytext()); }

{whitespace}+ { /* ignore */ }
[^] { throw new Error("Illegal character <" + yytext() + ">"); }