package pl.lodz.p.it.tks.data.model.resource;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ThreeStarApartmentEnt extends ApartmentEnt {
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
