package org.example;

import org.example.bucket.ColumnBucketBuilder;
import org.example.evaluator.Evaluator;
import org.example.evaluator.ExpressionEvaluator;
import org.example.index.*;
import org.example.parser.ExpressionParser;
import org.example.parser.Parser;
import org.example.reader.CsvReader;
import org.example.reader.MyCsvReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Entry point
        var scanner = new Scanner(System.in);
        // creating reader based on .csv file
        CsvReader reader = new MyCsvReader(
                "C:\\MyProjects\\autocomplete_airports\\src\\main\\resources\\airports.csv");
        // create list of processors for index creation
        List<IndexProcessor> processors = new ArrayList<>() {{
            add(new NameIndexProcessor());
        }};
        // create index generator and create indexes
        IndexGenerator indexGenerator = new MyIndexGenerator(reader, processors);
        List<ColumnIndex> indexes = indexGenerator.getIndexes();

        // ask user for filter
        System.out.println("Please, enter the filter:");
        var filter = scanner.nextLine();

        List<Object> tokenSequence = null; // contains tokenized filter sequence
        if (!filter.isEmpty()) {
            // parse filter to token sequence
            Parser parser = new ExpressionParser();
            parser.parseExpression(filter);
            tokenSequence = parser.getResult();
        }

        // ask user for request
        System.out.println("Please, enter the airport name:");
        var airportName = scanner.nextLine();
        var start = System.nanoTime();
        long end;

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
            end = (System.nanoTime() - start)/1_000_000;
            System.out.println("Results: ");
            for (var line : filteredLines) {
                System.out.println(line);
            }
            System.out.println(end + " ms");
            System.out.println("Entries: " + filteredLines.size());
            return;
        }

        // create buckets for evaluation
        var columnBuckets = ColumnBucketBuilder
                .createColumnBucketsFromLines(filteredLines);

        // create evaluator and resolve search
        Evaluator evaluator = new ExpressionEvaluator(columnBuckets, tokenSequence);
        var resultingLines = evaluator.evaluate();
        // print search results
        end = (System.nanoTime() - start)/1_000_000;
        System.out.println("Results: ");
        for (var lineNumber : resultingLines) {
            System.out.println(lines.get(lineNumber));
        }
        System.out.println("Entries: " + resultingLines.size());
        System.out.println(end + " ms");
    }
}