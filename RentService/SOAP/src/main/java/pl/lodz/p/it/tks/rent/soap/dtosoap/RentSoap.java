package pl.lodz.p.it.tks.rent.soap.dtosoap;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@SuperBuilder
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RentSoap")
public class RentSoap {
    private String id;
    private String carId;
    private String customerId;
    private String rentStartDate;
    private String rentEndDate;
    private double price;

    public static RentSoap toSoap(Rent reservation) {
        return RentSoap.builder()
                .id(reservation.getId().toString())
                .carId(reservation.getCar() != null ? reservation.getCar().getId().toString() : null)
                .customerId(reservation.getCustomer().getId().toString())
                .rentStartDate(reservation.getRentStartDate().toString())
                .rentEndDate(reservation.getRentEndDate() != null ? reservation.getRentEndDate().toString() : null)
                .price(reservation.getPrice())
                .build();
    }
}
