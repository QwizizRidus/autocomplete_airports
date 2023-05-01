package org.example.operation;

import org.example.index.FilePosition;

import java.util.Set;

public class BooleanOperation implements Operation {

    private final Operation leftOperand;
    private final Operation rightOperand;
    private final String operator;

    public BooleanOperation(Operation leftOperand,
                            Operation rightOperand,
                            String operator) {
        if(leftOperand == null || rightOperand == null || operator == null)
            throw new IllegalArgumentException("It's not possible to create a half-built operation. " +
                    "Some of constructor arguments are null");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
    }


    @Override
    public Set<Integer> evaluate() {
        Set<Integer> result;
        result = leftOperand.evaluate();
        if (operator.equals("||")) {
            result.addAll(rightOperand.evaluate());
            return result;
        }
        if (operator.equals("&")) {
            result.retainAll(rightOperand.evaluate());
            return result;
        }
        throw new IllegalStateException("Unsupported boolean operator used for operation");
    }


}
