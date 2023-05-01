package org.example.index;

import org.example.reader.CsvReader;

import java.io.IOException;
import java.util.*;

public class MyIndexGenerator implements IndexGenerator{
    private final CsvReader reader;
    private final List<IndexProcessor> indexProcessors;

    public MyIndexGenerator(CsvReader reader,
                            List<IndexProcessor> indexProcessors) {
        this.reader = reader;
        this.indexProcessors = indexProcessors;
    }

    @Override
    public List<ColumnIndex> getIndexes() {
        try {
            inflateIndexes();
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while index creating", e);
        }

        List<ColumnIndex> result = new ArrayList<>();
        for(var processor:indexProcessors) {
            if (processor != null) result.add(processor.getIndex());
            else result.add(null);
        }

        return result;
    }

    private void inflateIndexes() throws IOException {
        String line;
        long offset = 0L;
        while ((line = reader.getNextLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            int lineLength = line.getBytes().length;
            int columnNumber = 0;
            while (st.hasMoreTokens()) {
                String str = st.nextToken();
                if(str.charAt(0) == '\"' && str.charAt(str.length()-1) != '\"') {
                    str += ", " + st.nextToken();
                }
                Long offsetObj = offset;
                for (var processor : indexProcessors) {
                    if(processor == null) continue;
                    processor.tryToAddIndex(columnNumber, str, offsetObj, lineLength);
                }
                columnNumber++;
            }
            offset += lineLength + 1;
        }
    }

}
