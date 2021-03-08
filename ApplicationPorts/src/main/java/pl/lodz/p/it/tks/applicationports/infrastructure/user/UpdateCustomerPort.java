package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

public interface UpdateCustomerPort {
    void update(Customer customer) throws RepositoryAdapterException;
}
