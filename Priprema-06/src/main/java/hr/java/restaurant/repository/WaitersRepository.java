package hr.java.restaurant.repository;

import hr.java.restaurant.model.Contract;
import hr.java.restaurant.model.Waiter;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WaitersRepository {

    public static final String WAITERS_FILE_PATH = "dat/waiters.txt";
    public static final Integer NUMBER_OF_ROWS_PER_WAITER = 5;

    public static Waiter findById(Long id){
        return findAll().stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static List<Waiter> findAll() {
        List<Waiter> waiters = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Path.of(WAITERS_FILE_PATH))) {
            List<String> fileRows = stream.collect(Collectors.toList());

            for (int i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_WAITER); i++) {
                int baseIndex = i * NUMBER_OF_ROWS_PER_WAITER;

                Long id = Long.parseLong(fileRows.get(baseIndex));
                String firstName = fileRows.get(baseIndex + 1);
                String lastName = fileRows.get(baseIndex + 2);

                Long contractId = Long.parseLong(fileRows.get(baseIndex + 3));
                Contract contract = new ContractsRepository().findById(contractId);


                Waiter tempWaiter = new Waiter(firstName, lastName, contract);
                waiters.add(tempWaiter);
            }
        } catch (IOException e) {
            throw new RuntimeException("Pogreška pri radu s datotekom", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pogreška u formatu podataka u datoteci konobara!", e);
        }

        return waiters;
    }

}
