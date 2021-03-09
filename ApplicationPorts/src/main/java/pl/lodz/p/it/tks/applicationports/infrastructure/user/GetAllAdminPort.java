package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.User;

import java.util.List;

public interface GetAllAdminPort {
    List<Admin> getAll();
}
