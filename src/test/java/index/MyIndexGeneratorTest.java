package index;

import org.example.index.IndexGenerator;
import org.example.index.MyIndexGenerator;
import org.example.reader.CsvReader;
import org.example.reader.MyCsvReader;
import org.junit.jupiter.api.Test;

public class MyIndexGeneratorTest {

    @Test
    void generate_indexes_for_name_column_test() {
        CsvReader reader = new MyCsvReader(
                "C:\\MyProjects\\autocomplete_airports\\src\\main\\resources\\airports.csv");
        IndexGenerator generator = new MyIndexGenerator(reader);

        var indexes = generator.getIndexes();
    }
}
