package pl.lodz.p.it.tks.domainmodel.user.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.lodz.p.it.tks.domainmodel.exception.CustomerException;

@Getter
@ToString
@AllArgsConstructor
public abstract class CustomerType {
    private final int maxVehiclesRentedNumber;

    public abstract double calculateDiscount(double price) throws CustomerException;
}
