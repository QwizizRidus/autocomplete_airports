package org.example.index;

import org.example.reader.CsvReader;

import java.io.IOException;
import java.util.*;

public class MyIndexGenerator implements IndexGenerator{
    private final CsvReader reader;
    private List<Map<String,List<Integer>>> indexes;
    private Set<Integer> keys;


    public MyIndexGenerator(CsvReader reader) {
        this.reader = reader;
        // TODO substitute hardcoded capacity with runtime evaluated one
        keys = new HashSet<>(8000);
    }

    @Override
    public List<Map<String,List<Integer>>> getIndexes() {
        init();
        String line;
        try {
            while ((line = reader.getNextLine()) != null) {
                inflateNameIndex(line,1);
            }

        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while index creating", e);
        }

        return indexes;
    }

    private void inflateNameIndex(String line, int columnNumber) {
        StringTokenizer st = new StringTokenizer(line, ",");
        int currRowIndex = Integer.parseInt(st.nextToken());
        keys.add(currRowIndex);
        int i = 1;
        while (i < columnNumber) {
            st.nextToken();
            i++;
        }
        var suitableMap = indexes.get(columnNumber);
        String firstLetter = st.nextToken().substring(1, 2);
        List<Integer> range = suitableMap.get(firstLetter);
        if (range == null) {
            var lst = new ArrayList<Integer>();
            lst.add(currRowIndex);
            suitableMap.put(firstLetter, lst);
        } else {
            range.add(currRowIndex);
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
            indexes.add(new HashMap<>());
        }
    }
}
