package hr.java.restaurant.repository;

import hr.java.restaurant.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestaurantsRepository {

    public static final String RESTAURANTS_FILE_PATH = "dat/restaurants.txt";

    public static final Integer NUMBER_OF_ROWS_PER_RESTAURANT = 7;

    public Restaurant findById(Long id){
        return findAll().stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static List<Restaurant> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Path.of(RESTAURANTS_FILE_PATH))) {
            List<String> fileRows = stream.collect(Collectors.toList());

            for (int i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_RESTAURANT); i++) {
                int baseIndex = i * NUMBER_OF_ROWS_PER_RESTAURANT;

                Long id = Long.parseLong(fileRows.get(baseIndex));
                String name = fileRows.get(baseIndex + 1);

                Long addressId = Long.parseLong(fileRows.get(baseIndex + 2));
                Address address = new AddressesRepository().findById(addressId);

                String[] mealIds = fileRows.get(baseIndex + 3).split(",");
                Set<Meal> meals = new HashSet<>();
                for (String mealId : mealIds) {
                    Long mealIdLong = Long.parseLong(mealId);
                    Meal meal = new MealsRepository().findById(mealIdLong);
                    meals.add(meal);
                }

                String[] chefIds = fileRows.get(baseIndex + 4).split(",");
                Set<Chef> chefs = new HashSet<>();
                for (String chefId : chefIds) {
                    Long chefIdLong = Long.parseLong(chefId);
                    Chef chef = new ChefsRepository().findById(chefIdLong);
                    chefs.add(chef);
                }

                String[] waiterIds = fileRows.get(baseIndex + 5).split(",");
                Set<Waiter> waiters = new HashSet<>();
                for (String waiterId : waiterIds) {
                    Long waiterIdLong = Long.parseLong(waiterId);
                    Waiter waiter = new WaitersRepository().findById(waiterIdLong);
                    waiters.add(waiter);
                }

                String[] delivererIds = fileRows.get(baseIndex + 6).split(",");
                Set<Deliverer> deliverers = new HashSet<>();
                for (String delivererId : delivererIds) {
                    Long delivererIdLong = Long.parseLong(delivererId);
                    Deliverer deliverer = new DeliverersRepository().findById(delivererIdLong);
                    deliverers.add(deliverer);
                }

                Restaurant tempRestaurant = new Restaurant(id, name, address, meals, chefs, waiters, deliverers);
                restaurants.add(tempRestaurant);
            }
        } catch (IOException e) {
            throw new RuntimeException("Pogreška pri radu s datotekom", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pogreška u formatu podataka u datoteci restorana!", e);
        }

        return restaurants;
    }

}
