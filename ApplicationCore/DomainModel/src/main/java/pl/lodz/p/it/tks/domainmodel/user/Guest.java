package pl.lodz.p.it.tks.domainmodel.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.domainmodel.exception.GuestException;
import pl.lodz.p.it.tks.domainmodel.exception.GuestTypeException;
import pl.lodz.p.it.tks.domainmodel.guesttype.BasicGuestType;
import pl.lodz.p.it.tks.domainmodel.guesttype.GuestType;

import javax.validation.constraints.PositiveOrZero;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Guest extends User {
    @PositiveOrZero
    private int numberOfStays;

    @Setter(AccessLevel.NONE)
    @Builder.Default
    @NonNull
    private GuestType guestType = new BasicGuestType();

    public double getDiscount(double price) throws GuestTypeException {
        return guestType.calculateDiscount(price);
    }

    public void changeGuestType(GuestType guestType) throws GuestException {
        if (guestType == null) {
            throw new GuestException("guestTypeIsNull");
        } else {
            this.guestType = guestType;
        }
    }

    public int getMaxApartmentsNumber() {
        return this.guestType.getMaxApartmentsNumber();
    }
}

