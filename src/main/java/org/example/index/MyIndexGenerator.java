package org.example.index;

import org.example.reader.CsvReader;

import java.io.IOException;
import java.util.*;

public class MyIndexGenerator implements IndexGenerator{
    private final CsvReader reader;
    private final List<IndexProcessor> indexProcessors;


    public MyIndexGenerator(CsvReader reader) {
        this.reader = reader;

        // TODO inflate collection with processors
        indexProcessors = new ArrayList<>();
        indexProcessors.add(new NameIndexProcessor());
    }

    @Override
    public List<ColumnIndex> getIndexes() {
        try {
            inflateIndexes();
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while index creating", e);
        }

        List<ColumnIndex> result = new ArrayList<>();
        indexProcessors.forEach(proc -> result.add(proc.getIndex()));
        return result;
    }

    private void inflateIndexes() throws IOException {
        String line;
        Long offset = 0L;
        while ((line = reader.getNextLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            int lineLength = line.getBytes().length;
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

}
