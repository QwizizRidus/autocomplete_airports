package operation;

import org.example.index.ColumnIndex;
import org.example.index.FilePosition;
import org.example.operation.ComparisonOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ComparisonOperationTest {

    List<ColumnIndex> indexes;

    @BeforeEach
    void init() {
        indexes = new ArrayList<>();
        Map<String, Set<FilePosition>> firstColumnIndex = new HashMap<>();

        firstColumnIndex.put("\"KKA\"", new HashSet<>());
        firstColumnIndex.get("\"KKA\"").add(new FilePosition(1L, 1));
        firstColumnIndex.get("\"KKA\"").add(new FilePosition(2L, 2));
        firstColumnIndex.get("\"KKA\"").add(new FilePosition(3L, 3));

        firstColumnIndex.put("\"GKA\"", new HashSet<>());
        firstColumnIndex.get("\"GKA\"").add(new FilePosition(11L, 11));
        firstColumnIndex.get("\"GKA\"").add(new FilePosition(12L, 12));
        firstColumnIndex.get("\"GKA\"").add(new FilePosition(13L, 13));

        firstColumnIndex.put("\"AKA\"", new HashSet<>());
        firstColumnIndex.get("\"AKA\"").add(new FilePosition(4L, 4));
        firstColumnIndex.get("\"AKA\"").add(new FilePosition(5L, 5));
        firstColumnIndex.get("\"AKA\"").add(new FilePosition(6L, 6));
        indexes.add(new ColumnIndex(firstColumnIndex));
    }

    @Test
    void comparison_operation_columnFirst_test() {
        assertSetOfPositions(
                new ComparisonOperation("column[0]", "\"GKA\"", "=", indexes).evaluate(),
                new HashSet<>() {
                    {
                        add(new FilePosition(11L, 11));
                        add(new FilePosition(12L, 12));
                        add(new FilePosition(13L, 13));
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("column[0]", "\"GKA\"", "<", indexes).evaluate(),
                new HashSet<>() {
                    {
                        add(new FilePosition(4L, 4));
                        add(new FilePosition(5L, 5));
                        add(new FilePosition(6L, 6));
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("column[0]", "\"GKA\"", ">", indexes).evaluate(),
                new HashSet<>() {
                    {
                        add(new FilePosition(1L, 1));
                        add(new FilePosition(2L, 2));
                        add(new FilePosition(3L, 3));
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("column[0]", "\"GKA\"", "<>", indexes).evaluate(),
                new HashSet<>() {
                    {
                        add(new FilePosition(1L, 1));
                        add(new FilePosition(2L, 2));
                        add(new FilePosition(3L, 3));
                        add(new FilePosition(4L, 4));
                        add(new FilePosition(5L, 5));
                        add(new FilePosition(6L, 6));
                    }
                }
        );
    }

    @Test
    void comparison_operation_columnSecond_test() {
        assertSetOfPositions(
                new ComparisonOperation("\"GKA\"", "column[0]", "=", indexes).evaluate(),
                new HashSet<>() {
                    {
                        add(new FilePosition(11L, 11));
                        add(new FilePosition(12L, 12));
                        add(new FilePosition(13L, 13));
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("\"GKA\"", "column[0]", "<", indexes).evaluate(),
                new HashSet<>() {
                    {
                        add(new FilePosition(1L, 1));
                        add(new FilePosition(2L, 2));
                        add(new FilePosition(3L, 3));
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("\"GKA\"", "column[0]", ">", indexes).evaluate(),
                new HashSet<>() {
                    {
                        add(new FilePosition(4L, 4));
                        add(new FilePosition(5L, 5));
                        add(new FilePosition(6L, 6));
                    }
                }
        );

        assertSetOfPositions(
                new ComparisonOperation("\"GKA\"", "column[0]", "<>", indexes).evaluate(),
                new HashSet<>() {
                    {
                        add(new FilePosition(1L, 1));
                        add(new FilePosition(2L, 2));
                        add(new FilePosition(3L, 3));
                        add(new FilePosition(4L, 4));
                        add(new FilePosition(5L, 5));
                        add(new FilePosition(6L, 6));
                    }
                }
        );
    }


    void assertSetOfPositions(Set<FilePosition> result, Set<FilePosition> assertion){
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
