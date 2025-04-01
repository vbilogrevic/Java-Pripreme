package hr.java.restaurant.util;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataInput {

    public static final Integer NUMBER_OF_CATEGORIES = 3;
    public static final Integer NUMBER_OF_INGREDIENTS = 5;
    public static final Integer NUMBER_OF_MEALS = 3;
    public static final Integer NUMBER_OF_EMPLOYEES = 5;
    public static final Integer NUMBER_OF_RESTAURANTS = 3;
    public static final Integer NUMBER_OF_ORDERS = 3;

    public static long idBrojac = 0;

    public static Category nextCategory(Scanner input) {
        System.out.print("Naziv: ");
        String name = input.nextLine();
        System.out.print("Opis: ");
        String description = input.nextLine();

        return new Category(idBrojac++, name, description);
    }

    public static Ingredient nextIngredient(Scanner input, List<Category> categories) {
        System.out.print("Naziv: ");
        String name = input.nextLine();

        System.out.println("Kategorije");
        for(int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println((i + 1) + ". kategorija: " + categories.get(i).getName());
        }
        System.out.print("Odaberite kategoriju: ");
        Integer categoryIndex = input.nextInt() - 1;
        Category category = categories.get(categoryIndex);

        System.out.print("Kalorije: ");
        BigDecimal kcal = input.nextBigDecimal();
        input.nextLine();

        System.out.print("Način pripreme: ");
        String preparationMethod = input.nextLine();

        return new Ingredient(idBrojac++, name, category, kcal, preparationMethod);
    }

    public static Meal nextMeal(Scanner input, List<Category> categories, List<Ingredient> ingredients) {

        Integer typeOfMeal;
        do{
            System.out.print("Odaberite\n1. Vegan\n2. Vegetarian\n3. Meat\nOdaberite: ");
            typeOfMeal = input.nextInt();
            if(typeOfMeal < 1 || typeOfMeal > 3){
                System.out.println("Pogrešan unos, ponovite!");
            }
        }while(typeOfMeal < 1 || typeOfMeal > 3);
        input.nextLine();

        System.out.print("Naziv: ");
        String name = input.nextLine();

        System.out.println("Kategorije");
        for(int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println((i + 1) + ". kategorija: " + categories.get(i).getName());
        }
        System.out.print("Odaberite kategorju: ");
        Integer categoryIndex = input.nextInt() - 1;
        Category category = categories.get(categoryIndex);

        System.out.println("Sastojci");
        for(int i = 0; i < NUMBER_OF_INGREDIENTS; i++) {
            System.out.println((i + 1) + ". sastojak: " + ingredients.get(i).getName());
        }
        System.out.print("Koliko sastojaka želite odabrati: ");
        Integer numberOfIngredients = input.nextInt();

        List<Ingredient> tempIngredients = new ArrayList<>();
        for(int i = 0; i < numberOfIngredients; i++) {
            System.out.print("Unesite satojak: ");
            Integer ingredientIndex = input.nextInt() - 1;
            tempIngredients.add(ingredients.get(ingredientIndex));
        }

        System.out.print("Cijena: ");
        BigDecimal price = input.nextBigDecimal();
        input.nextLine();

        return switch(typeOfMeal){
          case 1 -> {
              System.out.print("Kalorije: ");
              Integer kcal = input.nextInt();
              input.nextLine();
              yield new VeganMeal(idBrojac++, name, category, tempIngredients, price, kcal); }
          case 2 -> {
              System.out.print("Sadrži li jelo orašaste plodove (da / ne): ");
              String nutsString = input.nextLine();
              Boolean containsNuts = false;
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

    public static Person nextPerson(Scanner input) {
        Integer typeOfEmployee;
        do{
            System.out.print("Odaberite vrstu zaposlenika\n1. Kuhar\n2. Konobar\n3. Dostavljač\nOdaberite: ");
            typeOfEmployee = input.nextInt();
            if(typeOfEmployee < 1 || typeOfEmployee > 3){
                System.out.println("Pogrešan unos, ponovite!");
            }
        }while(typeOfEmployee < 1 || typeOfEmployee > 3);
        input.nextLine();

        System.out.print("Ime: ");
        String firstName = input.nextLine();
        System.out.print("Prezime: ");
        String lastName = input.nextLine();

        System.out.print("Plaća: ");
        BigDecimal salary = input.nextBigDecimal();
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
            System.out.println("Vrsta ugovora\n1. Full time\n2. Part time\nOdaberite: ");
            typeOfContract = input.nextInt();
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

    public static Restaurant nextRestaurant(Scanner input, List<Meal> meals, List<Person> employees) {
        System.out.print("Naziv: ");
        String name = input.nextLine();

        System.out.print("Ulica: ");
        String street = input.nextLine();
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
        System.out.print("Kolika jela želite odabrati: ");
        Integer numberOfMeals = input.nextInt();

        List<Meal> tempMeals = new ArrayList<>();
        for(int i = 0; i < numberOfMeals; i++) {
            System.out.print("Unesite jelo: ");
            Integer mealIndex = input.nextInt() - 1;
            tempMeals.add(meals.get(mealIndex));
        }


        System.out.println("Kuhari");
        for(int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            if(employees.get(i) instanceof Chef chef) {
                System.out.println((i + 1) + ". kuhar: " + chef.getFirstName() + " " + chef.getLastName());
            }
        }
        System.out.println("Koliko kuhara želite odabrati: ");
        Integer numberOfChefs = input.nextInt();

        List<Chef> tempChefs = new ArrayList<>();
        for(int i = 0; i < numberOfChefs; i++) {
            System.out.println("Unesite kuhara: ");
            Integer chefIndex = input.nextInt() - 1;
            tempChefs.add((Chef) employees.get(chefIndex));
        }

        System.out.println("Konobari");
        for(int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            if(employees.get(i) instanceof Waiter waiter) {
                System.out.println((i + 1) + ". konobar: " + waiter.getFirstName() + " " + waiter.getLastName());
            }
        }
        System.out.println("Koliko konobara želite odabrati: ");
        Integer numberOfWaiters = input.nextInt();

        List<Waiter> tempWaiters = new ArrayList<>();
        for(int i = 0; i < numberOfWaiters; i++) {
            System.out.println("Unesite konobara: ");
            Integer waiterIndex = input.nextInt() - 1;
            tempWaiters.add((Waiter) employees.get(waiterIndex));
        }

        System.out.println("Dostavljači");
        for(int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            if(employees.get(i) instanceof Deliverer deliverer) {
                System.out.println((i + 1) + ". dostavljač: " + deliverer.getFirstName() + " " + deliverer.getLastName());
            }
        }
        System.out.println("Koliko dostavljača želite odabrati: ");
        Integer numberOfDeliverers = input.nextInt();

        List<Deliverer> tempDeliverers = new ArrayList<>();
        for(int i = 0; i < numberOfDeliverers; i++) {
            System.out.println("Unesite konobara: ");
            Integer delivererIndex = input.nextInt() - 1;
            tempDeliverers.add((Deliverer) employees.get(delivererIndex));
        }
        input.nextLine();

        return new Restaurant(idBrojac++, name, address, tempMeals, tempChefs, tempWaiters, tempDeliverers);
    }

    public static Order nextOrder(Scanner input, List<Restaurant> restaurants) {
        System.out.println("Restorani");
        for(int i = 0; i < NUMBER_OF_RESTAURANTS; i++) {
            System.out.println((i + 1) + ". restoran: " + restaurants.get(i).getName());
        }
        System.out.print("Odaberite restoran: ");
        Integer restaurantIndex = input.nextInt() - 1;
        Restaurant restaurant = restaurants.get(restaurantIndex);

        System.out.println("Jela");
        for(int i = 0; i < restaurant.getMeals().size(); i++) {
            System.out.println((i + 1) + ". jelo: " + restaurant.getMeals().get(i).getName());
        }
        System.out.print("Koliko jela želite odabrati: ");
        Integer numberOfMeals = input.nextInt();

        List<Meal> tempMeals = new ArrayList<>();
        for(int i = 0; i < numberOfMeals; i++) {
            System.out.println("Unesite jelo: ");
            Integer mealIndex = input.nextInt() - 1;
            tempMeals.add(restaurant.getMeals().get(mealIndex));
        }

        System.out.println("Dostavljači");
        for(int i = 0; i < restaurant.getDeliverers().size(); i++) {
            System.out.println((i + 1) + ". dostavljač: " + restaurant.getDeliverers().get(i).getFirstName() + " " + restaurant.getDeliverers().get(i).getLastName());
        }
        System.out.print("Odaberite dostavljača: ");
        Integer delivererIndex = input.nextInt() - 1;
        Deliverer deliverer = restaurant.getDeliverers().get(delivererIndex);
        deliverer.setDeliveryCount(deliverer.getDeliveryCount() + 1);

        LocalDateTime deliveryDateAndTime = LocalDateTime.now().plusMinutes(45);

        return new Order(idBrojac++, restaurant, tempMeals, deliverer, deliveryDateAndTime);
    }
}
