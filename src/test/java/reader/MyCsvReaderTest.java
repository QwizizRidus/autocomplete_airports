package reader;

import org.example.reader.MyCsvReader;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MyCsvReaderTest {

    MyCsvReader reader = new MyCsvReader(
            "C:\\MyProjects\\autocomplete_airports\\src\\main\\resources\\airports.csv");


    @Test
    void Read_Lines_By_Ids_test(){
        var ids = List.of(1,3,5);
        var res = reader.getLinesByIds(ids);
        System.out.println(res);
    }
}
