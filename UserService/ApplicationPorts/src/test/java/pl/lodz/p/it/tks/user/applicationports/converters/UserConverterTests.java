package pl.lodz.p.it.tks.user.applicationports.converters;

import org.testng.Assert;
import org.testng.annotations.Test;
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

        Assert.assertEquals(customerEnt.getId(), user.getId());
        Assert.assertEquals(customerEnt.isActive(), user.isActive());
        Assert.assertEquals(customerEnt.getFirstname(), user.getFirstname());
        Assert.assertEquals(customerEnt.getLastname(), user.getLastname());
        Assert.assertEquals(customerEnt.getLogin(), user.getLogin());
        Assert.assertEquals(customerEnt.getPassword(), user.getPassword());
        Assert.assertEquals(customerEnt.getUserTypeEnt(), UserTypeEnt.ADMIN);
    }
}
