package hr.java.restaurant.sort;

import hr.java.restaurant.model.Ingredient;

import java.util.Comparator;

/**
 * Klasa koja sortira namirnice abecedno
 */
public class IngredientAlphabeticalComparator implements Comparator<Ingredient> {
    @Override
    public int compare(Ingredient i1, Ingredient i2) {
        return i1.getName().compareToIgnoreCase(i2.getName());
    }
}
