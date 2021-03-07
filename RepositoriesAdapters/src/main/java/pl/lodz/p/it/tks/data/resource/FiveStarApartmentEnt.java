package pl.lodz.p.it.tks.data.resource;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FiveStarApartmentEnt extends ApartmentEnt {
    @NotEmpty
    @NonNull
    private String bonus;

    @NotEmpty
    @NonNull
    private String pcName;

    public double actualPricePerDay() {
        return 2 * super.getBasePricePerDay();
    }

    @Override
    public String presentYourself() {
        return super.presentYourself() +
                "bonus(" + getBonus() + ") " +
                "pcName(" + getPcName() + ") ";
    }
}
