import com.compass.aidshelter.config.DbConfig;
import com.compass.aidshelter.services.DistributionCenterService;

public class Application {
    public static void main(String[] args) throws InterruptedException {

        DbConfig.build();

        DistributionCenterService distributionCenterService = new DistributionCenterService(args[1]);



        Thread.sleep(99999);




    }
}
