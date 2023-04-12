package org.example.parser;

import org.example.exceptions.ParsingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionParser implements Parser{
    private final List<String> parsedTokens = new ArrayList<>();

    @Override
    public void parseExpression(String expression) {
        parsedTokens.clear();
        parse(expression);
    }
    private void parse(String expression) {
        int operatorIndex = tryFindOperator(expression,'|');
        if(operatorIndex != -1){
            parsedTokens.add("||");
            parse(skipParenthesise(expression.substring(operatorIndex+2)));
            parse(skipParenthesise(expression.substring(0,operatorIndex)));
            return;
        }
        operatorIndex = tryFindOperator(expression,'&');
        if(operatorIndex != -1){
            parsedTokens.add("&");
            parse(skipParenthesise(expression.substring(operatorIndex+1)));
            parse(skipParenthesise(expression.substring(0,operatorIndex)));
            return;
        }

        operatorIndex = tryFindOperator(expression, Arrays.asList('>','<','='));
        if(operatorIndex != -1){
            char c = expression.charAt(operatorIndex);
            if(c == '<' && expression.charAt(operatorIndex+1) == '>'){
                parsedTokens.add("<>");
                parsedTokens.add(expression.substring(operatorIndex+2));
                parsedTokens.add(expression.substring(0,operatorIndex));
            }else {
                parsedTokens.add(Character.toString(c));
                parsedTokens.add(expression.substring(operatorIndex+1));
                parsedTokens.add(expression.substring(0,operatorIndex));
            }
            return;
        }
        System.err.println("Unsuccessful attempt to parse: "+expression);
        throw new ParsingException("Unreachable code accessed. Something went wrong while parsing");
    }

    @Override
    public List<String> getResult() {
        return parsedTokens;
    }

    private int tryFindOperator(String str, char operator) {
        int braceCounter = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                braceCounter++;
            } else if (c == ')') {
                braceCounter--;
            } else if (c == operator) {
                if (braceCounter == 0) {
                    if(c=='|' && str.charAt(i+1) !='|') continue;
                    return i;
                }
            }
        }
        return -1;
    }

    private int tryFindOperator(String str, List<Character> operators) {
        int braceCounter = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                braceCounter++;
            } else if (c == ')') {
                braceCounter--;
            } else if (operators.contains(c)) {
                if (braceCounter == 0)
                    return i;
            }
        }
        return -1;
    }

    private String skipParenthesise(String str){
        if(str.charAt(0)=='(' && str.charAt(str.length()-1) == ')'){
            return str.substring(1,str.length()-1);
        }
        return str;
    }

}
