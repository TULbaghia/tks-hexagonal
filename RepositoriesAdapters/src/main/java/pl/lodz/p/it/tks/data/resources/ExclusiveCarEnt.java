package pl.lodz.p.it.tks.data.resources;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExclusiveCarEnt extends CarEnt {
    @NonNull
    @NotNull(message = "driverEquipment cannot be null.")
    @Size(min = 1, max = 255, message = "brand length should be 1-255 chars.")
    private String driverEquipment;
    @NonNull
    @NotNull(message = "boardPcName cannot be null.")
    @Size(min = 1, max = 25, message = "brand length should be 1-255 chars.")
    private String boardPcName;

    @Override
    public double actualPricePerDay() {
        return 2 * getBasePricePerDay();
    }
}
