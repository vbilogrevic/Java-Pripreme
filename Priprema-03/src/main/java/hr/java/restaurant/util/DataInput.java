package hr.java.restaurant.util;

import hr.java.restaurant.exception.DuplicateEntryException;
import hr.java.restaurant.exception.InvalidValueException;
import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static hr.java.production.main.Main.log;

/**
 * Klasa koja služi za kreiranje novih objekata
 */
public class DataInput {

    public static final Integer NUMBER_OF_CATEGORIES = 3;
    public static final Integer NUMBER_OF_INGREDIENTS = 5;
    public static final Integer NUMBER_OF_MEALS = 3;
    public static final Integer NUMBER_OF_EMPLOYEES = 5;
    public static final Integer NUMBER_OF_RESTAURANTS = 3;
    public static final Integer NUMBER_OF_ORDERS = 3;

    public static long idBrojac = 0;
    public static final BigDecimal MINIMAL_SALARY = new BigDecimal(1400);

    /**
     * Metoda za kreiranje novog objekta Category
     * @param input omogućuje korisniku unos traženih vrijednosti
     * @return novi objekt Category
     */
    public static Category nextCategory(Scanner input) {
        System.out.print("Naziv: ");
        String name = input.nextLine();
        System.out.print("Opis: ");
        String description = input.nextLine();

        return new Category(idBrojac++, name, description);
    }

