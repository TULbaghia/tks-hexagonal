package pl.lodz.p.it.tks.rent.domainmodel.user.customer;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lodz.p.it.tks.rent.domainmodel.exception.CustomerException;


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpecialCustomer extends CustomerType {
    public SpecialCustomer() {
        super(4);
    }

    @Override
    public double calculateDiscount(double price) throws CustomerException {
        if (price < 0) {
            throw new CustomerException("Price cannot be smaller than zero.");
        }
        return .8 * price;
    }
}
