package org.example.index;

import java.util.*;

public class ColumnIndex {
    private Map<String, Set<FilePosition>> indexContainer;

    public ColumnIndex(Map<String, Set<FilePosition>> indexContainer) {
        this.indexContainer = indexContainer;
    }

    public ColumnIndex() {
        indexContainer = new HashMap<>();
    }

    public Map<String, Set<FilePosition>> getIndexContainer() {
        return indexContainer;
    }

    public Set<String> getAllKeys(){
        return indexContainer.keySet();
    }

    public Set<FilePosition> getPositionsByKeys(List<String> keys) {
        Set<FilePosition> result = new HashSet<>();
        for (var key : keys) {
            var value = indexContainer.get(key);
            if (value == null)
                throw new NoSuchElementException("No corresponding elements for the key");
            result.addAll(value);
        }
        return result;
    }

}
