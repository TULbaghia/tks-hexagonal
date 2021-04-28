package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.customer;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;

import java.util.UUID;

public interface GetCustomerByIdPort {
    Customer get(UUID uuid) throws RepositoryAdapterException;
}
