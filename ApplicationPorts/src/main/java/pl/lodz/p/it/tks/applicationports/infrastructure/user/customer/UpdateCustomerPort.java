package pl.lodz.p.it.tks.applicationports.infrastructure.user.customer;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

public interface UpdateCustomerPort {
    Customer update(Customer customer) throws RepositoryAdapterException;
}
