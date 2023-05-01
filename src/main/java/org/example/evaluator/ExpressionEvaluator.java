package org.example.evaluator;

import org.example.bucket.ColumnBucket;
import org.example.operation.BooleanOperation;
import org.example.operation.ComparisonOperation;
import org.example.operation.Operation;

import java.util.List;
import java.util.Set;

public class ExpressionEvaluator implements Evaluator{

    private final List<ColumnBucket> columnBuckets;
    private final List<Object> tokenSequence;

    public ExpressionEvaluator(List<ColumnBucket> cellValueToLineNumbersByColumn,
                               List<Object> tokenSequence) {
        this.columnBuckets = cellValueToLineNumbersByColumn;
        this.tokenSequence = tokenSequence;
    }

    @Override
    public Set<Integer> evaluate() {
        for (int i = tokenSequence.size() - 1; i >= 0; i--) {
            // assume we have: ..., <, column[0], a, ...
            if (isComparisonOperator(tokenSequence.get(i))) {
                // place operation [a < column[0]] at the operator's place
                tokenSequence.set(i, new ComparisonOperation(
                        (String) tokenSequence.get(i + 2),
                        (String) tokenSequence.get(i + 1),
                        (String) tokenSequence.get(i),
                        columnBuckets
                ));
                // remove tokens 'a' and 'column[0]' from tokenSequence cuz they are
                // already encapsulated in operation object
                tokenSequence.remove(i+2);
                tokenSequence.remove(i+1);
            } else if (isBooleanOperator(tokenSequence.get(i))) {
                // place operation [{range_1} || {range2}] at the operator's place
                tokenSequence.set(i,new BooleanOperation(
                        (Operation)tokenSequence.get(i+1),
                        (Operation)tokenSequence.get(i+2),
                        (String)tokenSequence.get(i)
                ));
                // remove tokens {range_1} and {range_2} from tokenSequence cuz they are
                // already encapsulated in operation object
                tokenSequence.remove(i+2);
                tokenSequence.remove(i+1);
            }
        }

        if(tokenSequence.size()!=1)
            throw new RuntimeException("There are more than one element in the sequence after operation" +
                    "folding. Consider revising. Such behavior is not expected.");
        if(!(tokenSequence.get(0) instanceof Operation))
            throw new RuntimeException("Last sequence element after folding is not an Operation." +
                    " Consider revising. Such behavior is not expected.");

        return ((Operation)tokenSequence.get(0)).evaluate();
    }


    private boolean isComparisonOperator(Object obj){
        if(obj instanceof String){
            switch ((String) obj){
                case "<":
                case ">":
                case "<>":
                case "=":
                    return true;
            }
        }
        return false;
    }

    private boolean isBooleanOperator(Object obj){
        if(obj instanceof String){
            switch ((String) obj){
                case "&":
                case "||":
                    return true;
            }
        }
        return false;
    }






}
