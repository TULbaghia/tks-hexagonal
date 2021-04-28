package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.customer;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;

public interface GetCustomerByLoginPort {
    Customer get(String login) throws RepositoryAdapterException;
}
