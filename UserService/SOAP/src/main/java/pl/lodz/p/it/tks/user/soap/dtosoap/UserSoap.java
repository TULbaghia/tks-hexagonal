package pl.lodz.p.it.tks.user.soap.dtosoap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UserSoap")
public class UserSoap {
    private String id;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String userType;
    private Boolean active;

    public static UserSoap toSoap(User user) {
        return UserSoap.builder()
                .id(user.getId().toString())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .active(user.isActive())
                .userType(toUserTypeString(user.getUserType()))
                .build();
    }

    public static User fromSoap(UserSoap userDto) {
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