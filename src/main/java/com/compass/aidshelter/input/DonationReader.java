package com.compass.aidshelter.input;

import com.compass.aidshelter.dto.DonationDto;
import com.compass.aidshelter.entities.Donation;
import com.compass.aidshelter.repositories.DistributionCenterRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationReader {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public static List<DonationDto> readDonationsFromCsv(String filePath) {
        List<DonationDto> donationsItems = new ArrayList<>();
        DonationDto donationDto = null;
        Map<String, Integer> columns = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String[] line= reader.readNext();
            for (int i = 0; i < line.length; i++) {
                String col = line[i];
                columns.put(col, i);
            }
            while ((line = reader.readNext()) != null) {
                donationDto = new DonationDto(
                        getValueOrDefault(line, columns, "destination", "N/A"),
                        getValueOrDefault(line, columns, "itemType", "N/A"),
                        getValueOrDefault(line, columns, "description", "N/A"),
                        Integer.parseInt(getValueOrDefault(line, columns, "quantityItem", "0")),
                        getValueOrDefault(line, columns, "size", "N/A"),
                        getValueOrDefault(line, columns, "gender", "N/A"),
                        getValueOrDefault(line, columns, "unitMeasure", "N/A"),
                        getQuantityFood(line, columns),
                        parseOrDefault(getValueOrDefault(line, columns, "expirationDate", "01-01-1970"), LocalDate.MIN),
                        getValueOrDefault(line, columns, "typeToiletries","N/A")
                );
                donationsItems.add(donationDto);
                }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return donationsItems;
    }

    private static int getQuantityFood(String[] line, Map<String, Integer> columns) {
        try {
            return Integer.parseInt(line[columns.get("quantityFood")]);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    private static String getValueOrDefault(String[] line, Map<String, Integer> columns, String columnName, String defaultValue) {
        try {
            return line[columns.get(columnName)];
        } catch (IndexOutOfBoundsException e) {
            return defaultValue;
        }
    }

    private static LocalDate parseOrDefault(String date, LocalDate defaultValue){
        try{
            return LocalDate.parse(date, formatter);
        }catch (DateTimeParseException e){
            return defaultValue;
        }
    }
}
