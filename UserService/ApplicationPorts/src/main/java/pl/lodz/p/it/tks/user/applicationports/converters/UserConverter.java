package pl.lodz.p.it.tks.user.applicationports.converters;

import pl.lodz.p.it.tks.user.data.user.UserEnt;
import pl.lodz.p.it.tks.user.data.user.UserTypeEnt;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;

public class UserConverter {
    public static UserEnt toEnt(User user) {
        return UserEnt.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(user.isActive())
                .userTypeEnt(toUserTypeEnt(user.getUserType()))
                .build();
    }

    public static User toDomain(UserEnt user) {
        return User.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(user.isActive())
                .userType(toUserType(user.getUserTypeEnt()))
                .build();
    }

    private static UserTypeEnt toUserTypeEnt(UserType userType) {
        if (userType == UserType.ADMIN) {
            return UserTypeEnt.ADMIN;
        }
        if (userType == UserType.CUSTOMER) {
            return UserTypeEnt.CUSTOMER;
        }
        if(userType == UserType.EMPLOYEE) {
            return UserTypeEnt.EMPLOYEE;
        }
        throw new RuntimeException("");
    }

    private static UserType toUserType(UserTypeEnt userType) {
        if (userType == UserTypeEnt.ADMIN) {
            return UserType.ADMIN;
        }
        if (userType == UserTypeEnt.CUSTOMER) {
            return UserType.CUSTOMER;
        }
        if(userType == UserTypeEnt.EMPLOYEE) {
            return UserType.EMPLOYEE;
        }
        throw new RuntimeException("");
    }
}
