package pl.lodz.p.it.tks.applicationports.infrastructure.user.customer;

import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.User;

import java.util.List;

public interface GetAllCustomerPort {
    List<Customer> getAll();
}
