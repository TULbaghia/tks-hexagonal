package pl.lodz.p.it.tks.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

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
}
