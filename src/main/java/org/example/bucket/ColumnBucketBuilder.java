package org.example.bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ColumnBucketBuilder {

    // TODO: merge those two methods
    public static List<String> filterIndexedLinesByUserInput(List<String> lines, String input){
        var result = new ArrayList<String>();
        String schema = "\""+input;

        for (var line : lines) {
            StringTokenizer st = new StringTokenizer(line, ",");
            st.nextToken();
            var str = st.nextToken();
            if(str.charAt(0) == '\"' && str.charAt(str.length()-1) != '\"') {
                str += ", " + st.nextToken();
            }
            if (str.startsWith(schema)) {
                result.add(line);
            }
        }
        return result;
    }

    public static List<ColumnBucket> createColumnBucketsFromLines(List<String> lines) {
        List<ColumnBucket> result = new ArrayList<>(){{
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
            add(new ColumnBucket());
        }};

        int lineNumber = 0;
        for (var line : lines) {
            StringTokenizer st = new StringTokenizer(line, ",");
            int columnNumber = 0;
            while (st.hasMoreTokens()) {
                String str = st.nextToken();// cell value
                if(str.charAt(0) == '\"' && str.charAt(str.length()-1) != '\"') {
                    str += ", " + st.nextToken();
                }
                var correspondingBucket = result.get(columnNumber);
                correspondingBucket.addLineNumberByValue(str, lineNumber);
                columnNumber++;
            }
            lineNumber++;
        }
        return result;
    }
}
