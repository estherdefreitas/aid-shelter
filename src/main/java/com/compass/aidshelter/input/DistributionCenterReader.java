package com.compass.aidshelter.input;

import com.compass.aidshelter.entities.DistributionCenter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DistributionCenterReader {
    public static List<DistributionCenter> readDistributionCenters(String csvFilePath) throws IOException, CsvException {
        List<DistributionCenter> distributionCenters = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath, StandardCharsets.UTF_8))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                Long id = null;
                String name = values[0];
                String address = values[1];
                String responsible = values[2];
                String phone = values[3];

                DistributionCenter distributionCenter = new DistributionCenter(id,name, address, responsible, phone);
                distributionCenters.add(distributionCenter);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return distributionCenters;
    }
}
