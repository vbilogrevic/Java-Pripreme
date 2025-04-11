package hr.java.restaurant.repository;

import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Ingredient;
import hr.java.restaurant.model.Meal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MealsRepository {

    public static final String MEALS_FILE_PATH = "dat/meals.txt";
    public static final Integer NUMBER_OF_ROWS_PER_MEAL = 5;

    public Meal findById(Long id){
        return findAll().stream().filter(meal -> id.equals(meal.getId())).findFirst().orElse(null);
    }

    public static List<Meal> findAll(){
        List<Meal> meals = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(MEALS_FILE_PATH))) {
            List<String> fileRows = br.lines().toList();

            for (int i = 0; i < fileRows.size() / NUMBER_OF_ROWS_PER_MEAL; i++) {
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_MEAL));
                String name = fileRows.get(i * NUMBER_OF_ROWS_PER_MEAL + 1);
                Long categoryId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_MEAL + 2));
                Category category = new CategoriesRepository().findById(categoryId);

                String[] ingredientsIds = fileRows.get(i * NUMBER_OF_ROWS_PER_MEAL + 3).split(",");
                Set<Ingredient> ingredients = new HashSet<>();
                for (String ingredientId : ingredientsIds) {
                    Long ingredientIdLong = Long.parseLong(ingredientId);
                    Ingredient ingredient = new IngredientsRepository().findById(ingredientIdLong);
                    ingredients.add(ingredient);
                }

                BigDecimal kcal = new BigDecimal(fileRows.get(i * NUMBER_OF_ROWS_PER_MEAL + 4));

                Meal meal = new Meal(id, name, category, ingredients, kcal);
                meals.add(meal);
            }
        } catch (IOException e) {
            System.out.println("Pogreška pri radu s datotekom!");
        }
        return meals;
    }
}
