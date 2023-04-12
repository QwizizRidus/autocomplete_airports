package org.example.reader;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class MyCsvReader implements CsvReader, Closeable {

    private BufferedReader br;
    private String currentFilePath;

    public MyCsvReader(String path) {
        currentFilePath = path;
        reopenStream(currentFilePath);
    }

    @Override
    public List<String> getColumnNames() throws IOException {
        return null;
    }

    @Override
    public int getColumnCount() throws IOException {
        return 0;
    }

    @Override
    public String getNextLine() throws IOException {
        return br.readLine();
    }

    @Override
    public void reopen(String path) {
        reopenStream(path);
    }

    @Override
    public void reopen() {
        reopenStream(currentFilePath);
    }

    @Override
    public List<String> getLinesByIds(List<Integer> ids) {
        return br.lines().filter(
                str -> ids.contains(Integer.parseInt(str.split("^*,")[0]))
        ).collect(Collectors.toList());
    }


    private void reopenStream(String path){
        try {
            close();
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file",e);
        }
        catch (IOException e){
            throw new RuntimeException("Something went wrong during stream closing",e);
        }
    }

    @Override
    public void close() throws IOException {
        if (br!=null) {
            br.close();
        }
    }
}
