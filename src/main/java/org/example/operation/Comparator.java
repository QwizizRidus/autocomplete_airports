package org.example.operation;

import java.math.BigDecimal;

public class Comparator {

    public static boolean graterThan(String leftOperand, String rightOperand){
        return (compare(leftOperand, rightOperand, 1));
    }

    public static boolean equalsTo(String leftOperand, String rightOperand){
        return (compare(leftOperand, rightOperand, 0));
    }

    public static boolean lessThan(String leftOperand, String rightOperand){
        return (compare(leftOperand, rightOperand, -1));
    }

    private static boolean compare(String leftOperand, String rightOperand, int comparisonType) {
        if (comparisonType < -1 || comparisonType > 1)
            throw new IllegalArgumentException("comparisonType accept only -1, 0, 1 values");

        boolean isLeftNumber = isNumber(leftOperand);
        boolean isRightNumber = isNumber(rightOperand);
        if (isLeftNumber && isRightNumber) {
            return new BigDecimal(leftOperand).compareTo(
                    new BigDecimal(rightOperand)) == comparisonType;
        }
        if (!isLeftNumber && !isRightNumber) {
            int result = leftOperand.compareTo(rightOperand);
            switch (comparisonType) {
                case -1:
                    return result < 0;
                case 0:
                    return result == 0;
                case 1:
                    return result > 0;
            }
        }
        throw new IllegalArgumentException("operands have different types");
    }

    private static boolean isNumber(String str){
        return !(str.startsWith("\"") && str.endsWith("\""));
    }

}
