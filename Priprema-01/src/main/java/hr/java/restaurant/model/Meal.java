package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.List;

public class Meal {

    private String name;
    private Category category;
    private List<Ingredient> ingredients;
    private BigDecimal price;

    public Meal(String name, Category category, List<Ingredient> ingredients, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.price = price;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
