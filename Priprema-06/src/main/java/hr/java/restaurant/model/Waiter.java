package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Waiter extends Person{

    private Contract contract;

    public Waiter(String firstName, String lastName, Contract contract) {
        super(firstName, lastName);
        this.contract = contract;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public static class WaiterBuilder {
        private String firstName;
        private String lastName;
        private Contract contract;

        public Waiter.WaiterBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Waiter.WaiterBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Waiter.WaiterBuilder setContract(Contract contract) {
            this.contract = contract;
            return this;
        }

        public Waiter build() {
            return new Waiter(firstName, lastName, contract);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WaiterBuilder that = (WaiterBuilder) o;
            return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(contract, that.contract);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName, contract);
        }
    }
}
