package pl.lodz.p.it.tks.soap.dtosoap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ExclusiveCarSoap")
public class ExclusiveCarSoap extends CarSoap {
    @NonNull
    @NotNull(message = "driverEquipment cannot be null.")
    @Size(min = 1, max = 255, message = "brand length should be 1-255 chars.")
    private String driverEquipment;
    @NonNull
    @NotNull(message = "boardPcName cannot be null.")
    @Size(min = 1, max = 25, message = "brand length should be 1-255 chars.")
    private String boardPcName;

    public static ExclusiveCarSoap toSoap(ExclusiveCar economyCar) {
        return ExclusiveCarSoap.builder()
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
