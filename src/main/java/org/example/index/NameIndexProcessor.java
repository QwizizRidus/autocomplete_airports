package org.example.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NameIndexProcessor implements IndexProcessor{

    private final Map<String, List<FilePosition>> indexContainer;

    public NameIndexProcessor(Map<String, List<FilePosition>> indexContainer) {
        this.indexContainer = indexContainer;
    }

    @Override
    public void tryToAddIndex(int columnNumber, String str, long offset, int length) {
        if (columnNumber != 1) return;

        String firstLetter = str.substring(1, 2);
        List<FilePosition> positions = indexContainer.get(firstLetter);
        if (positions == null) {
            var lst = new ArrayList<FilePosition>();
            lst.add(new FilePosition(offset, length));
            indexContainer.put(firstLetter, lst);
        } else {
            positions.add(new FilePosition(offset, length));
        }
    }
}
