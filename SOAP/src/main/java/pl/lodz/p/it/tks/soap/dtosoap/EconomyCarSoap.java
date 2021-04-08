package pl.lodz.p.it.tks.soap.dtosoap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EconomyCarSoap extends CarSoap {
    @JsonProperty
    @NonNull
    @NotNull(message = "driverEquipment cannot be null.")
    @Size(min = 1, max = 255)
    private String driverEquipment;

    public static EconomyCarSoap toSoap(EconomyCar economyCar) {
        return EconomyCarSoap.builder()
                .id(economyCar.getId().toString())
                .engineCapacity(economyCar.getEngineCapacity())
                .vin(economyCar.getVin())
                .doorNumber(economyCar.getDoorNumber())
                .brand(economyCar.getBrand())
                .basePricePerDay(economyCar.getBasePricePerDay())
                .driverEquipment(economyCar.getDriverEquipment())
                .build();
    }

    @Override
    public double actualPricePerDay() {
        return 1.5 * getBasePricePerDay();
    }
}
