package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.customer;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;

import java.util.UUID;

public interface DeleteCustomerPort {
    void delete(UUID uuid) throws RepositoryAdapterException;
}
