package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public final class VegetarianMeal extends Meal implements Vegetarian{

    private Boolean containsNuts;

    public VegetarianMeal(Long id, String name, Category category, Set<Ingredient> ingredients, BigDecimal price, Boolean containsNuts) {
        super(id, name, category, ingredients, price);
        this.containsNuts = containsNuts;
    }

    public Boolean getContainsNuts() {
        return containsNuts;
    }

    public void setContainsNuts(Boolean containsNuts) {
        this.containsNuts = containsNuts;
    }

    @Override
    public String recommendedDrink() {
        return "Uz ovo jelo preporučujemo bijelo vino.";
    }
}
