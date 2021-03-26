package pl.lodz.p.it.tks.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.json.JSONPropertyIgnore;
import pl.lodz.p.it.tks.domainmodel.user.User;

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
    private Boolean active;

    @JSONPropertyIgnore
    public String getPassword() {
        return password;
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId().toString())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .active(user.isActive())
                .build();
    }

}