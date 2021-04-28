package pl.lodz.p.it.tks.user.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.json.JSONPropertyIgnore;
import pl.lodz.p.it.tks.user.data.user.UserTypeEnt;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;

import java.util.UUID;

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
    @JsonProperty
    private String userType;

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
                .userType(toUserTypeString(user.getUserType()))
                .build();
    }

    public static User fromDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId() != null ? UUID.fromString(userDto.getId()) : null)
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .isActive(userDto.getActive() == null || userDto.getActive())
                .userType(toUserType(userDto.getUserType()))
                .build();
    }

    private static String toUserTypeString(UserType userType) {
        return userType.getValue();
    }

    private static UserType toUserType(String userType) {
        if (UserType.ADMIN.getValue().equals(userType)) {
            return UserType.ADMIN;
        }
        if (UserType.CUSTOMER.getValue().equals(userType)) {
            return UserType.CUSTOMER;
        }
        if(UserType.EMPLOYEE.getValue().equals(userType)) {
            return UserType.EMPLOYEE;
        }
        throw new RuntimeException("");
    }

}