package pl.lodz.p.it.tks.domainmodel.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.domainmodel.trait.ModelIdTrait;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Car extends ModelIdTrait {
    @NonNull
    @NotNull(message = "engineCapacity cannot be null.")
    @Min(value = 1, message = "engineCapacity should be at 1 minimum.")
    private double engineCapacity;

    @NonNull
    @NotNull(message = "vin cannot be null.")
    @Size(min = 17, max = 17, message = "brand length should be 17 chars.")
    private String vin;

    @NonNull
    @NotNull(message = "doorNumber cannot be null.")
    @Min(value = 3, message = "doorNumber should be at 3 minimum.")
    private int doorNumber;

    @NonNull
    @NotNull(message = "brand cannot be null.")
    @Size(min = 1, max = 25, message = "brand length should be 1-25 chars.")
    private String brand;

    @NonNull
    @NotNull(message = "basePricePerDay cannot be null.")
    @Min(value = 200, message = "basePricePerDay should be at 200 minimum.")
    private double basePricePerDay;

    public abstract double actualPricePerDay();
}
