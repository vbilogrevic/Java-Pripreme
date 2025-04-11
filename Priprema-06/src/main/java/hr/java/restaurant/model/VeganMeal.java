package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public final class VeganMeal extends Meal implements Vegan{

    private Integer calories;

    public VeganMeal(Long id, String name, Category category, Set<Ingredient> ingredients, BigDecimal price, Integer calories) {
        super(id, name, category, ingredients, price);
        this.calories = calories;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public boolean isLowCalorie() {
        return calories < 450;
    }
}
