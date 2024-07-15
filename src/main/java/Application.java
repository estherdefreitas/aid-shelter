import com.compass.aidshelter.config.DbConfig;
import com.compass.aidshelter.dto.ClothesDto;
import com.compass.aidshelter.dto.FoodsDto;
import com.compass.aidshelter.dto.ShelterDto;
import com.compass.aidshelter.dto.ToiletriesDto;
import com.compass.aidshelter.entities.*;
import com.compass.aidshelter.entities.enums.ClothesGender;
import com.compass.aidshelter.entities.enums.ClothesSize;
import com.compass.aidshelter.entities.enums.ToiletriesType;
import com.compass.aidshelter.enums.ItemTypes;
import com.compass.aidshelter.repositories.*;
import com.compass.aidshelter.services.DistributionCenterService;
import com.compass.aidshelter.services.DonationService;
import com.compass.aidshelter.services.OrderService;
import com.compass.aidshelter.services.ShelterService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        DbConfig.build();

        ClothesRepository clothesRepository = new ClothesRepository();
        FoodsRepository foodsRepository = new FoodsRepository();
        ToiletriesRepository toiletriesRepository = new ToiletriesRepository();
        DistributionCenterRepository distributionCenterRepository = new DistributionCenterRepository();
        DonationRepository donationRepository = new DonationRepository();
        ShelterRepository shelterRepository = new ShelterRepository();
        OrderItemRepository orderItemRepository = new OrderItemRepository();
        ItemRepository itemRepository = new ItemRepository();
        OrderRepository orderRepository = new OrderRepository();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        DistributionCenterService distributionCenterService = new DistributionCenterService(distributionCenterRepository);
        distributionCenterService.saveDistributionCenter(args[0]);

        DonationService donationService = new DonationService(clothesRepository, foodsRepository, toiletriesRepository, distributionCenterRepository, donationRepository);
        donationService.loadDonations(args[1]);

        OrderService orderService = new OrderService(orderRepository, distributionCenterRepository, donationService);

        ShelterService shelterService = new ShelterService(shelterRepository, orderItemRepository);

        Scanner scanner;
        scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("=== Menu ===");
            System.out.println("1 - Cadastro de Itens Doados");
            System.out.println("2 - Leitura de Itens Doados");
            System.out.println("3 - Edição de Itens Doados");
            System.out.println("4 - Exclusão de Itens Doados");
            System.out.println("5 - Cadastro de abrigo");
            System.out.println("6 - Leitura de abrigos");
            System.out.println("7 - Edição de abrigo");
            System.out.println("8 - Exclusão de abrigo");
            System.out.println("9 - Ordem de pedido de itens");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            option = scanner.nextInt();
            int itemOption = -1;
            scanner.nextLine();
            Long distributionCenterId;
            long donationId;
            long shelterId;
            ShelterDto shelterDto;
            Shelter shelter;
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
                                    donationService.saveClothesDonation(
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
                                    donationService.saveToiletriesDonation(
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
                                    donationService.saveFoodsDonation(
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
                            int newQuantity = getInt("Informe o novo valor:", scanner);
                            if(donationService.canDistributionCenterReceiveItemOfType(getDonationType(donation), newQuantity)){
                                donation.setQuantity(newQuantity);
                            } else {
                                System.out.println("Limite de itens excedido para esse centro de distribuição.\n");
                            }
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
                case 5:
                    shelterDto = new ShelterDto(
                            getString("Informe o nome do abrigo",scanner),
                            getString("Informe o endereço do abrigo", scanner),
                            getString("Informe o nome do responsável pelo abrigo", scanner),
                            getString("Informe o telefone do abrigo", scanner),
                            getString("Informe o e-mail do abrigo", scanner),
                            getString("Informe a capacidade do abrigo", scanner),
                            getString("Informe a porcentagem de ocupação do abrigo ",scanner)
                            );
                    Set<ConstraintViolation<ShelterDto>> violations = validator.validate(shelterDto);

                    if (!violations.isEmpty()) {
                        for (ConstraintViolation<ShelterDto> violation : violations) {
                            System.out.println(violation.getMessage());
                        }
                    } else {
                        shelterService.saveShelter(shelterDto);
                    }
                    break;
                case 6:
                    System.out.println("Abrigos cadastrados:\n");
                    printShelters(shelterRepository.findAll());
                    break;
                case 7:
                    shelterId = chooseShelterById("Qual abrigo você deseja editar?", shelterRepository, scanner);
                    if(shelterId == -1){
                        break;
                    }
                    shelter = shelterRepository.findById(shelterId);
                    System.out.println("Informe os novos dados do abrigo:");
                    shelterDto = new ShelterDto(
                            getString("Nome:", scanner),
                            getString("Endereço:", scanner),
                            getString("Responsável:", scanner),
                            getString("Telefone:", scanner),
                            getString("Email:", scanner),
                            getString("Capacidade de armazenamento:", scanner),
                            getString("Porcentagem de ocupação:", scanner)
                    );
                    shelterService.updateShelter(shelter.getId(), shelterDto);
                    break;
                case 8:
                    shelterId = chooseShelterById("Qual abrigo você deseja excluir?", shelterRepository, scanner);
                    shelterRepository.delete(shelterId);
                    break;
                case 9:
                    shelterId = chooseShelterById("Escolha o abrigo:", shelterRepository, scanner);
                    if (shelterId < 0){
                        break;
                    }
                    shelter = shelterRepository.findById(shelterId);
                    String itemType = ItemTypes.itemTypesToDomain(ItemTypes.valueOf(getString("Informe o tipo de item (roupa, comida, higiene):", scanner).toUpperCase()));
                    Item item = printAllItemsOfType(itemType, itemRepository, scanner);
                    OrderItem orderItem = new OrderItem();
                    orderItem.setItem(item);
                    orderItem.setQuantity(getInt("Informe a quantidade necessária: ", scanner));
                    orderItem.setShelter(shelter);
                    List<OrderItem> orderItems = new ArrayList<>();
                    orderItems.add(orderItem);
                    Order order = orderService.createOrderRequest(shelter, orderItems);
                    System.out.println("Pedido cadastrado com sucesso");
                    List<DistributionCenter> centers = orderService.findCentersForItem(item, orderItem.getQuantity());
                    if (centers.isEmpty()) {
                        System.out.println("Não há centros de distribuição disponíveis para atender a demanda.");
                    } else {
                        System.out.println("Centros de distribuição disponíveis:");
                        for(DistributionCenter center : centers){
                            System.out.println(center);
                        }

                    }

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
            shelterRepository.close();
            itemRepository.close();
            orderRepository.close();
        }

    private static Item printAllItemsOfType(String itemType, ItemRepository itemRepository, Scanner scanner) {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            System.out.println("Não há itens cadastrados.");
            return null;
        }
        System.out.println("Itens do tipo " + capitalizeFirstLetter(itemType) + ":");
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getClass().getSimpleName().equals(itemType)) {
                filteredItems.add(item);
            }
        }
        if(filteredItems.isEmpty()){
            System.out.println("Não há itens desse tipo.");
            return null;
        }
        filteredItems.forEach(System.out::println);
        long itemId;
        while (true) {
            try {
                System.out.println("Escolha um item pelo ID:");
                itemId = scanner.nextLong();
                scanner.nextLine();
                long finalItemId = itemId;
                if (filteredItems.stream().anyMatch(item -> item.getId().equals(finalItemId))) {
                    return itemRepository.findById(itemId);
                } else {
                    System.out.println("ID inválido. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            }
        }
    }

    private static long chooseShelterById(String question, ShelterRepository shelterRepository, Scanner scanner) {
        long shelterId;
        List<Shelter> shelters = shelterRepository.findAll();
        if(shelters.isEmpty()){
            System.out.println("Não há abrigos cadastrados");
            return -1;
        }
        System.out.println(question);
        printShelters(shelters);
        while (true) {
            try {
                shelterId = scanner.nextLong();
                scanner.nextLine();
                long finalShelterId = shelterId;
                if (shelters.stream().anyMatch(shelter -> shelter.getId().equals(finalShelterId))) {
                    return finalShelterId;
                } else {
                    System.out.println("ID inválido. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine();
            }
        }
    }

    private static void printShelters(List<Shelter> shelters) {
        if (shelters.isEmpty()) {
            System.out.println("Não há abrigos cadastrados.");
        } else {
            shelters.forEach(shelter -> {
                System.out.println("ID do abrigo: " + shelter.getId());
                System.out.println("Nome: " + shelter.getName());
                System.out.println("Endereço: " + shelter.getAddress());
                System.out.println("Responsável: " + shelter.getResponsible());
                System.out.println("Telefone: " + shelter.getPhone());
                System.out.println("E-mail: " + shelter.getEmail());
                System.out.println("Capacidade: " + shelter.getStorageCapacity());
                System.out.println("Ocupação: " + shelter.getOccupationPercentage() + "%");
                System.out.println("----------");
            });
        }
    }

    private static String getDonationType(Donation donation) {
        if(donation.getClothes() != null) {
            return "roupa";
        } else if (donation.getFoods() != null) {
            return "comida";
        } else if (donation.getToiletries() != null) {
            return "higiene";
        }
        throw new IllegalArgumentException("Não foi possível distinguir qual o tipo da doação, todos os valores estão nulos");
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
    private static Item printAllItemsAndReturnOneItem(List<Clothes> allClothes, List<Foods> allFoods, List<Toiletries> allToiletries,Scanner scanner) {
        List<Item> allItems = new ArrayList<>();
        allItems.addAll(allClothes);
        allItems.addAll(allFoods);
        allItems.addAll(allToiletries);
        for (int i = 0; i < allItems.size(); i++) {
            System.out.println((i + 1) + ": " + allItems.get(i));
        }
        int choice = -1;
        while (choice < 1 || choice > allItems.size()) {
            System.out.print("Enter the number of the item you want to choose: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > allItems.size()) {
                    System.out.println("Invalid choice. Please choose a number between 1 and " + allItems.size() + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        return allItems.get(choice - 1);
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
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
