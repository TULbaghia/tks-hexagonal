package pl.lodz.p.it.tks.rent.mq;

import org.json.JSONObject;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import java.util.UUID;

public class UserFactory {
    public static User createUser(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String login = jsonObject.getString("login");
        String firstname =  jsonObject.getString("firstname");
        String lastname =  jsonObject.getString("lastname");
        String userType = jsonObject.getString("userType");
        boolean isActive = jsonObject.getBoolean("active");

        switch (userType) {
            case "ADMIN" : {
                return Admin.builder()
                        .id(UUID.fromString(id))
                        .firstname(firstname)
                        .lastname(lastname)
                        .isActive(isActive)
                        .login(login)
                        .build();
            }
            case "EMPLOYEE" : {
                return Employee.builder()
                        .id(UUID.fromString(id))
                        .firstname(firstname)
                        .lastname(lastname)
                        .isActive(isActive)
                        .login(login)
                        .build();
            }
            case "CUSTOMER" : {
                return Customer.builder()
                        .id(UUID.fromString(id))
                        .firstname(firstname)
                        .lastname(lastname)
                        .isActive(isActive)
                        .login(login)
                        .build();
            }
            default : break;
        }
        return null;
    }
}
