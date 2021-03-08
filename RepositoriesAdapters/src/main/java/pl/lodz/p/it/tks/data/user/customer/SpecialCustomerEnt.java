package pl.lodz.p.it.tks.data.user.customer;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lodz.p.it.tks.data.exception.CustomerEntException;


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpecialCustomerEnt extends CustomerTypeEnt {
    public SpecialCustomerEnt() {
        super(4);
    }

    @Override
    public double calculateDiscount(double price) throws CustomerEntException {
        if (price < 0) {
            throw new CustomerEntException("Price cannot be smaller than zero.");
        }
        return .8 * price;
    }
}
