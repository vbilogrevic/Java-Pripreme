package hr.java.production.main;

import hr.java.restaurant.model.*;
import hr.java.restaurant.repository.*;
import hr.java.restaurant.sort.ContractDurationComparator;
import hr.java.restaurant.sort.IngredientAlphabeticalComparator;
import hr.java.restaurant.sort.SalaryComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Klasa u kojoj se izvršava program
 */
public class Main {

    public static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Aplikacija je pokrenuta");

        Scanner input = new Scanner(System.in);

        List<Category> categories = CategoriesRepository.findAll();
        List<Ingredient> ingredients = IngredientsRepository.findAll();
        List<Meal> meals = MealsRepository.findAll();
        List<Restaurant> restaurants = RestaurantsRepository.findAll();
        List<Order> orders = OrdersRepository.findAll();

        ChefsRepository chefsRepository = new ChefsRepository();
        List<Chef> chefs = chefsRepository.findAll();
        DeliverersRepository deliverersRepository = new DeliverersRepository();
        List<Deliverer> deliverers = deliverersRepository.findAll();
        WaitersRepository waitersRepository = new WaitersRepository();
        List<Waiter> waiters = waitersRepository.findAll();

        List<Person> employees = new ArrayList<>();
        employees.addAll(chefs);
        employees.addAll(deliverers);
        employees.addAll(waiters);


        Map<Meal, List<Restaurant>> restaurantListMap = new HashMap<>();


    /*
        for(int i = 0; i < DataInput.NUMBER_OF_INGREDIANTS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". namirnicu");
            Ingredient ingredient = DataInput.nextIngredient(input, categories);
            ingredients.add(ingredient);
            log.info("Kreirana nova namirnica: " + ingredient.getName());
        }

        for(int i = 0; i < DataInput.NUMBER_OF_MEALS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". jelo");
            Meal meal = DataInput.nextMeal(input, categories, ingredients, meals);
            meals.add(meal);
            log.info("Kreirano novo jelo: " + meal.getName());
        }

        for(int i = 0; i < DataInput.NUMBER_OF_EMPLOYEES; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". zaposlenika");
            Person person = DataInput.nextPerson(input, employees);
            employees.add(person);
            log.info("Kreiran novi zaposlenik: " + person.getFirstName() + " " + person.getLastName());
        }

        for(int i = 0; i < DataInput.NUMBER_OF_RESTAURANTS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". restoran");
            Restaurant restaurant = DataInput.nextRestaurant(input, meals, employees, restaurants);
            restaurants.add(restaurant);
            log.info("Kreiran novi restoran: " + restaurant.getName());
        }

        for(Restaurant restaurant : restaurants) {
            for(Meal meal : restaurant.getMeals()) {
                List<Restaurant> restaurantList = new ArrayList<>();
                restaurantList.add(restaurant);
                restaurantListMap.put(meal, restaurantList);
            }
        }

        for(int i = 0; i < DataInput.NUMBER_OF_ORDERS; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". narudžbu");
            Order order = DataInput.nextOrder(input, restaurants);
            orders.add(order);
            log.info("Kreirana nova narudžba iz restorana: " + order.getRestaurant().getName());
        }*/

        findMostExpensiveOrder(orders);
        //findTopDeliverer(orders);
        findHighestSalary(employees);
        findLongestContract(employees);
        findHighestAndLowestCalorieMeal(meals);

        employees.sort(new SalaryComparator());
        employees.sort(new ContractDurationComparator());
        ingredients.sort(new IngredientAlphabeticalComparator());
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

    private static void findHighestSalary(List<Person> employees) {
        Person employeeWithHighestSalary = null;
        BigDecimal highestSalary = BigDecimal.ZERO;

        for(Person employee : employees) {
            BigDecimal salary = BigDecimal.ZERO;

            if(employee instanceof Chef chef) {
                salary = chef.getContract().getSalary();
            }else if(employee instanceof Waiter waiter) {
                salary = waiter.getContract().getSalary();
            }else if(employee instanceof Deliverer deliverer) {
                salary = deliverer.getContract().getSalary();
            }

            if(salary.compareTo(highestSalary) > 0) {
                highestSalary = salary;
                employeeWithHighestSalary = employee;
            }
        }

        System.out.println("Zaposlenik s največom plaćom je " + employeeWithHighestSalary.getFirstName() + " " + employeeWithHighestSalary.getLastName());
    }

    private static void findLongestContract(List<Person> employees) {
        Person employeeWithLongestContract = employees.get(0);
        long daysBetween = 0;

        for(Person employee : employees) {
            long contract = 0;
            if(employee instanceof Chef chef) {
                contract = ChronoUnit.DAYS.between(chef.getContract().getStartDate(), chef.getContract().getEndDate());
            }else if(employee instanceof Waiter waiter) {
                contract = ChronoUnit.DAYS.between(waiter.getContract().getStartDate(), waiter.getContract().getEndDate());
            }else if(employee instanceof Deliverer deliverer) {
                contract = ChronoUnit.DAYS.between(deliverer.getContract().getStartDate(), deliverer.getContract().getEndDate());
            }

            if(contract > daysBetween) {
                daysBetween = contract;
                employeeWithLongestContract = employee;
            }
        }

        System.out.println("Zaposlenik s najdužim ugovor je " + employeeWithLongestContract.getFirstName() + " " + employeeWithLongestContract.getLastName());
    }

    private static void findHighestAndLowestCalorieMeal(List<Meal> meals) {
        Meal highCalorieMeal = meals.get(0);
        Meal lowCalorieMeal = meals.get(0);

        BigDecimal minKcal = BigDecimal.ZERO;
        BigDecimal maxKcal = BigDecimal.ZERO;

        for(Meal meal : meals) {
            BigDecimal mealKcal = meal.getTotalKcal();

            if(mealKcal.compareTo(maxKcal) > 0) {
                maxKcal = mealKcal;
                highCalorieMeal = meal;
            }

            if(mealKcal.compareTo(minKcal) < 0) {
                minKcal = mealKcal;
                lowCalorieMeal = meal;
            }
        }

        System.out.println("Jelo s najmanje kalorija je " + lowCalorieMeal.getName());
        System.out.println("Jelo s najviše kalorija je " + highCalorieMeal.getName());
    }
}