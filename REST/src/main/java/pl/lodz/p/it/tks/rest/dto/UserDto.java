package pl.lodz.p.it.tks.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@RequiredArgsConstructor
public class UserDto {
    @JsonProperty
    private String id;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private String login;
    @JsonProperty
    private String password;
    @JsonProperty
    @Getter
    private Boolean isActive;
}