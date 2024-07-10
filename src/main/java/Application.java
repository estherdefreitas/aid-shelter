
import com.compass.aidshelter.config.DbConfig;
import com.compass.aidshelter.entities.Clothes;
import com.compass.aidshelter.entities.Foods;
import com.compass.aidshelter.entities.Toileries;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import com.compass.aidshelter.entities.enums.ClothesType;
import com.compass.aidshelter.entities.enums.ToileriesType;
import com.compass.aidshelter.repositories.ClothesRepository;
import com.compass.aidshelter.repositories.FoodsRepository;
import com.compass.aidshelter.repositories.ToileriesRepository;

import java.time.Instant;
import java.util.Date;


public class Application {
    public static void main(String[] args) throws InterruptedException {

        DbConfig.build();

        FoodsRepository foodsRepository = new FoodsRepository();
        ClothesRepository clothesRepository = new ClothesRepository();
        ToileriesRepository toileriesRepository = new ToileriesRepository();

        Foods f1 = new Foods(null,2,"kg", Date.from(Instant.now()));
        foodsRepository.add(f1);
        System.out.println(foodsRepository.findAll());
        foodsRepository.close();

        Clothes c1 = new Clothes(null, ClothesType.AGASALHO, ClothesSize.G, ClothesGender.F);
        clothesRepository.add(c1);
        System.out.println(clothesRepository.findAll());
        clothesRepository.close();

        Toileries t1 = new Toileries(null, ToileriesType.SOAP);
        toileriesRepository.add(t1);
        System.out.println(toileriesRepository.findAll());
        toileriesRepository.close();

        Thread.sleep(99999);




    }
}
