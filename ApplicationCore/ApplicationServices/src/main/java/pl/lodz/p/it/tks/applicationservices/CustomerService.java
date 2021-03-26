package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.user.customer.*;
import pl.lodz.p.it.tks.applicationports.ui.CustomerUseCase;
import pl.lodz.p.it.tks.applicationservices.exception.CoreServiceException;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

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
        checkPassword(customer.getPassword());
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(customer.getLogin()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return addCustomerPort.add(customer);
    }

    @Override
    public Customer update(Customer customer) throws RepositoryAdapterException {
        checkPassword(customer.getPassword());
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

    private void checkPassword(String password) throws RepositoryAdapterException {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new RepositoryAdapterException("Password is not secure.");
        }
    }
}
