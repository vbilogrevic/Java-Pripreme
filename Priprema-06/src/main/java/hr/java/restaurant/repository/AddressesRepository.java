package hr.java.restaurant.repository;

import hr.java.restaurant.model.Address;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AddressesRepository {

    public static final String ADDRESSES_FILE_PATH = "dat/addresses.txt";

    public static final Integer NUMBER_OF_ROWS_PER_ADDRESS = 5;

    public Address findById(Long id){
        return findAll().stream().filter(temp -> id.equals(temp.getId())).findFirst().orElse(null);
    }

    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Path.of(ADDRESSES_FILE_PATH))) {
            List<String> fileRows = stream.toList();

            for (int i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_ADDRESS); i++) {

                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESS));
                String street = fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESS + 1);
                String houseNumber = fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESS + 2);
                String city = fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESS + 3);
                String postalCode = fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESS + 4);

                Address tempAddress = new Address(id, street, houseNumber, city, postalCode);
                addresses.add(tempAddress);
            }
        } catch (IOException e) {
            throw new RuntimeException("Pogreška pri radu s datotekom", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pogreška u formatu podataka u datoteci adresa!", e);
        }

        return addresses;
    }


    public void save(List<Address> entities) {
        try(PrintWriter writer = new PrintWriter(ADDRESSES_FILE_PATH)) {
            for (Address entity : entities) {
                writer.println(entity.getId());
                writer.println(entity.getStreet());
                writer.println(entity.getHouseNumber());
                writer.println(entity.getCity());
                writer.println(entity.getPostalCode());
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
