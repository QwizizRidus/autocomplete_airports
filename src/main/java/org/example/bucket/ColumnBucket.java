package org.example.bucket;

import java.util.*;

public class ColumnBucket {
    private final Map<String, Set<Integer>> columnBucket;

    public ColumnBucket() {
        this.columnBucket = new HashMap<>();
    }

    public ColumnBucket(Map<String, Set<Integer>> columnBucket) {
        this.columnBucket = columnBucket;
    }

    public void addLineNumberByValue(String key, int lineNumber){
        var value = columnBucket.get(key);
        if (value == null) {
            var innerSet = new HashSet<Integer>();
            innerSet.add(lineNumber);
            columnBucket.put(key, innerSet);
        } else {
            value.add(lineNumber);
        }
    }

    public Set<String> getAllKeys(){
        return columnBucket.keySet();
    }

    public Set<Integer> getValue(String key){
        return columnBucket.get(key);
    }

}
