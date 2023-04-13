package org.example.index;

import org.example.reader.CsvReader;

import java.io.IOException;
import java.util.*;

public class MyIndexGenerator implements IndexGenerator{
    private final CsvReader reader;
    private List<ColumnIndex> indexes;
    private Set<Long> keys;
    private final List<IndexProcessor> indexProcessors;


    public MyIndexGenerator(CsvReader reader) {
        this.reader = reader;
        // TODO substitute hardcoded capacity with runtime evaluated one
        keys = new HashSet<>(8000);

        init();

        // TODO inflate collection with processors
        indexProcessors = new ArrayList<>();
        indexProcessors.add(new NameIndexProcessor(indexes.get(1).getIndexContainer()));
    }

    @Override
    public List<ColumnIndex> getIndexes() {
        try {
            inflateIndexes();
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while index creating", e);
        }

        return indexes;
    }

    private void inflateIndexes() throws IOException {
        String line;
        long offset = 0;
        while ((line = reader.getNextLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            int lineLength = line.getBytes().length;
            keys.add(offset);
            int columnNumber = 0;
            while (st.hasMoreTokens()) {
                String str = st.nextToken();
                for (var processor: indexProcessors) {
                    processor.tryToAddIndex(columnNumber, str, offset, lineLength);
                }
                columnNumber++;
            }
            offset += lineLength+1;
        }
    }

    private void init() {
        int columnCount = 14;
        // TODO do smth with hardcoded column count
//        try {
//            columnCount = reader.getColumnCount();
//        } catch (IOException e) {
//            throw new RuntimeException("Can't get column count from .csv file",e);
//        }
        indexes = new ArrayList<>(columnCount);
        for (int i = 0; i < columnCount; i++) {
            indexes.add(new ColumnIndex());
        }
    }
}
