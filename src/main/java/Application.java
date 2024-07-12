import com.compass.aidshelter.config.DbConfig;
import com.compass.aidshelter.repositories.ClothesRepository;
import com.compass.aidshelter.repositories.DistributionCenterRepository;
import com.compass.aidshelter.repositories.FoodsRepository;
import com.compass.aidshelter.repositories.ToiletriesRepository;
import com.compass.aidshelter.services.DistributionCenterService;
import com.compass.aidshelter.services.DonationService;

public class Application {
    public static void main(String[] args) throws InterruptedException {

        DbConfig.build();

        ClothesRepository clothesRepository = new ClothesRepository();
        FoodsRepository foodsRepository = new FoodsRepository();
        ToiletriesRepository toiletriesRepository = new ToiletriesRepository();
        DistributionCenterRepository distributionCenterRepository = new DistributionCenterRepository();

        DistributionCenterService distributionCenterService = new DistributionCenterService(distributionCenterRepository);
        distributionCenterService.saveDistributionCenter(args[0]);


        DonationService donationService = new DonationService(clothesRepository, foodsRepository, toiletriesRepository, distributionCenterRepository);
        donationService.saveDonations(args[1]);

        Thread.sleep(99999);




    }
}
