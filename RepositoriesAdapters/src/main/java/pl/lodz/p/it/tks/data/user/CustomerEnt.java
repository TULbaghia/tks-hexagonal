package pl.lodz.p.it.tks.data.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.data.exception.CustomerEntException;
import pl.lodz.p.it.tks.data.user.customer.BasicCustomerEnt;
import pl.lodz.p.it.tks.data.user.customer.CustomerTypeEnt;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomerEnt extends UserEnt {
    private int rentsNumber;

    @NonNull
    @Builder.Default
    private CustomerTypeEnt customerTypeEnt = new BasicCustomerEnt();

    public void changeCustomerType(CustomerTypeEnt ct) throws CustomerEntException {
        if (ct == null) {
            throw new CustomerEntException("CustomerType cannot be null.");
        }
        customerTypeEnt = ct;
    }

    public double getDiscount(double price) throws CustomerEntException {
        return customerTypeEnt.calculateDiscount(price);
    }

    public CustomerTypeEnt getCustomerTypeEnt() {
        return customerTypeEnt;
    }

    public int getRentsNumber() {
        return rentsNumber;
    }
}
