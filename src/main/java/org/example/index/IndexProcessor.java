package org.example.index;

import java.util.List;
import java.util.Map;

public interface IndexProcessor {
    void tryToAddIndex(int columnNumber, String str, Long offset, int length);

    ColumnIndex getIndex();
}
