package hr.java.restaurant.repository;

import hr.java.restaurant.model.Deliverer;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Order;
import hr.java.restaurant.model.Restaurant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrdersRepository {

    public static final String ORDERS_FILE_PATH = "dat/orders.txt";

    public static final Integer NUMBER_OF_ROWS_PER_ORDER = 5;

    public static Order findById(Long id){
        return findAll().stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Path.of(ORDERS_FILE_PATH))) {
            List<String> fileRows = stream.collect(Collectors.toList());

            for (int i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_ORDER); i++) {
                int baseIndex = i * NUMBER_OF_ROWS_PER_ORDER;

                Long id = Long.parseLong(fileRows.get(baseIndex));

                Long restaurantId = Long.parseLong(fileRows.get(baseIndex + 1));
                Restaurant restaurant = new RestaurantsRepository().findById(restaurantId);

                String[] mealIds = fileRows.get(baseIndex + 2).split(",");
                List<Meal> meals = new ArrayList<>();
                for (String mealId : mealIds) {
                    Meal meal = new MealsRepository().findById(Long.parseLong(mealId.trim()));
                    meals.add(meal);
                }

                Long delivererId = Long.parseLong(fileRows.get(baseIndex + 3));
                Deliverer deliverer = new DeliverersRepository().findById(delivererId);

                LocalDateTime deliveryDateAndTime = LocalDateTime.parse(fileRows.get(baseIndex + 4), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

                Order tempOrder = new Order(id, restaurant, meals, deliverer, deliveryDateAndTime);
                orders.add( tempOrder);
            }
        } catch (IOException e) {
            throw new RuntimeException("Pogreška pri radu s datotekom", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pogreška u formatu podataka u datoteci narudžbi!", e);
        }

        return orders;
    }

}
