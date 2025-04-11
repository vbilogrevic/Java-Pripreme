package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Deliverer extends Person{

    private Contract contract;
    private Integer deliveryCount;

    public Deliverer(String firstName, String lastName, Contract contract) {
        super(firstName, lastName);
        this.contract = contract;
        this.deliveryCount = 0;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public static class DelivererBuilder {
        private String firstName;
        private String lastName;
        private Contract contract;

        public Deliverer.DelivererBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Deliverer.DelivererBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Deliverer.DelivererBuilder setContract(Contract contract) {
            this.contract = contract;
            return this;
        }

        public Deliverer build() {
            return new Deliverer(firstName, lastName, contract);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DelivererBuilder that = (DelivererBuilder) o;
            return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(contract, that.contract);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName, contract);
        }
    }
}
