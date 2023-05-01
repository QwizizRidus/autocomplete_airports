package org.example.index;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IndexGenerator {
    // TODO interface depends on implementation!!!!!!
    List<ColumnIndex> getIndexes();
}
