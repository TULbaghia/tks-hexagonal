package pl.lodz.p.it.tks.rent.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExclusiveCarDto extends CarDto {
    @JsonProperty
    @NonNull
    @NotNull(message = "driverEquipment cannot be null.")
    @Size(min = 1, max = 255, message = "brand length should be 1-255 chars.")
    private String driverEquipment;
    @JsonProperty
    @NonNull
    @NotNull(message = "boardPcName cannot be null.")
    @Size(min = 1, max = 25, message = "brand length should be 1-255 chars.")
    private String boardPcName;

    public static ExclusiveCarDto toDto(ExclusiveCar economyCar) {
        return ExclusiveCarDto.builder()
                .id(economyCar.getId().toString())
                .engineCapacity(economyCar.getEngineCapacity())
                .vin(economyCar.getVin())
                .doorNumber(economyCar.getDoorNumber())
                .brand(economyCar.getBrand())
                .basePricePerDay(economyCar.getBasePricePerDay())
                .driverEquipment(economyCar.getDriverEquipment())
                .boardPcName(economyCar.getBoardPcName())
                .build();
    }

    @Override
    public double actualPricePerDay() {
        return 2 * getBasePricePerDay();
    }
}
