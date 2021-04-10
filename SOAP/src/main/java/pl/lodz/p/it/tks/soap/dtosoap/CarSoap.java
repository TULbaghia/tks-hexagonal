package pl.lodz.p.it.tks.soap.dtosoap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CarSoap")
public abstract class CarSoap {
    @NonNull
    private String id;

    @NonNull
    @NotNull(message = "engineCapacity cannot be null.")
    @Min(value = 1, message = "engineCapacity should be at 1 minimum.")
    private double engineCapacity;

    @NonNull
    @NotNull(message = "vin cannot be null.")
    @Size(min = 17, max = 17, message = "vin length should be 17 chars.")
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
