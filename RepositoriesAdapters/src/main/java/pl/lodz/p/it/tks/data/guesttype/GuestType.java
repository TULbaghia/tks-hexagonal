package pl.lodz.p.it.tks.data.guesttype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.lodz.p.it.tks.data.exception.GuestTypeException;

@Getter
@ToString
@AllArgsConstructor
public abstract class GuestType {
    private final int maxApartmentsNumber;

    public abstract double calculateDiscount(double price) throws GuestTypeException;
}
