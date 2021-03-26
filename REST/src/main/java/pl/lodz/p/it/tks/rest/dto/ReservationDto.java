package pl.lodz.p.it.tks.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;

@Data
@SuperBuilder
@RequiredArgsConstructor
public class ReservationDto {
    @JsonProperty
    private String id;
    @JsonProperty
    private String carId;
    @JsonProperty
    private String customerId;
    @JsonProperty
    private String rentStartDate;
    @JsonProperty
    private String rentEndDate;
    @JsonProperty
    private double price;

    public static ReservationDto toDto(Rent reservation) {
        return ReservationDto.builder()
                .id(reservation.getId().toString())
                .carId(reservation.getCar() != null ? reservation.getCar().getId().toString() : null)
                .customerId(reservation.getCustomer().getId().toString())
                .rentStartDate(reservation.getRentStartDate().toString())
                .rentEndDate(reservation.getRentEndDate() != null ? reservation.getRentEndDate().toString() : null)
                .price(reservation.getPrice())
                .build();
    }
}
