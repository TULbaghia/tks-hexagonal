package pl.lodz.p.it.tks.data.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.data.guesttype.BasicGuestType;
import pl.lodz.p.it.tks.data.guesttype.GuestType;
import pl.lodz.p.it.tks.data.exception.GuestEntException;
import pl.lodz.p.it.tks.data.exception.GuestTypeException;

import javax.validation.constraints.PositiveOrZero;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GuestEnt extends UserEnt {
    @PositiveOrZero
    private int numberOfStays;

    @Setter(AccessLevel.NONE)
    @Builder.Default
    @NonNull
    private GuestType guestType = new BasicGuestType();

    public double getDiscount(double price) throws GuestTypeException {
        return guestType.calculateDiscount(price);
    }

    public void changeGuestType(GuestType guestType) throws GuestEntException {
        if (guestType == null) {
            throw new GuestEntException("guestTypeIsNull");
        } else {
            this.guestType = guestType;
        }
    }

    public int getMaxApartmentsNumber() {
        return this.guestType.getMaxApartmentsNumber();
    }
}

