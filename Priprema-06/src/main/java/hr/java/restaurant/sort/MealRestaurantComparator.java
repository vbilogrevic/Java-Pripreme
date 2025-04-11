package hr.java.restaurant.sort;

import hr.java.restaurant.model.Restaurant;

import java.util.Comparator;

public class MealRestaurantComparator implements Comparator<Restaurant> {


    @Override
    public int compare(Restaurant r1, Restaurant r2) {
        Integer restoran1 = getMealNumber(r1);
        Integer restoran2 = getMealNumber(r2);

        return restoran1.compareTo(restoran2);
    }

    private Integer getMealNumber(Restaurant restaurant) {
        Integer broj = restaurant.getMeals().size();
        return broj;
    }
}
