package pl.lodz.p.it.tks.applicationports.infrastructure.user.customer;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

public interface GetCustomerByLoginPort {
    Customer get(String login) throws RepositoryAdapterException;
}
