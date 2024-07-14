import com.compass.aidshelter.config.DbConfig;
import com.compass.aidshelter.dto.ClothesDto;
import com.compass.aidshelter.dto.FoodsDto;
import com.compass.aidshelter.entities.DistributionCenter;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import com.compass.aidshelter.repositories.*;
import com.compass.aidshelter.services.DistributionCenterService;
import com.compass.aidshelter.services.DonationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        donationService.loadDonations(args[1]);

        Scanner scanner;
        scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("=== Menu ===");
            System.out.println("1. Cadastro de Itens Doados");
            System.out.println("2. Leitura de Itens Doados");
            System.out.println("3. Edição de Itens Doados");
            System.out.println("4. Exclusão de Itens Doados");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            option = scanner.nextInt();
            int itemOption = -1;
            scanner.nextLine();
            String inputString;
            Long distributionCenterId;

            switch (option) {
                case 1:
                    while (itemOption != 0) {
                        System.out.println("Para qual centro de distribuição você deseja doar:");
                        List<DistributionCenter> dcs = distributionCenterRepository.findAll();
                        dcs.forEach(center -> {System.out.println(center.getId() + "- Nome: " + center.getName() + ", Endereço: " + center.getAddress());});
                        List<Long> dcIds = dcs.stream().map(DistributionCenter::getId).collect(Collectors.toList());
                        distributionCenterId = scanner.nextLong();
                        scanner.nextLine();
                        while (!dcIds.contains(distributionCenterId)){
                            System.out.println("Esse centro não existe, escolha uma opção válida!");
                            dcs.forEach(center -> {System.out.println(center.getId() + "- Nome: " + center.getName() + ", Endereço: " + center.getAddress());});
                            distributionCenterId = scanner.nextLong();
                        }
                        System.out.println("Você deseja doar:\n1 - Roupa\n2 - Produto de higiene\n3 - Comida\n0 - Voltar:");
                        itemOption = scanner.nextInt();
                        scanner.nextLine();
                        int itemQuantity;
                        switch (itemOption) {
                            case 1:
                                ClothesDto newClothes = new ClothesDto();
                                System.out.println("Informe a quantidade de roupas:");
                                itemQuantity = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Informe a descrição da roupa:");
                                newClothes.setDescription(scanner.nextLine());
                                System.out.println("Informe o tamanho " + Arrays.toString(ClothesSize.values()) + ":");
                                inputString = scanner.nextLine();
                                while (!ClothesSize.isValidClothesSize(inputString)){
                                    System.out.println("Informe um tamanho válido!" + Arrays.toString(ClothesSize.values()) + ":");
                                    inputString = scanner.nextLine();
                                }
                                newClothes.setSize(inputString.toUpperCase());
                                System.out.println("Informe o gênero" + Arrays.toString(ClothesGender.values()) + ":");
                                inputString = scanner.nextLine();
                                while (!ClothesGender.isValidClothesGender(inputString)){
                                    System.out.println("Informe um gênero válido!" + Arrays.toString(ClothesGender.values()) + ":");
                                    inputString = scanner.nextLine();
                                }
                                newClothes.setGender(inputString.toUpperCase());
                                donationService.clothesDonation(newClothes, distributionCenterRepository.findById(distributionCenterId), itemQuantity);

                                break;
                            case 2:


                                break;
                            case 3:
                                FoodsDto newFoods = new FoodsDto();
                                System.out.println("Informe quantas comidas embaladas:");
                                itemQuantity = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Informe a descrição da comida:");
                                newFoods.setDescription(scanner.nextLine());
                                System.out.println("Informe a quantidade que a embalagem armazena");
                                newFoods.setQuantityFood(scanner.nextInt());
                                scanner.nextLine();
                                System.out.println("Informe a unidade de medida que a embalagem comporta:");
                                newFoods.setUnitMeasure(scanner.nextLine());
                                System.out.println("Informe a válidade:");
                                LocalDate expirationDate = null;
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                while (expirationDate == null) {
                                    System.out.println("Informe a validade (dd-MM-yyyy):");
                                    String userInput = scanner.nextLine();

                                    try {
                                        expirationDate = LocalDate.parse(userInput, formatter);
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Formato de data inválido. Por favor, use o formato dd-MM-yyyy.");
                                    }
                                }
                                newFoods.setExpirationDate(expirationDate);
                                donationService.foodsDonation(newFoods,distributionCenterRepository.findById(distributionCenterId), itemQuantity);

                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Opção inválida!\n");
                                Thread.sleep(1000);
                        }
                        System.out.println("Deseja fazer outra doação?");
                        System.out.println("0 - Não");
                        System.out.println("1 - Sim");
                        itemOption = scanner.nextInt();
                        scanner.nextLine();
                    }
                        break;
                case 2:
                    System.out.println("Itens doados:\n");
                    donationRepository.findAll().stream()
                            .filter(donation -> donation.getClothes() != null || donation.getFoods() != null || donation.getToiletries() != null)
                            .forEach(donation -> {
                                System.out.println("ID: " + donation.getId() + ", Quantidade: " + donation.getQuantity());

                                if (donation.getClothes() != null) {
                                    System.out.println("Descrição da roupa: " + donation.getClothes().getDescription() + "\nGênero da roupa: " + donation.getClothes().getGender() + "\nTamanho da roupa: " + donation.getClothes().getSize());
                                }
                                if (donation.getFoods() != null) {
                                    System.out.println("Descrição da comida: " + donation.getFoods().getDescription() + "\nData de expiração da comida: " + donation.getFoods().getExpirationDate() + "Quantidade da embalagem: " + donation.getFoods().getQuantityFood() + donation.getFoods().getUnitMeasure());
                                }
                                if (donation.getToiletries() != null) {
                                    System.out.println("Descrição do produto de higiene: " + donation.getToiletries().getDescription() + "\nTipo do produto de higiene: " + donation.getToiletries().getType());
                                }
                                System.out.println();
                            });
                    break;
                case 3:
                    //edit donated items
                    break;
                case 4:
                    //delete donated items
                    break;
                case 0:
                    System.out.println("Saindo...");
                    Thread.sleep(1000);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    Thread.sleep(1000);
                    }
            }


            clothesRepository.close();
            foodsRepository.close();
            toiletriesRepository.close();
            distributionCenterRepository.close();

        }
}
