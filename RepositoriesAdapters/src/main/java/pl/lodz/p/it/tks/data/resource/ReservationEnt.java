package pl.lodz.p.it.tks.data.resource;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.data.exception.GuestTypeException;
import pl.lodz.p.it.tks.data.exception.ReservationEntException;
import pl.lodz.p.it.tks.data.trait.IdTraitEnt;
import pl.lodz.p.it.tks.data.user.GuestEnt;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReservationEnt extends IdTraitEnt {

    private ApartmentEnt apartment;

    @NonNull
    @NotNull
    private GuestEnt guest;

    @Builder.Default
    private LocalDateTime reservationStartDate = LocalDateTime.now();

    @Setter(AccessLevel.NONE)
    private LocalDateTime reservationEndDate;

    @Setter(AccessLevel.NONE)
    @PositiveOrZero
    private double price;

    public void endReservation() throws ReservationEntException, GuestTypeException {
        if (reservationEndDate == null) {
            LocalDateTime reservationEnd = LocalDateTime.now();
            if (reservationEnd.isBefore(this.getReservationStartDate())) {
                throw new ReservationEntException("reservationEndedBeforeStart");
            }
            if(this.apartment == null) {
                throw new ReservationEntException("apartmentIsNull");
            }
            this.reservationEndDate = reservationEnd;
            this.price = this.guest.getDiscount(this.getDurationDays() * this.apartment.actualPricePerDay());
            return;
        }
        throw new ReservationEntException("alreadyEnded");
    }

    public long getDurationDays() {
        if (reservationEndDate != null) {
            long duration = Math.abs(Duration.between(reservationEndDate, reservationStartDate).toDays());
            return duration == 0 ? 1 : duration;
        }
        return 0;
    }
}
