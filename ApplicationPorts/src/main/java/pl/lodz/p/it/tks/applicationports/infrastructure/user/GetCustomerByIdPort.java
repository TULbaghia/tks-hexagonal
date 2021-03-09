package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

import java.util.UUID;

public interface GetCustomerByIdPort {
    Customer get(UUID uuid) throws RepositoryAdapterException;
}
