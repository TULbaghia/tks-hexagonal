package pl.lodz.p.it.tks.applicationports.ui;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerUseCase {

    Customer add(Customer customer) throws RepositoryAdapterException;

    Customer update(Customer customer) throws RepositoryAdapterException;

    List<Customer> getAll();

    Customer get(UUID uuid) throws RepositoryAdapterException;

    Customer get(String login) throws RepositoryAdapterException;
}
