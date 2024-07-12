import com.compass.aidshelter.config.DbConfig;
import com.compass.aidshelter.repositories.*;
import com.compass.aidshelter.services.DistributionCenterService;
import com.compass.aidshelter.services.DonationService;

public class Application {
    public static void main(String[] args) throws InterruptedException {

        DbConfig.build();

        ClothesRepository clothesRepository = new ClothesRepository();
        FoodsRepository foodsRepository = new FoodsRepository();
        ToiletriesRepository toiletriesRepository = new ToiletriesRepository();
        DistributionCenterRepository distributionCenterRepository = new DistributionCenterRepository();
        DonationRepository donationRepository = new DonationRepository();

        DistributionCenterService distributionCenterService = new DistributionCenterService(distributionCenterRepository);
        distributionCenterService.saveDistributionCenter(args[0]);


        DonationService donationService = new DonationService(clothesRepository, foodsRepository, toiletriesRepository, distributionCenterRepository, donationRepository);
        donationService.saveDonations(args[1]);


        clothesRepository.close();
        foodsRepository.close();
        toiletriesRepository.close();
        distributionCenterRepository.close();
        Thread.sleep(99999);




    }
}
