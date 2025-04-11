package hr.java.restaurant.repository;

import hr.java.restaurant.model.Category;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


public class CategoriesRepository {

    public static final String CATEGORIES_FILE_PATH = "dat/categories.txt";
    public static final Integer NUMBER_OF_ROWS_PER_CATEGORY = 3;

    public Category findById(Long id) {
        return findAll().stream().filter(temp -> id.equals(temp.getId())).findFirst().orElse(null);
    }

    public static List<Category> findAll(){
        List<Category> categories = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(CATEGORIES_FILE_PATH))) {
            List<String> fileRows = br.lines().toList();

            for (int i = 0; i < fileRows.size() / NUMBER_OF_ROWS_PER_CATEGORY; i++) {
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_CATEGORY));
                String name = fileRows.get(i * NUMBER_OF_ROWS_PER_CATEGORY + 1);
                String description = fileRows.get(i * NUMBER_OF_ROWS_PER_CATEGORY + 2);
                Category category = new Category(id, name, description);
                categories.add(category);
            }
        } catch (IOException e) {
            System.out.println("Pogreška pri radu s datotekom!");
        }
        return categories;
    }

}
