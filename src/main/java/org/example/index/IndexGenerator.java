package org.example.index;

import java.util.List;
import java.util.Map;

public interface IndexGenerator {
    List<Map<String,List<Integer>>> getIndexes();
}
