package pl.lodz.p.it.tks.applicationcore.domainmodel.guesttype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.lodz.p.it.tks.applicationcore.domainmodel.exception.GuestTypeException;
import pl.lodz.p.it.tks.data.model.exception.GuestTypeException;

@Getter
@ToString
@AllArgsConstructor
public abstract class GuestType {
    private final int maxApartmentsNumber;

    public abstract double calculateDiscount(double price) throws GuestTypeException, GuestTypeException;
}
