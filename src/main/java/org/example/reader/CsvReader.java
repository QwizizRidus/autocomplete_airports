package org.example.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CsvReader {
    List<String> getColumnNames() throws IOException;

    int getColumnCount() throws IOException;

    String getNextLine() throws IOException;

    void reopen(String path);
    void reopen();

    List<String> getLinesByIds(List<Integer> ids);

}
