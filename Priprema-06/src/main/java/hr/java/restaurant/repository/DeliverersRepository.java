package hr.java.restaurant.repository;

import hr.java.restaurant.model.Contract;
import hr.java.restaurant.model.Deliverer;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DeliverersRepository {

    public static final String DELIVERERS_FILE_PATH = "dat/deliverers.txt";
    public static final Integer NUMBER_OF_ROWS_PER_DELIVERER = 5;

    public static Deliverer findById(Long id){
        return findAll().stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static List<Deliverer> findAll() {
        List<Deliverer> deliverers = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Path.of(DELIVERERS_FILE_PATH))) {
            List<String> fileRows = stream.toList();

            for (int i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_DELIVERER); i++) {
                int baseIndex = i * NUMBER_OF_ROWS_PER_DELIVERER;

                Long id = Long.parseLong(fileRows.get(baseIndex));
                String firstName = fileRows.get(baseIndex + 1);
                String lastName = fileRows.get(baseIndex + 2);

                Long contractId = Long.parseLong(fileRows.get(baseIndex + 3));
                Contract contract = new ContractsRepository().findById(contractId);

                Deliverer tempDeliverer = new Deliverer(firstName, lastName, contract);
                deliverers.add(tempDeliverer);
            }
        } catch (IOException e) {
            throw new RuntimeException("Pogreška pri radu s datotekom", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pogreška u formatu podataka u datoteci dostavljača!", e);
        }

        return deliverers;
    }

}
