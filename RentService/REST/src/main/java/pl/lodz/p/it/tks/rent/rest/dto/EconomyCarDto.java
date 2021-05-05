package pl.lodz.p.it.tks.rent.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EconomyCarDto extends CarDto {
    @JsonProperty
    @NonNull
    @NotNull(message = "driverEquipment cannot be null.")
    @Size(min = 1, max = 255)
    private String driverEquipment;

    public static EconomyCarDto toDto(EconomyCar economyCar) {
        return EconomyCarDto.builder()
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
