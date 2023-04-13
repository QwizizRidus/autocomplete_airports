package org.example.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnIndex {
    private Map<String, List<FilePosition>> indexContainer;

    public ColumnIndex(Map<String, List<FilePosition>> indexContainer) {
        this.indexContainer = indexContainer;
    }

    public ColumnIndex() {
        indexContainer = new HashMap<>();
    }

    public Map<String, List<FilePosition>> getIndexContainer() {
        return indexContainer;
    }

}
