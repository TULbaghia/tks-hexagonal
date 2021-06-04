package pl.lodz.p.it.tks.user.applicationports.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.user.data.user.UserEnt;
import pl.lodz.p.it.tks.user.data.user.UserTypeEnt;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;

public class UserConverterTests {

    @Test
    public void customerDomainToEntConverterTest() {
        User user = User.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userType(UserType.ADMIN)
                .build();

        UserEnt customerEnt = UserConverter.toEnt(user);

        Assertions.assertEquals(customerEnt.getId(), user.getId());
        Assertions.assertEquals(customerEnt.isActive(), user.isActive());
        Assertions.assertEquals(customerEnt.getFirstname(), user.getFirstname());
        Assertions.assertEquals(customerEnt.getLastname(), user.getLastname());
        Assertions.assertEquals(customerEnt.getLogin(), user.getLogin());
        Assertions.assertEquals(customerEnt.getPassword(), user.getPassword());
        Assertions.assertEquals(customerEnt.getUserTypeEnt(), UserTypeEnt.ADMIN);
    }
}
