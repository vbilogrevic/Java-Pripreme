package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public final class MeatMeal extends Meal implements Meat{

    private String meatType;

    public MeatMeal(Long id, String name, Category category, Set<Ingredient> ingredients, BigDecimal price, String meatType) {
        super(id, name, category, ingredients, price);
        this.meatType = meatType;
    }

    public String getMeatType() {
        return meatType;
    }

    public void setMeatType(String meatType) {
        this.meatType = meatType;
    }

    @Override
    public String recommendedSideDish() {
        return "";
    }
}
