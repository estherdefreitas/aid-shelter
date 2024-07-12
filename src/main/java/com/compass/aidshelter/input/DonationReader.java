package com.compass.aidshelter.input;

import com.compass.aidshelter.dtos.DonationDto;
import com.compass.aidshelter.repositories.DistributionCenterRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DonationReader {

    private static final DistributionCenterRepository distributionCenterRepository = new DistributionCenterRepository();
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
                        LocalDate.parse(getValueOrDefault(line, columns, "expirationDate", "01-01-1970"), formatter)
                );
                donationsItems.add(donationDto);
//                String itemType = line[1];
//                switch (itemType) {
//                    case "roupa":
//                        item = new Clothes(
//                                null,
//                                ClothesType.valueOf(line[2].toUpperCase()),
//                                ClothesSize.valueOf(line[4].toUpperCase()),
//                                ClothesGender.valueOf(line[5].toUpperCase())
//                        );
//                        break;
//                    case "higiene":
//                        item = new Toileries(
//                                null,
//                                ToileriesType.valueOf(line[2].toUpperCase().replace(" ","_")));
//                        break;
//                    case "comida":
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//                        LocalDate localDate = LocalDate.parse(line[7], formatter);
//                        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
//                        item = new Foods(
//                                line[2],
//                                Integer.parseInt(line[7]),
//                                line[6]
//                        );
//                        break;
//                    default:
//                        throw new IllegalArgumentException("Unknown item type: " + itemType);
//                }
//                donations.computeIfAbsent(distributionCenterRepository.findById(Long.valueOf(line[0])), k -> new ArrayList<>()).add(item);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        distributionCenterRepository.close();
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
}
