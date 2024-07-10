import entities.Clothes;
import entities.Foods;
import entities.enums.ClothesGender;
import entities.enums.ClothesSize;
import entities.enums.ClothesType;
import repositories.ClothesRepository;


public class Application {
    public static void main(String[] args) {

        ClothesRepository clothesRepository = new ClothesRepository();

        Clothes c1 = new Clothes(null, ClothesType.AGASALHO, ClothesSize.G, ClothesGender.F);
        clothesRepository.addClothes(c1);
        System.out.println(clothesRepository.findAllClothes());

        clothesRepository.close();


    }
}
