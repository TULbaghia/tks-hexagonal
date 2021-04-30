package pl.lodz.p.it.tks.rent.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.json.JSONPropertyIgnore;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

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
    @Getter
    private Boolean active;

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId().toString())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .active(user.isActive())
                .build();
    }

}