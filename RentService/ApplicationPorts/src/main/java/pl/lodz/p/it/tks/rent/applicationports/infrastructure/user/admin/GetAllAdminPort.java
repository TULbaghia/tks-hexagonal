package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.admin;

import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;

import java.util.List;

public interface GetAllAdminPort {
    List<Admin> getAll();
}