    /**
     * Metoda za kreiranje nove namirnice
     * @param input omogućuje korisniku unos traženih vrijednosti
     * @param categories lista kategorija
     * @return novi objekt Ingredient
     */
    public static Ingredient nextIngredient(Scanner input, List<Category> categories) {
        System.out.print("Naziv: ");
        String name = input.nextLine();

        System.out.println("Kategorije");
        for(int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println((i + 1) + ". kategorija: " + categories.get(i).getName());
        }
        Integer categoryIndex = InvalidInput.validatePositiveInteger(input, "Odaberite kategoriju: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
        Category category = categories.get(categoryIndex);

        BigDecimal kcal = InvalidInput.validatePositiveBigDecimal(input, "Kalorije: ", Messages.INVALID_BIGDECIMAL_INPUT_AND_NEGATIVE_ERROR);
        input.nextLine();

        System.out.print("Način pripreme: ");
        String preparationMethod = input.nextLine();

        return new Ingredient(idBrojac++, name, category, kcal, preparationMethod);
    }

    /**
     * Metoda za kreiranje novog jela
     * @param input omogućuje korisniku unos traženih vrijednosti
     * @param categories lista kategorija
     * @param ingredients lista namirnica
     * @param meals lista jela (služi za usporedbu da li već posotji uneseno jelo)
     * @return novi objek Meal
     */
    public static Meal nextMeal(Scanner input, List<Category> categories, List<Ingredient> ingredients, List<Meal> meals) {

        Integer typeOfMeal;
        do{
            typeOfMeal = InvalidInput.validatePositiveInteger(input, "Odaberite\n1. Vegan\n2. Vegetarian\n3. Meat\nOdaberite: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);
            if(typeOfMeal < 1 || typeOfMeal > 3){
                System.out.println("Pogrešan unos, ponovite!");
            }
        }while(typeOfMeal < 1 || typeOfMeal > 3);
        input.nextLine();

        String name;
        while (true) {
            try {
                System.out.print("naziv: ");
                name = input.nextLine();

                InvalidInput.checkForDuplicateMealName(name, meals);
                break;

            } catch (DuplicateEntryException e) {
                System.out.println("Pogreška: " + e.getMessage() + " Pokušajte ponovo.");
                log.error("POGREŠKA! Kreirano jelo koje već postoji!");
            }
        }


        System.out.println("Kategorije");
        for(int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println((i + 1) + ". kategorija: " + categories.get(i).getName());
        }
        Integer categoryIndex = InvalidInput.validatePositiveInteger(input, "Odaberite kategoriju: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
        Category category = categories.get(categoryIndex);

        System.out.println("Sastojci");
        for(int i = 0; i < NUMBER_OF_INGREDIENTS; i++) {
            System.out.println((i + 1) + ". sastojak: " + ingredients.get(i).getName());
        }
        Integer numberOfIngredients = InvalidInput.validatePositiveInteger(input, "Koliko sastojaka želite odabrati: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);

        List<Ingredient> tempIngredients = new ArrayList<>();
        for(int i = 0; i < numberOfIngredients; i++) {
            Integer ingredientIndex = InvalidInput.validatePositiveInteger(input, "Unesite sastojak: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
            tempIngredients.add(ingredients.get(ingredientIndex));
        }

        BigDecimal price;
        while(true){
            try{
                price = InvalidInput.validatePositiveBigDecimal(input, "Cijena: ", Messages.INVALID_BIGDECIMAL_INPUT_AND_NEGATIVE_ERROR);
                if(price.compareTo(new BigDecimal(45)) > 0) {
                    throw new InvalidValueException("Cijena jela ne može biti veća od 45");
                }
                break;
            }catch (InvalidValueException e){
                System.out.println("Pogrešan unos, " + e.getMessage());
                log.error("POGREŠKA! Unesena previsoka cijena jela!");
            }
        }
        input.nextLine();

        return switch(typeOfMeal){
          case 1 -> {
              Integer kcal = InvalidInput.validatePositiveInteger(input, "Kalorije: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);
              input.nextLine();
              yield new VeganMeal(idBrojac++, name, category, tempIngredients, price, kcal); }
          case 2 -> {
              System.out.print("Sadrži li jelo orašaste plodove (da / ne): ");
              String nutsString = input.nextLine();
              Boolean containsNuts;
              if(nutsString.equalsIgnoreCase("da")) {
                  containsNuts = true;
              }else{
                  containsNuts = false;
              }
              yield new VegetarianMeal(idBrojac++, name, category, tempIngredients, price, containsNuts); }
          case 3 -> {
              System.out.print("Vrsta mesa: ");
              String meatType = input.nextLine();
              yield new MeatMeal(idBrojac++, name, category, tempIngredients, price, meatType); }
            default -> throw new IllegalStateException("Unexpected value: " + typeOfMeal);
        };
    }

    /**
     * Služi za kreiranje novog zaposlenika
     * @param input omogućuje korisniku unos traženih vrijendosti
     * @param employees - lista zaposlenika koja služi za usporedbu da li već posotoji uneseno zaposlenik
     * @return novi objek Person
     */
    public static Person nextPerson(Scanner input, List<Person> employees) {
        Integer typeOfEmployee;
        do{
            typeOfEmployee = InvalidInput.validatePositiveInteger(input, "Odaberite vrstu zaposlenika\n1. Kuhar\n2. Konobar\n3. Dostavljač\nOdaberite: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);
            if(typeOfEmployee < 1 || typeOfEmployee > 3){
                System.out.println("Pogrešan unos, ponovite!");
            }
        }while(typeOfEmployee < 1 || typeOfEmployee > 3);
        input.nextLine();

        String firstName, lastName;

        while(true){
            try{
                System.out.print("Ime: ");
                firstName = input.nextLine();
                System.out.print("Prezime: ");
                lastName = input.nextLine();

                InvalidInput.checkForDuplicateEmployeer(firstName, lastName, employees);
                break;
            }catch (DuplicateEntryException e){
                System.out.println("Pogreška, " + e.getMessage());
                log.error("POGREŠKA! Unesen zaposlenik koji već postoji!");
            }
        }

        BigDecimal salary;
        while(true) {
            try{
                salary = InvalidInput.validatePositiveBigDecimal(input, "Plaća: ", Messages.INVALID_BIGDECIMAL_INPUT_AND_NEGATIVE_ERROR);
                if(salary.compareTo(MINIMAL_SALARY) < 0) {
                    throw new InvalidValueException("Plaća ne može biti manja od " + MINIMAL_SALARY);
                }
                break;
            } catch (InvalidValueException e) {
                System.out.println("Pogrešan unos " + e.getMessage());
                log.error("POGREŠKA! Unesena plaća koja je niža pd minimalne!");
            }
        }
        input.nextLine();

        System.out.print("Datum početka ugovora: ");
        String startDateString = input.nextLine();
        DateTimeFormatter formatterstart = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate startDate = LocalDate.parse(startDateString, formatterstart);

        System.out.print("Datum završetka ugovora: ");
        String endDateString = input.nextLine();
        DateTimeFormatter formatterend = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate endDate = LocalDate.parse(endDateString, formatterend);

        Integer typeOfContract;
        do{
            typeOfContract = InvalidInput.validatePositiveInteger(input, "Vrsta ugovora\n1. Full time\n2. Part time\nOdaberite: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);
            if(typeOfContract < 1 || typeOfContract > 2){
                System.out.println("Pogrešan unos, ponovite!");
            }
        }while(typeOfContract < 1 || typeOfContract > 2);

        Contract.ContractType contractType = null;
        switch(typeOfContract){
            case 1 -> contractType = Contract.ContractType.FULL_TIME;
            case 2 -> contractType = Contract.ContractType.PART_TIME;
        }

        Contract contract = new Contract(salary, startDate, endDate, contractType);

        input.nextLine();

        return switch(typeOfEmployee) {
            case 1 -> new Chef.ChefBuilder().setFirstName(firstName).setLastName(lastName).setContract(contract).build();
            case 2 -> new Waiter.WaiterBuilder().setFirstName(firstName).setLastName(lastName).setContract(contract).build();
            case 3 -> new Deliverer.DelivererBuilder().setFirstName(firstName).setLastName(lastName).setContract(contract).build();
            default -> throw new IllegalStateException("Unexpected value: " + typeOfContract);
        };
    }

    /**
     * Metoda za kreiranje novog restorana
     * @param input omogućuje korisniku unos traženih vrijednosti
     * @param meals lista jela
     * @param employees lista zaposlenika
     * @param restaurants lista restorana koja služi za provjeru da li posotji uneseni restoran
     * @return novi objekt Restaurant
     */
    public static Restaurant nextRestaurant(Scanner input, List<Meal> meals, List<Person> employees, List<Restaurant> restaurants) {

        String name, street;
        while(true) {
            try{
                System.out.print("Naziv: ");
                name = input.nextLine();
                System.out.print("Ulica: ");
                street = input.nextLine();

                InvalidInput.checkForDuplicateRestaurant(name, street, restaurants);
                break;
            }catch (DuplicateEntryException e){
                System.out.println("Pogreška, " + e.getMessage());
                log.error("POGREŠKA! Unesen restoran koji već postoji!");
            }
        }

        System.out.print("Kućni broj: ");
        String houseNumber = input.nextLine();
        System.out.print("Grad: ");
        String city = input.nextLine();
        System.out.print("Poštanski broj: ");
        String postalCode = input.nextLine();
        Address address = new Address(idBrojac++, street, houseNumber, city, postalCode);

        System.out.println("Jela");
        for(int i = 0; i < NUMBER_OF_MEALS; i++) {
            System.out.println((i + 1) + ". jelo: " + meals.get(i).getName());
        }
        Integer numberOfMeals = InvalidInput.validatePositiveInteger(input, "Koliko jela želite odabrati: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);

        List<Meal> tempMeals = new ArrayList<>();
        for(int i = 0; i < numberOfMeals; i++) {
            Integer mealIndex = InvalidInput.validatePositiveInteger(input, "Unesite jelo: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
            tempMeals.add(meals.get(mealIndex));
        }


        System.out.println("Kuhari");
        for(int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            if(employees.get(i) instanceof Chef chef) {
                System.out.println((i + 1) + ". kuhar: " + chef.getFirstName() + " " + chef.getLastName());
            }
        }
        Integer numberOfChefs = InvalidInput.validatePositiveInteger(input, "Koliko kuhara želite odabrati: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);

        List<Chef> tempChefs = new ArrayList<>();
        for(int i = 0; i < numberOfChefs; i++) {
            Integer chefIndex = InvalidInput.validatePositiveInteger(input, "Unesite kuhara: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
            tempChefs.add((Chef) employees.get(chefIndex));
        }

        System.out.println("Konobari");
        for(int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            if(employees.get(i) instanceof Waiter waiter) {
                System.out.println((i + 1) + ". konobar: " + waiter.getFirstName() + " " + waiter.getLastName());
            }
        }
        Integer numberOfWaiters = InvalidInput.validatePositiveInteger(input, "Koliko konobara želite odabrati: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);

        List<Waiter> tempWaiters = new ArrayList<>();
        for(int i = 0; i < numberOfWaiters; i++) {
            Integer waiterIndex = InvalidInput.validatePositiveInteger(input, "Unesite konobara: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
            tempWaiters.add((Waiter) employees.get(waiterIndex));
        }

        System.out.println("Dostavljači");
        for(int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            if(employees.get(i) instanceof Deliverer deliverer) {
                System.out.println((i + 1) + ". dostavljač: " + deliverer.getFirstName() + " " + deliverer.getLastName());
            }
        }
        Integer numberOfDeliverers = InvalidInput.validatePositiveInteger(input, "Koliko dostavljača želite odabrati: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);

        List<Deliverer> tempDeliverers = new ArrayList<>();
        for(int i = 0; i < numberOfDeliverers; i++) {
            Integer delivererIndex = InvalidInput.validatePositiveInteger(input, "Unesite konobara: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
            tempDeliverers.add((Deliverer) employees.get(delivererIndex));
        }
        input.nextLine();

        return new Restaurant(idBrojac++, name, address, tempMeals, tempChefs, tempWaiters, tempDeliverers);
    }

    /**
     * Metoda za kreiranje nove narudžbe
     * @param input omogućuje korisniku unos traženih vrijednosti
     * @param restaurants lista restorana
     * @return novi objekt Order
     */
    public static Order nextOrder(Scanner input, List<Restaurant> restaurants) {
        System.out.println("Restorani");
        for(int i = 0; i < NUMBER_OF_RESTAURANTS; i++) {
            System.out.println((i + 1) + ". restoran: " + restaurants.get(i).getName());
        }
        Integer restaurantIndex = InvalidInput.validatePositiveInteger(input, "Odaberite restoran: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
        Restaurant restaurant = restaurants.get(restaurantIndex);

        System.out.println("Jela");
        for(int i = 0; i < restaurant.getMeals().size(); i++) {
            System.out.println((i + 1) + ". jelo: " + restaurant.getMeals().get(i).getName());
        }
        Integer numberOfMeals = InvalidInput.validatePositiveInteger(input, "Koliko jela želite odabrati: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR);

        List<Meal> tempMeals = new ArrayList<>();
        for(int i = 0; i < numberOfMeals; i++) {
            Integer mealIndex = InvalidInput.validatePositiveInteger(input, "Unesite jelo: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
            tempMeals.add(restaurant.getMeals().get(mealIndex));
        }

        System.out.println("Dostavljači");
        for(int i = 0; i < restaurant.getDeliverers().size(); i++) {
            System.out.println((i + 1) + ". dostavljač: " + restaurant.getDeliverers().get(i).getFirstName() + " " + restaurant.getDeliverers().get(i).getLastName());
        }
        Integer delivererIndex = InvalidInput.validatePositiveInteger(input, "Odaberite dostavljača: ", Messages.INVALID_INT_INPUT_AND_NEGATIVE_ERROR) - 1;
        Deliverer deliverer = restaurant.getDeliverers().get(delivererIndex);
        deliverer.setDeliveryCount(deliverer.getDeliveryCount() + 1);

        LocalDateTime deliveryDateAndTime = LocalDateTime.now().plusMinutes(45);

        return new Order(idBrojac++, restaurant, tempMeals, deliverer, deliveryDateAndTime);
    }
}
