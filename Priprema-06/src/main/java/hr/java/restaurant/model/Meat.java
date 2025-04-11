package hr.java.restaurant.model;

public sealed interface Meat permits MeatMeal {

    String recommendedSideDish();
}
