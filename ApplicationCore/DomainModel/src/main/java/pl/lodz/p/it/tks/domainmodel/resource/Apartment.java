package pl.lodz.p.it.tks.domainmodel.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.domainmodel.trait.IdTrait;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class Apartment extends IdTrait {
    @PositiveOrZero
    private int howManyBeds;

    @PositiveOrZero
    private int doorNumber;

    @Positive
    private double basePricePerDay;

    public abstract double actualPricePerDay();

    public String presentYourself() {
        return "[" + getId() + "] DoorN(" + getDoorNumber() + ") Price(" + getBasePricePerDay() + ") ";
    }
}