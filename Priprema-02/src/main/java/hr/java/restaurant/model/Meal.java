package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.List;

public class Meal extends Entity {

    private String name;
    private Category category;
    private List<Ingredient> ingredients;
    private BigDecimal price;

    public Meal(Long id, String name, Category category, List<Ingredient> ingredients, BigDecimal price) {
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
