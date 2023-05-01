package operation;

import org.example.bucket.ColumnBucket;
import org.example.operation.ComparisonOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ComparisonOperationTest {

    List<ColumnBucket> columnBuckets;

    @BeforeEach
    void init() {
        columnBuckets = new ArrayList<>();
        Map<String, Set<Integer>> firstColumnBucket = new HashMap<>();

        firstColumnBucket.put("\"KKA\"", new HashSet<>());
        firstColumnBucket.get("\"KKA\"").add(1);
        firstColumnBucket.get("\"KKA\"").add(2);
        firstColumnBucket.get("\"KKA\"").add(3);

        firstColumnBucket.put("\"GKA\"", new HashSet<>());
        firstColumnBucket.get("\"GKA\"").add(11);
        firstColumnBucket.get("\"GKA\"").add(12);
        firstColumnBucket.get("\"GKA\"").add(13);

        firstColumnBucket.put("\"AKA\"", new HashSet<>());
        firstColumnBucket.get("\"AKA\"").add(4);
        firstColumnBucket.get("\"AKA\"").add(5);
        firstColumnBucket.get("\"AKA\"").add(6);
        columnBuckets.add(new ColumnBucket(firstColumnBucket));
    }

    @Test
    void comparison_operation_columnFirst_test() {
        assertSetOfPositions(
                new ComparisonOperation("column[0]", "\"GKA\"", "=", columnBuckets).evaluate(),
                new HashSet<>() {
                    {
                        add(11);
                        add(12);
                        add(13);
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("column[0]", "\"GKA\"", "<", columnBuckets).evaluate(),
                new HashSet<>() {
                    {
                        add(4);
                        add(5);
                        add(6);
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("column[0]", "\"GKA\"", ">", columnBuckets).evaluate(),
                new HashSet<>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("column[0]", "\"GKA\"", "<>", columnBuckets).evaluate(),
                new HashSet<>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                        add(4);
                        add(5);
                        add(6);
                    }
                }
        );
    }

    @Test
    void comparison_operation_columnSecond_test() {
        assertSetOfPositions(
                new ComparisonOperation("\"GKA\"", "column[0]", "=", columnBuckets).evaluate(),
                new HashSet<>() {
                    {
                        add(11);
                        add(12);
                        add(13);
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("\"GKA\"", "column[0]", "<", columnBuckets).evaluate(),
                new HashSet<>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("\"GKA\"", "column[0]", ">", columnBuckets).evaluate(),
                new HashSet<>() {
                    {
                        add(4);
                        add(5);
                        add(6);
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("\"GKA\"", "column[0]", "<>", columnBuckets).evaluate(),
                new HashSet<>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                        add(4);
                        add(5);
                        add(6);
                    }
                }
        );
    }


    void assertSetOfPositions(Set<Integer> result, Set<Integer> assertion){
        if(result.size() == assertion.size()){
            for(var item : assertion){
                if(result.stream().noneMatch(pos -> pos.equals(item))){
                    throw new AssertionError("There is no " + item + " in result");
                }
            }
        }
        else throw new AssertionError("Different number of elements");
    }

}
