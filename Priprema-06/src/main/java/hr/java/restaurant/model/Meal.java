package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Meal extends Entity {

    private String name;
    private Category category;
    private Set<Ingredient> ingredients;
    private BigDecimal price;

    public Meal(Long id, String name, Category category, Set<Ingredient> ingredients, BigDecimal price) {
        super(id);
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.price = price;
    }

    public BigDecimal getTotalKcal() {
        BigDecimal totalKcal = BigDecimal.ZERO;
        for (Ingredient ingredient : ingredients) {
            totalKcal = totalKcal.add(ingredient.getKcal());
        }
        return totalKcal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(name, meal.name) && Objects.equals(category, meal.category) && Objects.equals(ingredients, meal.ingredients) && Objects.equals(price, meal.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, ingredients, price);
    }
}
