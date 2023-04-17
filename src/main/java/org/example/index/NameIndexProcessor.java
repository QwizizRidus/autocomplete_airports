package org.example.index;

import java.util.*;

public class NameIndexProcessor implements IndexProcessor{

    private final ColumnIndex indexContainer;

    public NameIndexProcessor() {
        this.indexContainer = new ColumnIndex();
    }

    @Override
    public void tryToAddIndex(int columnNumber, String str, Long offset, int length) {
        if (columnNumber != 1) return;

        String firstLetter = str.substring(1, 2);
        var innerMap = indexContainer.getIndexContainer();
        Set<FilePosition> positions = innerMap.computeIfAbsent(firstLetter, k -> new HashSet<>());
        positions.add(new FilePosition(offset, length));
    }

    @Override
    public ColumnIndex getIndex() {
        return indexContainer;
    }
}
