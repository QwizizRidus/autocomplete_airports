package org.example.index;

import java.util.HashSet;
import java.util.Set;

public class SimilarQueriesIndexProcessor implements IndexProcessor{

    private final ColumnIndex indexContainer;
    private final int indexColumnNumber;

    public SimilarQueriesIndexProcessor(ColumnIndex indexContainer,
                                        int indexColumnNumber) {
        this.indexContainer = indexContainer;
        this.indexColumnNumber = indexColumnNumber;
    }

    @Override
    public void tryToAddIndex(int columnNumber, String str, Long offset, int length) {
        if(columnNumber!= indexColumnNumber) return;

        var innerMap = indexContainer.getIndexContainer();
        Set<FilePosition> positions = innerMap.computeIfAbsent(str, k -> new HashSet<>());
        positions.add(new FilePosition(offset, length));
    }

    @Override
    public ColumnIndex getIndex() {
        return indexContainer;
    }
}
