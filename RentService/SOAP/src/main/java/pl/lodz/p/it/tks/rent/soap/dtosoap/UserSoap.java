package pl.lodz.p.it.tks.rent.soap.dtosoap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
    private Boolean active;

    public static UserSoap toSoap(User user) {
        return UserSoap.builder()
                .id(user.getId().toString())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .active(user.isActive())
                .build();
    }

}