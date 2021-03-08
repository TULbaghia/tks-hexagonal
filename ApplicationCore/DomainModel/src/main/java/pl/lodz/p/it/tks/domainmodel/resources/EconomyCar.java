package pl.lodz.p.it.tks.domainmodel.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EconomyCar extends Car {
    @JsonProperty
    @NonNull
    @NotNull(message = "driverEquipment cannot be null.")
    @Size(min = 1, max = 255)
    private String driverEquipment;

    @Override
    public double actualPricePerDay() {
        return 1.5 * getBasePricePerDay();
    }
}
