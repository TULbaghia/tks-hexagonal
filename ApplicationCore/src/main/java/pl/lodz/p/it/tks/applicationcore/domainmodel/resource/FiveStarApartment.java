package pl.lodz.p.it.tks.applicationcore.domainmodel.resource;

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
public class FiveStarApartment extends Apartment {
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
