package org.example.index;

import java.util.*;

public class ColumnIndex {
    private final Map<String, Set<FilePosition>> indexContainer;

    public ColumnIndex(Map<String, Set<FilePosition>> indexContainer) {
        this.indexContainer = indexContainer;
    }

    public ColumnIndex() {
        indexContainer = new HashMap<>();
    }

    public Map<String, Set<FilePosition>> getIndexContainer() {
        return indexContainer;
    }

    public Set<FilePosition> getPositionsByCellValue(String value){
        return indexContainer.get(value.substring(0,1));
    }



}
