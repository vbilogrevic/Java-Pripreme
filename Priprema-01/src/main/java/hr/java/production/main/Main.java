package hr.java.production.main;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final Integer NUMBER_OF_CATEGORIES = 3;
    public static final Integer NUMBER_OF_INGREDIENTS = 5;
    public static final Integer NUMBER_OF_MEALS = 3;
    public static final Integer NUMBER_OF_CHEFS = 3;
    public static final Integer NUMBER_OF_WAITERS = 3;
    public static final Integer NUMBER_OF_DELIVERERS = 3;
    public static final Integer NUMBER_OF_RESTAURANTS = 3;
    public static final Integer NUMBER_OF_ORDERS = 3;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        List<Category> categories = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        List<Meal> meals = new ArrayList<>();
        List<Chef> chefs = new ArrayList<>();
        List<Waiter> waiters = new ArrayList<>();
        List<Deliverer> deliverers = new ArrayList<>();
        List<Restaurant> restaurants = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        for(int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". kategoriju");
            Category category = nextCategory(input);
            categories.add(category);
        }

        for(int i = 0; i < NUMBER_OF_INGREDIENTS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". namirnicu");
            Ingredient ingredient = nextIngredient(input, categories);
            ingredients.add(ingredient);
        }

        for(int i = 0; i < NUMBER_OF_MEALS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". jelo");
            Meal meal = nextMeal(input, categories, ingredients);
            meals.add(meal);
        }

        for(int i = 0; i < NUMBER_OF_CHEFS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". kuhara");
            Chef chef = nextChef(input);
            chefs.add(chef);
        }

        for(int i = 0; i < NUMBER_OF_WAITERS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". konobara");
            Waiter waiter = nextWaiter(input);
            waiters.add(waiter);
        }

        for(int i = 0; i < NUMBER_OF_DELIVERERS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". dostavljača");
            Deliverer deliverer = nextDeliverer(input);
            deliverers.add(deliverer);
        }

        for(int i = 0; i < NUMBER_OF_RESTAURANTS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". restoran");
            Restaurant restaurant = nextRestaurant(input, meals, chefs, waiters, deliverers);
            restaurants.add(restaurant);
        }

        for(int i = 0; i < NUMBER_OF_ORDERS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". narudžbu");
            Order order = nextOrder(input, restaurants);
            orders.add(order);
        }

        findMostExpensiveOrder(orders);
        findTopDeliverer(orders);
    }

    private static Category nextCategory(Scanner input) {
        System.out.print("Naziv: ");
        String name = input.nextLine();
        System.out.print("Opis: ");
        String description = input.nextLine();

        return new Category(name, description);
    }

    private static Ingredient nextIngredient(Scanner input, List<Category> categories) {
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

        return new Ingredient(name, category, kcal, preparationMethod);
    }

    private static Meal nextMeal(Scanner input, List<Category> categories, List<Ingredient> ingredients) {
        System.out.print("Naziv: ");
        String name = input.nextLine();

        System.out.println("Kategorije");
        for(int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println((i + 1) + ". kategorija: " + categories.get(i).getName());
        }
        System.out.print("Odaberite kategorju: ");
        Integer categoryIndex = input.nextInt() - 1;
        Category category = categories.get(categoryIndex);

        System.out.print("Sastojci");
        for(int i = 0; i < NUMBER_OF_INGREDIENTS; i++) {
            System.out.println((i + 1) + ". sastojak: " + ingredients.get(i).getName());
        }
        System.out.print("Koliko sastojaka želite odabrati: ");
        Integer numberOfIngredients = input.nextInt();

        List<Ingredient> tempIngredients = new ArrayList<>();
        for(int i = 0; i < numberOfIngredients; i++) {
            System.out.print("Unesite sastojak: ");
            Integer ingredientIndex = input.nextInt() - 1;
            tempIngredients.add(ingredients.get(ingredientIndex));
        }

        System.out.print("Cijena: ");
        BigDecimal price = input.nextBigDecimal();
        input.nextLine();

        return new Meal(name, category, tempIngredients, price);
    }

    private static Chef nextChef(Scanner input) {
        System.out.print("Ime: ");
        String firstName = input.nextLine();
        System.out.print("Prezime: ");
        String lastName = input.nextLine();
        System.out.print("Plaća: ");
        BigDecimal salary = input.nextBigDecimal();
        input.nextLine();

        return new Chef(firstName, lastName, salary);
    }

    private static Waiter nextWaiter(Scanner input) {
        System.out.print("Ime: ");
        String firstName = input.nextLine();
        System.out.print("Prezime: ");
        String lastName = input.nextLine();
        System.out.print("Plaća: ");
        BigDecimal salary = input.nextBigDecimal();
        input.nextLine();

        return new Waiter(firstName, lastName, salary);
    }

    private static Deliverer nextDeliverer(Scanner input) {
        System.out.print("Ime: ");
        String firstName = input.nextLine();
        System.out.print("Prezime: ");
        String lastName = input.nextLine();
        System.out.print("Plaća: ");
        BigDecimal salary = input.nextBigDecimal();
        input.nextLine();

        return new Deliverer(firstName, lastName, salary);
    }

    private static Restaurant nextRestaurant(Scanner input, List<Meal> meals, List<Chef> chefs, List<Waiter> waiters, List<Deliverer> deliverers) {
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
        Address address = new Address(street, houseNumber, city, postalCode);

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
        for(int i = 0; i < NUMBER_OF_CHEFS; i++) {
            System.out.println((i + 1) + ". kuhar: " + chefs.get(i).getFirstName() + " " + chefs.get(i).getLastName());
        }
        System.out.println("Koliko kuhara želite odabrati: ");
        Integer numberOfChefs = input.nextInt();

        List<Chef> tempChefs = new ArrayList<>();
        for(int i = 0; i < numberOfChefs; i++) {
            System.out.println("Unesite kuhara: ");
            Integer chefIndex = input.nextInt() - 1;
            tempChefs.add(chefs.get(chefIndex));
        }

        System.out.println("Konobari");
        for(int i = 0; i < NUMBER_OF_WAITERS; i++) {
            System.out.println((i + 1) + ". konobar: " + waiters.get(i).getFirstName() + " " + waiters.get(i).getLastName());
        }
        System.out.println("Koliko konobara želite odabrati: ");
        Integer numberOfWaiters = input.nextInt();

        List<Waiter> tempWaiters = new ArrayList<>();
        for(int i = 0; i < numberOfWaiters; i++) {
            System.out.println("Unesite konobara: ");
            Integer waiterIndex = input.nextInt() - 1;
            tempWaiters.add(waiters.get(waiterIndex));
        }

        System.out.println("Dostavljači");
        for(int i = 0; i < NUMBER_OF_DELIVERERS; i++) {
            System.out.println((i + 1) + ". dostavljač: " + deliverers.get(i).getFirstName() + " " + deliverers.get(i).getLastName());
        }
        System.out.println("Koliko konobara želite odabrati: ");
        Integer numberOfDeliverers = input.nextInt();

        List<Deliverer> tempDeliverers = new ArrayList<>();
        for(int i = 0; i < numberOfDeliverers; i++) {
            System.out.println("Unesite konobara: ");
            Integer delivererIndex = input.nextInt() - 1;
            tempDeliverers.add(deliverers.get(delivererIndex));
        }
        input.nextLine();

        return new Restaurant(name, address, tempMeals, tempChefs, tempWaiters, tempDeliverers);
    }

    private static Order nextOrder(Scanner input, List<Restaurant> restaurants) {
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

        return new Order(restaurant, tempMeals, deliverer, deliveryDateAndTime);
    }

    private static void findMostExpensiveOrder(List<Order> orders){
        List<Restaurant> restaurants = new ArrayList<>();
        BigDecimal maxPrice = BigDecimal.ZERO;

        for(Order order : orders) {
            if(order.getTotalPrice().compareTo(maxPrice) > 0) {
                maxPrice = order.getTotalPrice();
                restaurants.clear();
                restaurants.add(order.getRestaurant());
            }else if(order.getTotalPrice().compareTo(maxPrice) == 0) {
                if(!restaurants.contains(order.getRestaurant())) {
                    restaurants.add(order.getRestaurant());
                }
            }
        }

        System.out.println("Restoran/i s najskupljom narudžbom su: ");
        for(Restaurant restaurant : restaurants) {
            System.out.println(restaurant.getName());
        }
    }

    private static void findTopDeliverer(List<Order> orders) {
        List<Deliverer> deliverers = new ArrayList<>();
        Integer topDeliveries = 0;

        for(Order order : orders) {
            if(order.getDeliverer().getDeliveryCount().compareTo(topDeliveries) > 0) {
                topDeliveries = order.getDeliverer().getDeliveryCount();
                deliverers.clear();
                deliverers.add(order.getDeliverer());
            }else if(order.getDeliverer().getDeliveryCount().compareTo(topDeliveries) == 0) {
                if(!deliverers.contains(order.getDeliverer())) {
                    deliverers.add(order.getDeliverer());
                }
            }
        }

        System.out.println("Dostavljač/i s najviše dostava su: ");
        for(Deliverer deliverer : deliverers){
            System.out.println(deliverer.getFirstName() + " " + deliverer.getLastName());
        }
    }
}