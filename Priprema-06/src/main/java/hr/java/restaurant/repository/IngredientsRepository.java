package hr.java.restaurant.repository;

import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Ingredient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IngredientsRepository {

    public static final String INGREDIENTS_FILE_PATH = "dat/ingrediants.txt";
    public static final Integer NUMBER_OF_ROWS_PER_INGREDIENT = 5;

    public Ingredient findById(Long id) {
        return findAll().stream().filter(temp -> id.equals(temp.getId())).findFirst().orElse(null);
    }

    public static List<Ingredient> findAll(){
        List<Ingredient> ingredients = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(INGREDIENTS_FILE_PATH))) {
            List<String> fileRows = br.lines().toList();

            for (int i = 0; i < fileRows.size() / NUMBER_OF_ROWS_PER_INGREDIENT; i++) {
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT));
                String name = fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT + 1);
                Category category = new CategoriesRepository().findById(Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT + 2)));
                BigDecimal kcal = new BigDecimal(fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT + 3));
                String preparationMethod = fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT + 4);
                Ingredient ingredient = new Ingredient(id, name, category, kcal, preparationMethod);
                ingredients.add(ingredient);
            }
        } catch (IOException e) {
            System.out.println("Pogreška pri radu s datotekom");
        }
        return ingredients;
    }
}
