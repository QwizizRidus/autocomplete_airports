package index;

import org.example.index.IndexGenerator;
import org.example.index.MyIndexGenerator;
import org.example.index.NameIndexProcessor;
import org.example.reader.CsvReader;
import org.example.reader.MyCsvReader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MyIndexGeneratorTest {

    @Test
    void generate_indexes_for_name_column_test() {
        CsvReader reader = new MyCsvReader(
                "C:\\MyProjects\\autocomplete_airports\\src\\main\\resources\\airports.csv");
        IndexGenerator generator = new MyIndexGenerator(reader, new ArrayList<>() {{
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
            add(null);
        }});

        var indexes = generator.getIndexes();
    }

    @Test
    void airport_name_index_generation_test() {
        CsvReader reader = new MyCsvReader(
                "C:\\MyProjects\\autocomplete_airports\\src\\main\\resources\\airports.csv");
        IndexGenerator generator = new MyIndexGenerator(reader, new ArrayList<>() {{
            add(new NameIndexProcessor());
        }});

        var indexes = generator.getIndexes();
    }
}
