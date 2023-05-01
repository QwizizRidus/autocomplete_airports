package reader;

import org.example.index.FilePosition;
import org.example.reader.MyCsvReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyCsvReaderTest {

    MyCsvReader reader = new MyCsvReader(
            "C:\\MyProjects\\autocomplete_airports\\src\\main\\resources\\airports.csv");


    @Test
    void read_lines_by_position_test(){
        Set<FilePosition> positions = new HashSet<>();
        positions.add(new FilePosition(0L,151));
        positions.add(new FilePosition(152L,145));
        positions.add(new FilePosition(804L,157));
        var res = reader.getLinesByOffsets(positions);

        Assertions.assertTrue(res.contains("1,\"Goroka Airport\",\"Goroka\",\"Papua New Guinea\",\"GKA\",\"AYGA\",-6.081689834590001,145.391998291," +
                "5282,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\""));
        Assertions.assertTrue(res.contains("2,\"Madang Airport\",\"Madang\",\"Papua New Guinea\",\"MAG\",\"AYMD\",-5.20707988739,145.789001465,20,10,\"U\"," +
                "\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\""));
        Assertions.assertTrue(res.contains("6,\"Wewak International Airport\",\"Wewak\",\"Papua New Guinea\",\"WWK\",\"AYWK\",-3.58383011818,143.669006348," +
                "19,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\""));
    }

}
