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
                int baseIndex = i * NUMBER_OF_ROWS_PER_CHEF;

                Long id = Long.parseLong(fileRows.get(baseIndex)); // ID
                String firstName = fileRows.get(baseIndex + 1); // Ime kuhara
                String lastName = fileRows.get(baseIndex + 2); // Prezime kuhara

                // Dohvati ugovor prema ID-u
                Long contractId = Long.parseLong(fileRows.get(baseIndex + 3)); // ID ugovora
                Contract contract = new ContractsRepository().findById(contractId);

                // Bonus kuhara
                BigDecimal bonusAmount = new BigDecimal(fileRows.get(baseIndex + 4));

                // Kreiraj objekt Chef
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
