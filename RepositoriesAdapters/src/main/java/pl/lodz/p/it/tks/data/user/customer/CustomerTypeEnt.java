package pl.lodz.p.it.tks.data.user.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.lodz.p.it.tks.data.exception.CustomerEntException;

@Getter
@ToString
@AllArgsConstructor
public abstract class CustomerTypeEnt {
    private final int maxVehiclesRentedNumber;

    public abstract double calculateDiscount(double price) throws CustomerEntException;
}
