import com.compass.aidshelter.config.DbConfig;
import com.compass.aidshelter.dto.ClothesDto;
import com.compass.aidshelter.dto.FoodsDto;
import com.compass.aidshelter.dto.ToiletriesDto;
import com.compass.aidshelter.entities.*;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import com.compass.aidshelter.entities.enums.ToiletriesType;
import com.compass.aidshelter.input.DonationReader;
import com.compass.aidshelter.repositories.*;
import com.compass.aidshelter.services.DistributionCenterService;
import com.compass.aidshelter.services.DonationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
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
            long donationId;
            switch (option) {
                case 1:
                    if(getInt("Deseja cadastrar:\n1 - De um arquivo csv\n2 - Manualmente", scanner) == 1){
                        System.out.println("ATENÇÃO! Seu csv deve conter um cabeçalho com os seguintes campos destination,itemType,description,quantityItem,size,gender,unitMeasure,quantityFood,expirationDate,typeToiletries! ");
                        String newPath = getString("Informe o caminho do arquivo:",scanner);
                        donationService.loadDonations(newPath);
                    } else {
                            distributionCenterId = getValidDistributionCenterId(
                                    "Para qual centro de distribuição você deseja doar:",
                                    distributionCenterRepository,
                                    scanner
                            );
                            itemOption = getInt(
                                    "Você deseja doar:\n1 - Roupa\n2 - Produto de higiene\n3 - Comida\n0 - Voltar:",
                                    scanner
                            );
                            int itemQuantity;
                            switch (itemOption) {
                                case 1:
                                    itemQuantity = getInt(
                                            "Informe a quantidade de roupas:",
                                            scanner
                                    );
                                    donationService.clothesDonation(
                                            getDonationClothesDto(scanner),
                                            distributionCenterRepository.findById(distributionCenterId),
                                            itemQuantity
                                    );

                                    break;
                                case 2:
                                    ToiletriesDto newToiletries = new ToiletriesDto();
                                    itemQuantity = getInt(
                                            "Informe a quantidade de produtos de higiene:",
                                            scanner
                                    );
                                    donationService.toiletriesDonation(
                                            getDonationToiletriesDto(scanner),
                                            distributionCenterRepository.findById(distributionCenterId),
                                            itemQuantity
                                    );
                                    break;
                                case 3:
                                    itemQuantity = getInt(
                                            "Informe quantas comidas embaladas:",
                                            scanner
                                    );
                                    donationService.foodsDonation(
                                            getDonationFoodsDto(scanner),
                                            distributionCenterRepository.findById(distributionCenterId),
                                            itemQuantity
                                    );
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Opção inválida!\n");
                                    Thread.sleep(1000);
                            }
                    }
                        break;
                case 2:
                    System.out.println("Itens doados:\n");
                    printDonations(donationRepository.findAll());
                    break;
                case 3:
                    donationId = chooseDonationById("Qual item você deseja editar?",donationRepository, scanner);
                    itemOption = getInt("O que você deseja alterar?\n1 - Quantidade do item\n2 - Destino\n3 - O item doado", scanner);
                    Donation donation = donationRepository.findById(donationId);
                    switch (itemOption){
                        case 1:
                            donation.setQuantity(getInt("Informe o novo valor:", scanner));
                            break;
                        case 2:
                            donation.setDistributionCenter(distributionCenterRepository
                                    .findById(getValidDistributionCenterId(
                                            "Informe qual centro de distribuição:",
                                            distributionCenterRepository,
                                            scanner)
                                    )
                            );
                            break;
                        case 3:
                            if(donation.getClothes() != null){
                                Clothes clothes = donation.getClothes();
                                System.out.println("Roupa: " + clothes.getDescription() + "\nGênero: " + clothes.getGender() + "\nTamanho: " + clothes.getSize());
                                System.out.println("Informe os novos dados da roupa:");
                                donation.setClothes(getDonationClothesDto(scanner).toEntity(donation.getClothes().getId()));
                            } else if (donation.getFoods() != null) {
                                Foods foods = donation.getFoods();
                                System.out.println("Comida: " + foods.getDescription() + "\nQuantidade: " + foods.getQuantityFood() + "\nUnidade de medida: " + foods.getUnitMeasure() + "\nValidade: " + foods.getExpirationDate());
                                System.out.println("Informe os novos dados da comida:");
                                donation.setFoods(getDonationFoodsDto(scanner).toEntity(donation.getFoods().getId()));
                            } else if (donation.getToiletries() != null) {
                                Toiletries toiletries = donation.getToiletries();
                                System.out.println("Produto de higiene: " + toiletries.getDescription() + "\nTipo: " + toiletries.getType());
                                System.out.println("Informe os novos dados do produto de higiene:");
                                donation.setToiletries(getDonationToiletriesDto(scanner).toEntity(donation.getToiletries().getId()));

                            }
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                    donationRepository.update(donation);


                    break;
                case 4:
                     donationId = chooseDonationById("Qual doação você deseja excluir?",donationRepository,scanner);
                     donationRepository.delete(donationId);
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

    private static ToiletriesDto getDonationToiletriesDto(Scanner scanner) {
        return new ToiletriesDto(getString("Informe a descrição do produto de higiene:", scanner), getValidToiletriesType(scanner));
    }

    private static FoodsDto getDonationFoodsDto(Scanner scanner) {
        return new FoodsDto(
                getString("Informe a descrição da comida:", scanner),
                getInt("Informe a quantidade que a embalagem armazena", scanner),
                getString("Informe a unidade de medida que a embalagem comporta:", scanner),
                getValidLocalDate("Informe a validade:", scanner, "dd-MM-yyyy")
        );
    }

    private static ClothesDto getDonationClothesDto(Scanner scanner) {
        return new ClothesDto(
                getString("Informe a descrição da roupa", scanner),
                getValidClothesSize("Informe o tamanho ", scanner),
                getValidClothesGender("Informe o gênero ", scanner)
        );
    }

    private static long chooseDonationById(String question, DonationRepository donationRepository, Scanner scanner) {
        long donationId;
        System.out.println(question);
        List<Donation> donations = donationRepository.findAll();
        printDonations(donations);

        while (true) {
            try {
                donationId = scanner.nextLong();
                long finalDonationId = donationId;
                if (donations.stream().anyMatch(donation -> donation.getId().equals(finalDonationId))) {
                    break;
                } else {
                    System.out.println("ID inválido. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine();
            }
        }

        scanner.nextLine();
        return donationId;
    }

    private static void printDonations(List<Donation> donations) {
        donations.stream()
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
    }

    private static LocalDate getValidLocalDate(String question, Scanner scanner, String pattern) {
        System.out.println(question);
        LocalDate expirationDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        while (expirationDate == null) {
            System.out.println("Informe a validade (" + pattern + "):");
            String userInput = scanner.nextLine();

            try {
                expirationDate = LocalDate.parse(userInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Por favor, use o formato dd-MM-yyyy.");
            }
        }
        return expirationDate;
    }

    private static String getValidToiletriesType(Scanner scanner) {
        String inputString;
        System.out.println("Informe o tipo de produto de higiene " + Arrays.toString(ToiletriesType.values()) + ":");
        inputString = scanner.nextLine();
        while (!ToiletriesType.isValidToiletriesType(inputString)){
            System.out.println("Informe um tipo de produto de higiene válido! " + Arrays.toString(ToiletriesType.values()) + ":");
            inputString = scanner.nextLine();
        }
        return inputString;
    }

    private static int getInt(String question, Scanner scanner) {
        int num;
        System.out.println(question);
        num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }

    private static String getString(String question, Scanner scanner) {
        System.out.println(question);
        return scanner.nextLine();
    }

    private static String getValidClothesSize(String question, Scanner scanner) {
        String inputString;
        System.out.println(question + Arrays.toString(ClothesSize.values()) + ":");
        inputString = scanner.nextLine();
        while (!ClothesSize.isValidClothesSize(inputString)){
            System.out.println("Informe um tamanho válido!" + Arrays.toString(ClothesSize.values()) + ":");
            inputString = scanner.nextLine();
        }
        return inputString;
    }

    private static String getValidClothesGender(String question, Scanner scanner) {
        String inputString;
        System.out.println(question + Arrays.toString(ClothesGender.values()) + ":");
        inputString = scanner.nextLine();
        while (!ClothesGender.isValidClothesGender(inputString)){
            System.out.println("Informe um gênero válido!" + Arrays.toString(ClothesGender.values()) + ":");
            inputString = scanner.nextLine();
        }
        return inputString.toUpperCase();
    }

    private static Long getValidDistributionCenterId(String question, DistributionCenterRepository distributionCenterRepository, Scanner scanner) {
        long distributionCenterId;
        System.out.println(question);
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
        return distributionCenterId;
    }
}
