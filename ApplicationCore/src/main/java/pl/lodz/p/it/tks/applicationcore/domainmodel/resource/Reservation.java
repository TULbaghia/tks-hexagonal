package pl.lodz.p.it.tks.applicationcore.domainmodel.resource;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.applicationcore.domainmodel.exception.GuestTypeException;
import pl.lodz.p.it.tks.applicationcore.domainmodel.exception.ReservationException;
import pl.lodz.p.it.tks.applicationcore.domainmodel.trait.IdTrait;
import pl.lodz.p.it.tks.applicationcore.domainmodel.user.Guest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Reservation extends IdTrait {

    private Apartment apartment;

    @NonNull
    @NotNull
    private Guest guest;

    @Builder.Default
    private LocalDateTime reservationStartDate = LocalDateTime.now();

    @Setter(AccessLevel.NONE)
    private LocalDateTime reservationEndDate;

    @Setter(AccessLevel.NONE)
    @PositiveOrZero
    private double price;

    public void endReservation() throws ReservationException, GuestTypeException {
        if (reservationEndDate == null) {
            LocalDateTime reservationEnd = LocalDateTime.now();
            if (reservationEnd.isBefore(this.getReservationStartDate())) {
                throw new ReservationException("reservationEndedBeforeStart");
            }
            if(this.apartment == null) {
                throw new ReservationException("apartmentIsNull");
            }
            this.reservationEndDate = reservationEnd;
            this.price = this.guest.getDiscount(this.getDurationDays() * this.apartment.actualPricePerDay());
            return;
        }
        throw new ReservationException("alreadyEnded");
    }

    public long getDurationDays() {
        if (reservationEndDate != null) {
            long duration = Math.abs(Duration.between(reservationEndDate, reservationStartDate).toDays());
            return duration == 0 ? 1 : duration;
        }
        return 0;
    }
}
