package pl.lodz.p.it.tks.rent.applicationservices;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.customer.*;
import pl.lodz.p.it.tks.rent.applicationports.ui.CustomerUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService implements CustomerUseCase {
    private final AddCustomerPort addCustomerPort;
    private final UpdateCustomerPort updateCustomerPort;
    private final GetAllCustomerPort getAllCustomerPort;
    private final GetCustomerByIdPort getCustomerByIdPort;
    private final GetCustomerByLoginPort getCustomerByLoginPort;

    @Inject
    public CustomerService(AddCustomerPort addCustomerPort, UpdateCustomerPort updateCustomerPort, GetAllCustomerPort getAllCustomerPort, GetCustomerByIdPort getCustomerByIdPort, GetCustomerByLoginPort getCustomerByLoginPort) {
        this.addCustomerPort = addCustomerPort;
        this.updateCustomerPort = updateCustomerPort;
        this.getAllCustomerPort = getAllCustomerPort;
        this.getCustomerByIdPort = getCustomerByIdPort;
        this.getCustomerByLoginPort = getCustomerByLoginPort;
    }

    @Override
    public Customer add(Customer customer) throws RepositoryAdapterException {
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(customer.getLogin()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return addCustomerPort.add(customer);
    }

    @Override
    public Customer update(Customer customer) throws RepositoryAdapterException {
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(customer.getLogin()) && !x.getId().equals(customer.getId()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return updateCustomerPort.update(customer);
    }

    @Override
    public List<Customer> getAll() {
        return getAllCustomerPort.getAll();
    }

    @Override
    public Customer get(UUID uuid) throws RepositoryAdapterException {
        return getCustomerByIdPort.get(uuid);
    }

    @Override
    public Customer get(String login) throws RepositoryAdapterException {
        return getCustomerByLoginPort.get(login);
    }
}
