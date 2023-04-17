package org.example.parser;

import java.util.List;

public interface Parser {
    void parseExpression(String query);
    List<Object> getResult();
}
