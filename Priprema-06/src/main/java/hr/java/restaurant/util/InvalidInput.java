package hr.java.restaurant.util;

import hr.java.restaurant.exception.DuplicateEntryException;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Person;
import hr.java.restaurant.model.Restaurant;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Klasa koja služi za provjeru unesenih vrijednsoti
 */
public class InvalidInput {

    /**
     * Metoda koja provjerava da li je unesena vrijednost numerička i pozitivna
     * @param input omogućuje korisniku unos tražene vrijednosti
     * @param message poruka koja govori što se traži od korisnika
     * @param errorMessage - poruka pogreške
     * @return unesena vrijednost
     */
    public static Integer validatePositiveInteger(Scanner input, String message, String errorMessage) {
        Integer number = 0;
        Boolean isValid = false;

        do{
            isValid = true;
            try{
                System.out.print(message);
                number = input.nextInt();

                if(number < 0) {
                    System.out.println(errorMessage);
                    isValid = false;
                }
            }catch(InputMismatchException e){
                System.out.println(errorMessage);
                isValid = false;
                input.nextLine();
            }
        }while(!isValid);
        return number;
    }

    /**
     * Metoda koja provjerava da li je unesena vrijednost numerička i pozitivna
     * @param input omogućuje korisniku unos tražene vrijednosti
     * @param message poruka koja govori što se traži od korisnika
     * @param errorMessage - poruka pogreške
     * @return unesena vrijednost
     */
    public static BigDecimal validatePositiveBigDecimal(Scanner input, String message, String errorMessage) {
        BigDecimal number = BigDecimal.ZERO;
        Boolean isValid = false;

        do{
            isValid = true;
            try{
                System.out.print(message);
                number = input.nextBigDecimal();

                if(number.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println(errorMessage);
                    isValid = false;
                }
            }catch(InputMismatchException e){
                System.out.println(errorMessage);
                isValid = false;
                input.nextLine();
            }
        }while(!isValid);
        return number;
    }

    /**
     * Metoda koja provjervava da li posotji jelo s istim imenom
     * @param name ime jela
     * @param meals lista jela
     * @throws DuplicateEntryException u slucaju postojeceg jela ce se baciti iznimka
     */
    public static void checkForDuplicateMealName(String name, List<Meal> meals) throws DuplicateEntryException {

        for (Meal meal : meals) {
            if (meal.getName().equalsIgnoreCase(name)) {
                throw new DuplicateEntryException("Jelo s nazivom '" + name + "' već postoji.");
            }
        }
    }

    /**
     * Metoda koja provjerava da li posotji zaposlenik s istim imenom i prezimenom
     * @param firstName ime zaposlenika
     * @param lastName prezime zaposlenika
     * @param employees lista zaposlenika
     * @throws DuplicateEntryException u slucaju postojeceg zaposlenika ce se baciti iznimka
     */
    public static void checkForDuplicateEmployeer(String firstName, String lastName, List<Person> employees) throws DuplicateEntryException {

        for(Person employee : employees) {
            if (employee.getFirstName().equalsIgnoreCase(firstName) && employee.getLastName().equalsIgnoreCase(lastName)) {
                throw new DuplicateEntryException("Zaposlenik " + firstName + " " + lastName + " već postoji!");
            }
        }
    }

    /**
     * Metoda koja provjarava da li posotji restoran s istim imenom na istoj adresi
     * @param name ime restorana
     * @param street ulica restorana
     * @param restaurants lista restorana
     * @throws DuplicateEntryException u slucaju postojeceg restorana ce se baciti iznimka
     */
    public static void checkForDuplicateRestaurant(String name, String street, List<Restaurant> restaurants) throws DuplicateEntryException {

        for(Restaurant restaurant : restaurants) {
            if(restaurant.getName().equalsIgnoreCase(name) && restaurant.getAddress().getStreet().equalsIgnoreCase(street)) {
                throw new DuplicateEntryException("Restoran " + name + " već postoji!");
            }
        }
    }
}
