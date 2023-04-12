package parser;

import org.example.parser.ExpressionParser;
import org.example.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

public class ExpressionParserTest {

    Parser parser = new ExpressionParser();

    @Test
    void expression_parsing_test(){
        assert_parsing("column[1]>10&column[5]='GKA'",
                Arrays.asList("&","=", "'GKA'", "column[5]", ">", "10", "column[1]"));
        assert_parsing("a>10&((b<7||c=3)&c=8)||a>5",
                Arrays.asList("||",">","5","a","&","&","=","8","c","||","=","3","c","<","7","b",">","10","a"));
        assert_parsing("a>10&b<7||c=3&d<>2",
                Arrays.asList("||","&","<>","2","d","=","3","c","&","<","7","b",">","10","a"));
    }


    void assert_parsing(String expression, List<String> expectedResult){
        parser.parseExpression(expression);
        Assertions.assertEquals(expectedResult, parser.getResult());
    }

}
