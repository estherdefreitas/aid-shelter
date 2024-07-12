import com.compass.aidshelter.config.DbConfig;
import com.compass.aidshelter.repositories.ClothesRepository;
import com.compass.aidshelter.repositories.DistributionCenterRepository;
import com.compass.aidshelter.repositories.FoodsRepository;
import com.compass.aidshelter.repositories.ToiletriesRepository;
import com.compass.aidshelter.services.DistributionCenterService;
import com.compass.aidshelter.services.DonationService;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;


public class Application {
    public static void main(String[] args) throws InterruptedException, IOException, CsvException {

        DbConfig.build();


        DistributionCenterService distributionCenterService = new DistributionCenterService(args[1]);

        ClothesRepository clothesRepository = new ClothesRepository();
        FoodsRepository foodsRepository = new FoodsRepository();
        ToiletriesRepository toiletriesRepository = new ToiletriesRepository();
        DistributionCenterRepository DistributionCenterRepository = new DistributionCenterRepository();

        DonationService donationService = new DonationService(clothesRepository, foodsRepository, toiletriesRepository, DistributionCenterRepository);

        donationService.saveDonations(args[0]);

        Thread.sleep(99999);




    }
}
