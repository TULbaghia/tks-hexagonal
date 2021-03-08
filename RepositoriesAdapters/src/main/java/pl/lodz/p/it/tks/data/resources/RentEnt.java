package pl.lodz.p.it.tks.data.resources;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.data.exception.CustomerEntException;
import pl.lodz.p.it.tks.data.exception.RentEntException;
import pl.lodz.p.it.tks.data.trait.ModelIdTraitEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RentEnt extends ModelIdTraitEnt {
    @NonNull
    @Builder.Default
    private LocalDateTime rentStartDate = LocalDateTime.now();
    @NonNull
    private CustomerEnt customer;
    @NonNull
    private CarEnt car;

    @Setter(AccessLevel.NONE)
    private double price;
    @Setter(AccessLevel.NONE)
    private LocalDateTime rentEndDate;

    public void endRent() throws RentEntException, CustomerEntException {
        if (rentEndDate == null) {
            LocalDateTime rentEnd = LocalDateTime.now();
            if (rentEnd.isBefore(this.getRentStartDate())) {
                throw new RentEntException("Rent end must be placed after rent start date");
            }
            rentEndDate = rentEnd;
            price = customer.getDiscount(getDurationDays() * car.actualPricePerDay());
            return;
        }
        throw new RentEntException("Rent has been already ended.");
    }

    public long getDurationDays() {
        if (rentEndDate != null) {
            long duration = Math.abs(Duration.between(rentEndDate, rentStartDate).toDays());
            return duration == 0 ? 1 : duration;
        }
        return 0;
    }
}
