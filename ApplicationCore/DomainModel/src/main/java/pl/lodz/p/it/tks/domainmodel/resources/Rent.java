package pl.lodz.p.it.tks.domainmodel.resources;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.domainmodel.exception.CustomerException;
import pl.lodz.p.it.tks.domainmodel.exception.RentException;
import pl.lodz.p.it.tks.domainmodel.trait.ModelIdTrait;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Rent extends ModelIdTrait {
    @NonNull
    @Builder.Default
    private LocalDateTime rentStartDate = LocalDateTime.now();
    @NonNull
    private Customer customer;
    @NonNull
    private Car car;

    @Setter(AccessLevel.NONE)
    private double price;
    @Setter(AccessLevel.NONE)
    private LocalDateTime rentEndDate;

    public void endRent() throws RentException, CustomerException {
        if (rentEndDate == null) {
            LocalDateTime rentEnd = LocalDateTime.now();
            if (rentEnd.isBefore(this.getRentStartDate())) {
                throw new RentException("Rent end must be placed after rent start date");
            }
            rentEndDate = rentEnd;
            price = customer.getDiscount(getDurationDays() * car.actualPricePerDay());
            return;
        }
        throw new RentException("Rent has been already ended.");
    }

    public long getDurationDays() {
        if (rentEndDate != null) {
            long duration = Math.abs(Duration.between(rentEndDate, rentStartDate).toDays());
            return duration == 0 ? 1 : duration;
        }
        return 0;
    }
}
