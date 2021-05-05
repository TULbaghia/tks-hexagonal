package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.customer;

import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;

import java.util.List;

public interface GetAllCustomerPort {
    List<Customer> getAll();
}
