package org.example.index;

import java.util.List;
import java.util.Map;

public interface IndexGenerator {
    // TODO interface depends on implementation. Need to fix!
    List<ColumnIndex> getIndexes();
}
