package hr.java.restaurant.sort;

import hr.java.restaurant.model.*;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;

/**
 * Klasa koja usporeduje duzine ugovore medu zaposlenicima
 * od najkracek prema najduzem
 */
public class ContractDurationComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {

        Long duration1 = getContractDuration(p1);
        Long duration2 = getContractDuration(p2);
        return duration1.compareTo(duration2);
    }

    private Long getContractDuration(Person person) {
        long duration = 0;

        if(person instanceof Chef chef) {
            duration = ChronoUnit.DAYS.between(chef.getContract().getStartDate(), chef.getContract().getEndDate());
        }else if(person instanceof Waiter waiter) {
            duration = ChronoUnit.DAYS.between(waiter.getContract().getStartDate(), waiter.getContract().getEndDate());
        }else if(person instanceof Deliverer deliverer) {
            duration = ChronoUnit.DAYS.between(deliverer.getContract().getStartDate(), deliverer.getContract().getEndDate());
        }
        return duration;
    }
}
