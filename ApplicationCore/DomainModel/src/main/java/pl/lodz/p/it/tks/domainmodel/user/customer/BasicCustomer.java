package pl.lodz.p.it.tks.domainmodel.user.customer;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lodz.p.it.tks.domainmodel.exception.CustomerException;


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BasicCustomer extends CustomerType {
    public BasicCustomer() {
        super(2);
    }

    @Override
    public double calculateDiscount(double price) throws CustomerException {
        if (price < 0) {
            throw new CustomerException("Price cannot be smaller than zero.");
        }
        return price;
    }
}
