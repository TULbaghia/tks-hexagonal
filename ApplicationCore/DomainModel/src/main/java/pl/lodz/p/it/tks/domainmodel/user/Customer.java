package pl.lodz.p.it.tks.domainmodel.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.json.JSONPropertyIgnore;
import pl.lodz.p.it.tks.domainmodel.exception.CustomerException;
import pl.lodz.p.it.tks.domainmodel.user.customer.BasicCustomer;
import pl.lodz.p.it.tks.domainmodel.user.customer.CustomerType;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Customer extends User {
    private int rentsNumber;

    @NonNull
    @Builder.Default
    private CustomerType customerType = new BasicCustomer();

    public void changeCustomerType(CustomerType ct) throws CustomerException {
        if (ct == null) {
            throw new CustomerException("CustomerType cannot be null.");
        }
        customerType = ct;
    }

    public double getDiscount(double price) throws CustomerException {
        return customerType.calculateDiscount(price);
    }
}
