package pl.lodz.p.it.tks.rent.domainmodel.user.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.lodz.p.it.tks.rent.domainmodel.exception.CustomerException;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public abstract class CustomerType {
    private final int maxVehiclesRentedNumber;

    public abstract double calculateDiscount(double price) throws CustomerException;
}
