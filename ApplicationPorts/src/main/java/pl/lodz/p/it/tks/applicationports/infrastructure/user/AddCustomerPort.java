package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

public interface AddCustomerPort {
    void add(Customer customer) throws RepositoryAdapterException;
}
