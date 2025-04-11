package hr.java.restaurant.repository;

import hr.java.restaurant.model.Contract;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class ContractsRepository {

    public static final String CONTRACTS_FILE_PATH = "dat/contracts.txt";
    public static final Integer NUMBER_OF_ROWS_PER_CONTRACT = 5;

    public Contract findById(Long id){
        return findAll().stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public List<Contract> findAll() {
        List<Contract> contracts = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Path.of(CONTRACTS_FILE_PATH))) {
            List<String> fileRows = stream.collect(Collectors.toList());

            for (int i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_CONTRACT); i++) {
                int baseIndex = i * NUMBER_OF_ROWS_PER_CONTRACT;

                Long id = Long.parseLong(fileRows.get(baseIndex));
                BigDecimal salary = new BigDecimal(fileRows.get(baseIndex + 1));

                LocalDate startDate = LocalDate.parse(fileRows.get(baseIndex + 2), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalDate endDate = LocalDate.parse(fileRows.get(baseIndex + 3), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                Contract.ContractType contractType = Contract.ContractType.valueOf(fileRows.get(baseIndex + 4));

                Contract tempContract = new Contract(id, salary, startDate, endDate, contractType);
                contracts.add(tempContract);
            }
        } catch (IOException e) {
            throw new RuntimeException("Pogreška pri radu s datotekom", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pogreška u formatu podataka u datoteci ugovora!", e);
        }

        return contracts;
    }

}
