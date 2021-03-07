package pl.lodz.p.it.tks.domainmodel.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ThreeStarApartment extends Apartment {
    @NotEmpty
    @NonNull
    private String bonus;

    @Override
    public double actualPricePerDay() {
        return 1.5 * super.getBasePricePerDay();
    }

    @Override
    public String presentYourself() {
        return super.presentYourself() + "bonus(" + getBonus() + ") ";
    }
}
