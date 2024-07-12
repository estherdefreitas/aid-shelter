package com.compass.aidshelter.services;

import com.compass.aidshelter.entities.DistributionCenter;
import com.compass.aidshelter.input.DistributionCenterReader;
import com.compass.aidshelter.repositories.DistributionCenterRepository;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public class DistributionCenterService {

    public DistributionCenterService(String path) {
        DistributionCenterRepository distributionCenterRepository = new DistributionCenterRepository();
        try {
            List<DistributionCenter> distributionCenters = DistributionCenterReader.readDistributionCenters(path);
            distributionCenters.forEach(distributionCenterRepository::save);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
