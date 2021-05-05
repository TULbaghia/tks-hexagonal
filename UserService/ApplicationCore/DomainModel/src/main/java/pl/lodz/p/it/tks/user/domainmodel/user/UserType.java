package pl.lodz.p.it.tks.user.domainmodel.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    CUSTOMER("CUSTOMER"), EMPLOYEE("EMPLOYEE"), ADMIN("ADMIN");

    private final String value;
}
