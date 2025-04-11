package hr.java.restaurant.sort;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Klasa koja usporeduje place medu zaposlenicima
 * od najvece prema najmanjoj
 */
public class SalaryComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        BigDecimal salary1 = getSalary(p1);
        BigDecimal salary2 = getSalary(p2);

        return salary2.compareTo(salary1);
    }

    private BigDecimal getSalary(Person person) {
        BigDecimal salary = BigDecimal.ZERO;

        if(person instanceof Chef chef) {
            salary = chef.getContract().getSalary();
        }else if(person instanceof Waiter waiter) {
            salary = waiter.getContract().getSalary();
        }else if(person instanceof Deliverer deliverer) {
            salary = deliverer.getContract().getSalary();
        }
        return salary;
    }
}
