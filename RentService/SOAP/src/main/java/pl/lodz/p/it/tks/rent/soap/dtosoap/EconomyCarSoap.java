package pl.lodz.p.it.tks.rent.soap.dtosoap;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;

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
@XmlRootElement(name = "EconomyCarSoap")
public class EconomyCarSoap extends CarSoap {
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
