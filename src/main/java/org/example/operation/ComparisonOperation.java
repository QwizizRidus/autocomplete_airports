package org.example.operation;

import org.example.bucket.ColumnBucket;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ComparisonOperation implements Operation{
    private final String leftOperand;
    private final String rightOperand;
    private final String operator;
    private final List<ColumnBucket> columnBuckets; // cell value to line number mapping

    public ComparisonOperation(String leftOperand,
                               String rightOperand,
                               String operator,
                               List<ColumnBucket> columnBuckets) {
        if(leftOperand == null || rightOperand == null || operator == null)
            throw new IllegalArgumentException("It's not possible to create a half-built operation. " +
                    "Some of constructor arguments are null");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
        this.columnBuckets = columnBuckets;
    }


    @Override
    public Set<Integer> evaluate() {
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
        var correspondingBucket = columnBuckets.get(columnNumber);
        filteredKeys = correspondingBucket.getAllKeys()
                .stream().filter(filter).collect(Collectors.toList());

        var result = new HashSet<Integer>();
        for(var key: filteredKeys){
            result.addAll(correspondingBucket.getValue(key));
        }

        return result;
    }


    private int getColumnNumber(String columnOperand){
        return columnOperand.length() == 10?
                Integer.parseInt(columnOperand.substring (7,9)):// column[10]
                Integer.parseInt(columnOperand.substring (7,8));// column[1]
    }

    private boolean isColumnOperand(String operand){
        return operand.contains("column[");
    }
}
