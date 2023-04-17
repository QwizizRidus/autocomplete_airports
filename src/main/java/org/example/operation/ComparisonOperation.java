package org.example.operation;

import org.example.index.ColumnIndex;
import org.example.index.FilePosition;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ComparisonOperation implements Operation{
    private final String leftOperand;
    private final String rightOperand;
    private final String operator;
    private final List<ColumnIndex> indexes;

    public ComparisonOperation(String leftOperand,
                               String rightOperand,
                               String operator,
                               List<ColumnIndex> indexes) {
        if(leftOperand == null || rightOperand == null || operator == null)
            throw new IllegalArgumentException("It's not possible to create a half-built operation. " +
                    "Some of constructor arguments are null");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
        this.indexes = indexes;
    }


    @Override
    public Set<FilePosition> evaluate() {
        List<String> filteredKeys;
        Predicate<String> filter;
        int columnNumber;
        if (isColumnOperand(leftOperand)) {
            // column[i] > a
            columnNumber = getColumnNumber(leftOperand);
            switch (operator){
                case ">":
                    filter = key -> Comparator.graterThan(key,rightOperand);
                    break;
                case "<":
                    filter = key -> Comparator.lessThan(key,rightOperand);
                    break;
                case "=":
                    filter = key -> Comparator.equalsTo(key,rightOperand);
                    break;
                case "<>":
                    filter = key -> !Comparator.equalsTo(key,rightOperand);
                    break;
                default:
                    throw new IllegalStateException("Unsupported operator used for operation");

            }
        } else {
            // a > column[i]
            columnNumber = getColumnNumber(rightOperand);
            switch (operator){
                case ">":
                    filter = key -> Comparator.graterThan(leftOperand, key);
                    break;
                case "<":
                    filter = key -> Comparator.lessThan(leftOperand, key);
                    break;
                case "=":
                    filter = key -> Comparator.equalsTo(leftOperand, key);
                    break;
                case "<>":
                    filter = key -> !Comparator.equalsTo(leftOperand, key);
                    break;
                default:
                    throw new IllegalStateException("Unsupported operator used for operation creation");

            }
        }
        Set<String> keys = indexes.get(columnNumber).getAllKeys();
        filteredKeys = keys.stream().filter(filter).collect(Collectors.toList());
        return indexes.get(columnNumber).getPositionsByKeys(filteredKeys);
    }


    private int getColumnNumber(String columnOperand){
        return  Integer.parseInt(columnOperand.substring (7,8));
    }

    private boolean isColumnOperand(String operand){
        return operand.contains("column[");
    }
}
