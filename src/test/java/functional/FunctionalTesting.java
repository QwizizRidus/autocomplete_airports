package functional;

import org.example.bucket.ColumnBucketBuilder;
import org.example.evaluator.Evaluator;
import org.example.evaluator.ExpressionEvaluator;
import org.example.index.*;
import org.example.parser.ExpressionParser;
import org.example.parser.Parser;
import org.example.reader.CsvReader;
import org.example.reader.MyCsvReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;


public class FunctionalTesting {

    static CsvReader reader;
    static List<ColumnIndex> indexes;

    @BeforeAll
    static void init(){
        // creating reader based on .csv file
        reader = new MyCsvReader(
                "C:\\MyProjects\\autocomplete_airports\\src\\main\\resources\\airports.csv");
        // create list of processors for index creation
        List<IndexProcessor> processors = new ArrayList<>() {{
            add(new NameIndexProcessor());
        }};
        // create index generator and create indexes
        IndexGenerator indexGenerator = new MyIndexGenerator(reader, processors);
        indexes = indexGenerator.getIndexes();
    }


    @ParameterizedTest
    @CsvSource({
            ", Bower, 1",
            ", Bo, 68",
            ", Al, 98",
            "column[6]>10&column[7]<45, Al, 54",
            "column[6]>10||column[7]<45, Al, 91",
            "column[7]<45&column[10]=\"A\", A, 86",
            "column[8]>50&column[8]<500, A, 145",
            "column[6]>10||column[7]<45&column[10]=\"A\", A , 312",
            "(column[6]>10||column[7]<45)&column[10]=\"A\", A , 87",
    })
    void test(String filter, String airportName, String expectedRecordsCountString){
        if(filter == null) filter = "";
        int expectedRecordsCount = Integer.parseInt(expectedRecordsCountString);
        List<Object> tokenSequence = null; // contains tokenized filter sequence
        if (!filter.isEmpty()) {
            // parse filter to token sequence
            Parser parser = new ExpressionParser();
            parser.parseExpression(filter);
            tokenSequence = parser.getResult();
        }

        // get table block based on airport name index
        var tableBlock = indexes.get(0).getPositionsByCellValue(airportName);
        // get lines from the table by offsets. Offsets are fetched from the
        // constrained by index block
        var lines = reader.getLinesByOffsets(tableBlock);

        // we should filter lines because index bound is not precise. It includes resulting set, but
        // not necessarily one
        var filteredLines = ColumnBucketBuilder
                .filterIndexedLinesByUserInput(lines, airportName);
        // if the filter is empty the resulting lines have already been received
        if (filter.isEmpty()) {
            Assertions.assertEquals(expectedRecordsCount, filteredLines.size());
            return;
        }

        // create buckets for evaluation
        var columnBuckets = ColumnBucketBuilder
                .createColumnBucketsFromLines(filteredLines);

        // create evaluator and resolve search
        Evaluator evaluator = new ExpressionEvaluator(columnBuckets, tokenSequence);
        var resultingLines = evaluator.evaluate();

        Assertions.assertEquals(expectedRecordsCount, resultingLines.size());
    }
}
