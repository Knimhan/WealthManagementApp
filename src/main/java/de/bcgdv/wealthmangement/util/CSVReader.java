package de.bcgdv.wealthmangement.util;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static <T> List<T> readObjects(String file, Class type) {
        List<T> objects = new ArrayList<>();
        try {
            objects = new CsvToBeanBuilder(getReader(file))
                    .withType(type).build().parse();
        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return objects;
    }

    private static BufferedReader getReader(String filename) throws FileNotFoundException {
        InputStream inputStream = CSVReader.class.getResourceAsStream(filename);
        if (inputStream == null) throw new FileNotFoundException(filename + " not found");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(streamReader);
    }
}
