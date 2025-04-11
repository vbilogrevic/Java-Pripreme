package hr.java.restaurant.repository;

import hr.java.restaurant.model.Chef;
import hr.java.restaurant.model.Contract;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChefsRepository {

    public static final String CHEFS_FILE_PATH = "dat/chefs.txt";

    public static final Integer NUMBER_OF_ROWS_PER_CHEF = 5;

    public Chef findById(Long id){
        return findAll().stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public List<Chef> findAll() {
        List<Chef> chefs = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Path.of(CHEFS_FILE_PATH))) {
            List<String> fileRows = stream.collect(Collectors.toList());

            for (int i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_CHEF); i++) {

                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_CHEF));
                String firstName = fileRows.get(i * NUMBER_OF_ROWS_PER_CHEF + 1);
                String lastName = fileRows.get(i * NUMBER_OF_ROWS_PER_CHEF + 2);

                Long contractId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_CHEF + 3));
                Contract contract = new ContractsRepository().findById(contractId);

                Chef tempChef = new Chef(firstName, lastName, contract);
                chefs.add(tempChef);
            }
        } catch (IOException e) {
            throw new RuntimeException("Pogreška pri radu s datotekom", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pogreška u formatu podataka u datoteci kuhara!", e);
        }

        return chefs;
    }

}
